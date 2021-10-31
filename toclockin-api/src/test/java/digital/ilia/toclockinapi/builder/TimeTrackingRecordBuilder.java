package digital.ilia.toclockinapi.builder;

import digital.ilia.toclockinapi.dtos.request.TimeTrackingRecordRequest;
import digital.ilia.toclockinapi.dtos.response.handler.HandlerTimeTrackingRecord;
import digital.ilia.toclockinapi.services.TimeTrackingRecordService;

import java.time.LocalDateTime;

public class TimeTrackingRecordBuilder {

    private LocalDateTime timeTrackingDate;
    private String userMessage;

    private HandlerTimeTrackingRecord handle;

    public TimeTrackingRecordBuilder(String userMessage) {
        this.userMessage = userMessage;
    }

    public TimeTrackingRecordBuilder buildHourEntry(int year, int month, int monthDay) {
        this.timeTrackingDate = LocalDateTime.of(year, month, monthDay, 9, 10);
        return this;
    }

    public TimeTrackingRecordBuilder buildHourLunch(int year, int month, int monthDay) {
        this.timeTrackingDate = LocalDateTime.of(year, month, monthDay, 12, 0);
        return this;
    }

    public TimeTrackingRecordBuilder buildHourLunchReturn(int year, int month, int monthDay) {
        this.timeTrackingDate = LocalDateTime.of(year, month, monthDay, 13, 10);
        return this;
    }

    public TimeTrackingRecordBuilder buildHourExit(int year, int month, int monthDay) {
        this.timeTrackingDate = LocalDateTime.of(year, month, monthDay, 18, 5);
        return this;
    }

    public TimeTrackingRecordBuilder entry() {
        this.timeTrackingDate = LocalDateTime.of(2021, 10, 29, 9, 10);
        return this;
    }

    public TimeTrackingRecordBuilder lunch() {
        this.timeTrackingDate = LocalDateTime.of(2021, 10, 29, 12, 10);
        return this;
    }

    public TimeTrackingRecordBuilder lunchReturn() {
        this.timeTrackingDate = LocalDateTime.of(2021, 10, 29, 13, 30);
        return this;
    }

    public TimeTrackingRecordBuilder exit() {
        this.timeTrackingDate = LocalDateTime.of(2021, 10, 29, 18, 15);
        return this;
    }

    public TimeTrackingRecordBuilder saveTimeTrackingRecordBuilder(Long userId, TimeTrackingRecordService service) {
        this.handle = service.saveTimeTrackingRecord(new TimeTrackingRecordRequest(this.timeTrackingDate, this.userMessage), userId);
        return this;
    }

    public HandlerTimeTrackingRecord builder() {
        return new HandlerTimeTrackingRecord(this.handle);
    }
}
