package digital.ilia.toclockinapi.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

public class TimeTrackingRecordRequest {

    @NotNull
    private LocalDateTime timeTrackingDate;

    @Size(min = 3, max = 200)
    private String userMessage;

    public TimeTrackingRecordRequest(LocalDateTime timeTrackingDate, String userMessage) {
        this.timeTrackingDate = timeTrackingDate;
        this.userMessage = userMessage;
    }

    public LocalDateTime getTimeTrackingDate() {
        return timeTrackingDate;
    }

    public void setTimeTrackingDate(LocalDateTime timeTrackingDate) {
        this.timeTrackingDate = timeTrackingDate;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeTrackingRecordRequest that = (TimeTrackingRecordRequest) o;
        return Objects.equals(timeTrackingDate, that.timeTrackingDate) && Objects.equals(userMessage, that.userMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeTrackingDate, userMessage);
    }
}
