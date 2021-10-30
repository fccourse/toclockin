package digital.ilia.toclockinapi.services;

import digital.ilia.toclockinapi.dto.request.TimeTrackingRecordRequest;
import digital.ilia.toclockinapi.entities.TimeTrackingRecord;
import digital.ilia.toclockinapi.entities.User;
import digital.ilia.toclockinapi.entities.enums.TimeTrackingType;
import digital.ilia.toclockinapi.repositories.TimeTrackingRecordRepository;
import digital.ilia.toclockinapi.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void saveTimeTrackingRecord(TimeTrackingRecordRequest request, Long userId) {
        var todayDate = request.getTimeTrackingDate();

        if (isSaturdayOrSunday(todayDate)) {
            logger.info("It is not possible to register the point on weekends.");
        } else {
            User user = userRepository.findById(userId).get();
            List<TimeTrackingRecord> todayTimeTrackings = getTodayTimeTrackingRecords(user, request.getTimeTrackingDate());

            if (todayTimeTrackings.size() >= 4) {
                logger.info("You have already registered all the points of the day.");
            } else {
                validateTimeTrackingRecord(request, user, todayTimeTrackings);
            }
        }
    }

    private void validateTimeTrackingRecord(TimeTrackingRecordRequest request, User user, List<TimeTrackingRecord> todayTimeTrackings) {
        var typeNumber = todayTimeTrackings.size();

        switch (typeNumber) {
            case 0:
                saveTimeTrackingRecordByType(request, user, TimeTrackingType.ENTRY);
                break;
            case 1:
                saveTimeTrackingRecordByType(request, user, TimeTrackingType.LUNCH_START);
                break;
            case 2:
                Long timeDifferenceLunch = timeDifferenceLunch(todayTimeTrackings, request.getTimeTrackingDate());
                if (timeDifferenceLunch <= 3600000) {
                    logger.info("There should be a one-hour lunch break.");
                } else {
                    saveTimeTrackingRecordByType(request, user, TimeTrackingType.LUNCH_RETURN);
                }
                break;
            case 3:
                saveTimeTrackingRecordByType(request, user, TimeTrackingType.EXIT);
                break;
            default:
                logger.info("Unexpected operation.");
        }
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
                    Long now = ZonedDateTime.of(timeTrackingDate, ZoneId.systemDefault()).toInstant().toEpochMilli();
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
