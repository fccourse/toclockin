package digital.ilia.toclockinapi.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

public class TimeTrackingRecordRequest {

    @NotNull
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("dataHora")
    private LocalDateTime dataHora;

    @Size(min = 3, max = 200)
    private String userMessage;

    public TimeTrackingRecordRequest(LocalDateTime timeTrackingDate, String userMessage) {
        this.dataHora = timeTrackingDate;
        this.userMessage = userMessage;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
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
        return Objects.equals(dataHora, that.dataHora) && Objects.equals(userMessage, that.userMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataHora, userMessage);
    }
}
