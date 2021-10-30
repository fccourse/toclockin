package digital.ilia.toclockinapi.repositories;

import digital.ilia.toclockinapi.entities.TimeTrackingRecord;
import digital.ilia.toclockinapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TimeTrackingRecordRepository extends JpaRepository<TimeTrackingRecord, Long> {

    public List<TimeTrackingRecord> getByUserAndTimeTrackingDateBetween(User user, LocalDateTime from, LocalDateTime to);
}
