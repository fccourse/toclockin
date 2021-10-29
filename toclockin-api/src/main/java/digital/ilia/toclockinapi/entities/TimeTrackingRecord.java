package digital.ilia.toclockinapi.entities;

import digital.ilia.toclockinapi.entities.enums.TimeTrackingType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "time_tracking_record")
public class TimeTrackingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timeTrackingDate;

    @Enumerated(EnumType.STRING)
    private TimeTrackingType timeTrackingType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public TimeTrackingRecord() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimeTrackingDate() {
        return timeTrackingDate;
    }

    public void setTimeTrackingDate(LocalDateTime timeTrackingDate) {
        this.timeTrackingDate = timeTrackingDate;
    }

    public TimeTrackingType getTimeTrackingType() {
        return timeTrackingType;
    }

    public void setTimeTrackingType(TimeTrackingType timeTrackingType) {
        this.timeTrackingType = timeTrackingType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeTrackingRecord that = (TimeTrackingRecord) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TimeTrackingRecord{" +
                "id=" + id +
                ", timeTrackingDate=" + timeTrackingDate +
                ", timeTrackingType=" + timeTrackingType +
                ", user=" + user +
                '}';
    }
}
