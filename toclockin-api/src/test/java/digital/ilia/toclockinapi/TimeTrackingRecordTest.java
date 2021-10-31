package digital.ilia.toclockinapi;

import digital.ilia.toclockinapi.builder.TimeTrackingRecordBuilder;
import digital.ilia.toclockinapi.dtos.response.handler.HandlerTimeTrackingRecord;
import digital.ilia.toclockinapi.dtos.response.handler.HandlerTimeTrackingType;
import digital.ilia.toclockinapi.entities.TimeTrackingRecord;
import digital.ilia.toclockinapi.entities.User;
import digital.ilia.toclockinapi.repositories.TimeTrackingRecordRepository;
import digital.ilia.toclockinapi.repositories.UserRepository;
import digital.ilia.toclockinapi.services.TimeTrackingRecordService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TimeTrackingRecordTest {

    @Autowired
    private TimeTrackingRecordService timeTrackingRecordService;

    @Autowired
    private TimeTrackingRecordRepository timeTrackingRepository;

    @Autowired
    private UserRepository userRepository;

    private User userEmployee;

    private User userManager;

    @BeforeAll
    public void init() {
        userEmployee = userRepository
                .findById(1L)
                .orElse(null);

        userManager = userRepository
                .findById(2L)
                .orElse(null);
    }

    @Test
    @Order(1)
    void saveTimeTrackingRecordTest() {
        HandlerTimeTrackingRecord handle = new TimeTrackingRecordBuilder("Entry")
                .buildHourEntry(2021, 10, 29)
                .saveTimeTrackingRecordBuilder(userManager.getId(), timeTrackingRecordService)
                .builder();

        var timeTracking = timeTrackingRepository.findById(handle.getTimeTrackingRecord().getId());
        Assertions.assertTrue(timeTracking.isPresent());
    }

    @Test
    @Order(2)
    void fullFlow_SaveTimeTrackingRecordTest() {
        HandlerTimeTrackingRecord handle = new TimeTrackingRecordBuilder("Entry")
                .entry().saveTimeTrackingRecordBuilder(userEmployee.getId(), timeTrackingRecordService)
                .lunch().saveTimeTrackingRecordBuilder(userEmployee.getId(), timeTrackingRecordService)
                .lunchReturn().saveTimeTrackingRecordBuilder(userEmployee.getId(), timeTrackingRecordService)
                .exit().saveTimeTrackingRecordBuilder(userEmployee.getId(), timeTrackingRecordService)
                .builder();

        List<TimeTrackingRecord> todayTimeTrackings = getTodayTimeTrackingRecords(userEmployee, handle.getTimeTrackingRecord().getTimeTrackingDate());
        Assertions.assertEquals(4, todayTimeTrackings.size());
    }

    @Test
    @Order(3)
    void maxFourHours_SaveTimeTrackingRecordTest() {
        HandlerTimeTrackingRecord handle = new TimeTrackingRecordBuilder("Entry")
                .entry()
                .saveTimeTrackingRecordBuilder(userEmployee.getId(), timeTrackingRecordService)
                .builder();
        Assertions.assertTrue(handle.getType() == HandlerTimeTrackingType.ALREADY_DAY);
    }

    @Test
    @Order(4)
    void lunchTimeOneHour_SaveTimeTrackingRecordTest() {
        HandlerTimeTrackingRecord handle = new TimeTrackingRecordBuilder("Entry")
                .entry()
                .saveTimeTrackingRecordBuilder(userEmployee.getId(), timeTrackingRecordService)
                .builder();

        Assertions.assertTrue(handle.getType() == HandlerTimeTrackingType.ALREADY_DAY);
    }

    @Test
    @Order(4)
    void weekendRule_SaveTimeTrackingRecordTest() {
        HandlerTimeTrackingRecord handle = new TimeTrackingRecordBuilder("Entry")
                .buildHourEntry(2021, 10, 31)
                .saveTimeTrackingRecordBuilder(userEmployee.getId(), timeTrackingRecordService)
                .builder();

        Assertions.assertTrue(handle.getType() == HandlerTimeTrackingType.NOT_WEEKENDS);
    }

    private List<TimeTrackingRecord> getTodayTimeTrackingRecords(User user, LocalDateTime time) {
        LocalDate date = time.toLocalDate();
        LocalDateTime startDate = date.atStartOfDay();
        LocalDateTime endDate = date.plusDays(1).atStartOfDay();
        return timeTrackingRepository.getByUserAndTimeTrackingDateBetween(user, startDate, endDate);
    }
}