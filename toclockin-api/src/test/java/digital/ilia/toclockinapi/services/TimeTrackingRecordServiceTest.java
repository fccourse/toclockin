package digital.ilia.toclockinapi.services;

import digital.ilia.toclockinapi.dtos.request.TimeTrackingRecordRequest;
import digital.ilia.toclockinapi.entities.TimeTrackingRecord;
import digital.ilia.toclockinapi.entities.User;
import digital.ilia.toclockinapi.repositories.TimeTrackingRecordRepository;
import digital.ilia.toclockinapi.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class TimeTrackingRecordServiceTest {

    @Autowired
    private TimeTrackingRecordService service;

    @Autowired
    private TimeTrackingRecordRepository timeTrackingRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveTimeTrackingRecordTest() {
        TimeTrackingRecordRequest requestEntry = new TimeTrackingRecordRequest(LocalDateTime.of(2021, 10, 29, 10, 0), "Entry");
        service.saveTimeTrackingRecord(requestEntry, 1L);

        TimeTrackingRecordRequest requestLunchStart = new TimeTrackingRecordRequest(LocalDateTime.of(2021, 10, 29, 12, 30), "Lunch");
        service.saveTimeTrackingRecord(requestLunchStart, 1L);

        TimeTrackingRecordRequest requestLunchReturn = new TimeTrackingRecordRequest(LocalDateTime.of(2021, 10, 29, 13, 40), "Lunch Return");
        service.saveTimeTrackingRecord(requestLunchReturn, 1L);

        TimeTrackingRecordRequest requestExit = new TimeTrackingRecordRequest(LocalDateTime.of(2021, 10, 29, 18, 10), "Exit");
        service.saveTimeTrackingRecord(requestExit, 1L);

        User user = userRepository.findById(1L).get();
        LocalDate date = requestEntry.getTimeTrackingDate().toLocalDate();
        LocalDateTime startDate = date.atStartOfDay();
        LocalDateTime endDate = date.plusDays(1).atStartOfDay();
        List<TimeTrackingRecord> todayTimeTrackings = timeTrackingRepository.getByUserAndTimeTrackingDateBetween(user, startDate, endDate);

        Assertions.assertTrue(todayTimeTrackings.size() > 0 && todayTimeTrackings.size() <= 4);
    }
}