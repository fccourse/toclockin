package digital.ilia.toclockinapi.services;

import digital.ilia.toclockinapi.dtos.request.TimeTrackingRecordRequest;
import digital.ilia.toclockinapi.dtos.response.handler.HandlerTimeTrackingRecord;
import digital.ilia.toclockinapi.dtos.response.handler.HandlerTimeTrackingType;
import digital.ilia.toclockinapi.entities.TimeTrackingRecord;
import digital.ilia.toclockinapi.entities.User;
import digital.ilia.toclockinapi.entities.enums.TimeTrackingType;
import digital.ilia.toclockinapi.repositories.TimeTrackingRecordRepository;
import digital.ilia.toclockinapi.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;

@Service
public class TimeTrackingRecordService {

    Logger logger = LoggerFactory.getLogger(TimeTrackingRecordService.class);

    private final TimeTrackingRecordRepository timeTrackingRecordRepository;
    private final UserRepository userRepository;

    @Autowired
    public TimeTrackingRecordService(TimeTrackingRecordRepository timeTrackingRecordRepository, UserRepository userRepository) {
        this.timeTrackingRecordRepository = timeTrackingRecordRepository;
        this.userRepository = userRepository;
    }

    public HandlerTimeTrackingRecord saveTimeTrackingRecord(TimeTrackingRecordRequest request, String email) {
        var todayDate = request.getTimeTrackingDate();

        if (isSaturdayOrSunday(todayDate)) {
            return setHandlerTimeTrackingRecord(HandlerTimeTrackingType.NOT_WEEKENDS,
                    "It is not possible to register the point on weekends.", null);
        } else {
            User user = userRepository.findByEmail(email).orElseThrow(() -> {
                throw new EmptyResultDataAccessException(1);
            });

            List<TimeTrackingRecord> todayTimeTrackings = getTodayTimeTrackingRecords(user, request.getTimeTrackingDate());

            if (todayTimeTrackings.size() >= 4) {
                return setHandlerTimeTrackingRecord(HandlerTimeTrackingType.ALREADY_DAY,
                        "You have already registered all the points of the day.", null);
            } else {
                return validateTimeTrackingRecord(request, user, todayTimeTrackings);
            }
        }
    }

    private HandlerTimeTrackingRecord validateTimeTrackingRecord(TimeTrackingRecordRequest request, User user, List<TimeTrackingRecord> todayTimeTrackings) {
        var typeNumber = todayTimeTrackings.size();

        var messageReturn = "created";
        HandlerTimeTrackingType type = HandlerTimeTrackingType.CREATED;
        TimeTrackingRecord timeTracking = null;

        switch (typeNumber) {
            case 0:
                timeTracking = saveTimeTrackingRecordByType(request, user, TimeTrackingType.ENTRY);
                break;
            case 1:
                timeTracking = saveTimeTrackingRecordByType(request, user, TimeTrackingType.LUNCH_START);
                break;
            case 2:
                Long timeDifferenceLunch = timeDifferenceLunch(todayTimeTrackings, request.getTimeTrackingDate());
                if (timeDifferenceLunch <= 3600000) {
                    type = HandlerTimeTrackingType.ONE_HOUR_LUNCH;
                    messageReturn = "There should be a one-hour lunch break.";
                } else {
                    timeTracking = saveTimeTrackingRecordByType(request, user, TimeTrackingType.LUNCH_RETURN);
                }
                break;
            case 3:
                timeTracking = saveTimeTrackingRecordByType(request, user, TimeTrackingType.EXIT);
                break;
            default:
                type = HandlerTimeTrackingType.UNEXPECTED_OPERATION;
                messageReturn = "Unexpected operation.";
        }

        return setHandlerTimeTrackingRecord(type, messageReturn, timeTracking);
    }

    private HandlerTimeTrackingRecord setHandlerTimeTrackingRecord(HandlerTimeTrackingType type, String message, TimeTrackingRecord timeTrackingRecord) {
        HandlerTimeTrackingRecord handler = new HandlerTimeTrackingRecord(message, type, timeTrackingRecord);
        logger.info(message);
        return handler;
    }

    private TimeTrackingRecord saveTimeTrackingRecordByType(TimeTrackingRecordRequest request, User user, TimeTrackingType type) {
        var timeTrackingRecord = new TimeTrackingRecord(request, user, type);
        return timeTrackingRecordRepository.save(timeTrackingRecord);
    }

    private Long timeDifferenceLunch(List<TimeTrackingRecord> todayTimeTrackings, LocalDateTime timeTrackingDate) {
        return todayTimeTrackings.stream()
                .filter(t -> t.getTimeTrackingType() == TimeTrackingType.LUNCH_START)
                .map(t -> ZonedDateTime.of(t.getTimeTrackingDate(), ZoneId.systemDefault()).toInstant().toEpochMilli())
                .reduce(0L, (total, hour) -> {
                    long now = ZonedDateTime.of(timeTrackingDate, ZoneId.systemDefault()).toInstant().toEpochMilli();
                    return now - (total + hour);
                });
    }

    private List<TimeTrackingRecord> getTodayTimeTrackingRecords(User user, LocalDateTime time) {
        LocalDate date = time.toLocalDate();
        LocalDateTime startDate = date.atStartOfDay();
        LocalDateTime endDate = date.plusDays(1).atStartOfDay();
        return timeTrackingRecordRepository.getByUserAndTimeTrackingDateBetween(user, startDate, endDate);
    }

    private boolean isSaturdayOrSunday(LocalDateTime todayDate) {
        return todayDate.getDayOfWeek() == DayOfWeek.SATURDAY || todayDate.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

}
