package digital.ilia.toclockinapi.repositories;

import digital.ilia.toclockinapi.entities.TimeTrackingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeTrackingRecordRepository extends JpaRepository<Long, TimeTrackingRecord> {
}
