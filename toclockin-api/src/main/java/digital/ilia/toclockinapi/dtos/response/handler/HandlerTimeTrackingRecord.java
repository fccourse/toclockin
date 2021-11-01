package digital.ilia.toclockinapi.dtos.response.handler;

import digital.ilia.toclockinapi.dtos.response.ResponseTimeTrackingRecord;
import digital.ilia.toclockinapi.entities.TimeTrackingRecord;
import digital.ilia.toclockinapi.exceptions.UnknownTypeTimeTrackingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HandlerTimeTrackingRecord {

    private String message;
    private HandlerTimeTrackingType type;
    private TimeTrackingRecord timeTrackingRecord;

    public HandlerTimeTrackingRecord() {
    }

    public HandlerTimeTrackingRecord(HandlerTimeTrackingRecord handle) {
        this.message = handle.getMessage();
        this.type = handle.getType();
        this.timeTrackingRecord = handle.getTimeTrackingRecord();
    }

    public HandlerTimeTrackingRecord(String message, HandlerTimeTrackingType type) {
        this.message = message;
        this.type = type;
    }

    public HandlerTimeTrackingRecord(String message, HandlerTimeTrackingType type, TimeTrackingRecord timeTrackingRecord) {
        this.message = message;
        this.type = type;
        this.timeTrackingRecord = timeTrackingRecord;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HandlerTimeTrackingType getType() {
        return type;
    }

    public void setType(HandlerTimeTrackingType type) {
        this.type = type;
    }

    public TimeTrackingRecord getTimeTrackingRecord() {
        return timeTrackingRecord;
    }

    public void setTimeTrackingRecord(TimeTrackingRecord timeTrackingRecord) {
        this.timeTrackingRecord = timeTrackingRecord;
    }

    public ResponseEntity responseValidationHandleType() {
        ResponseTimeTrackingRecord response = new ResponseTimeTrackingRecord(this.getMessage());
        if (this.getType() == HandlerTimeTrackingType.CREATED) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else if (this.getType() == HandlerTimeTrackingType.ONE_HOUR_LUNCH) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } else if (this.getType() == HandlerTimeTrackingType.ALREADY_DAY) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } else if (this.getType() == HandlerTimeTrackingType.NOT_WEEKENDS) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } else if (this.getType() == HandlerTimeTrackingType.UNEXPECTED_OPERATION) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else {
            throw new UnknownTypeTimeTrackingException("The assigned type could not be identified.");
        }
    }
}