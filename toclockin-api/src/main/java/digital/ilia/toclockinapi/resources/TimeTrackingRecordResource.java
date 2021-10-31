package digital.ilia.toclockinapi.resources;

import digital.ilia.toclockinapi.dtos.request.TimeTrackingRecordRequest;
import digital.ilia.toclockinapi.dtos.response.handler.HandlerTimeTrackingRecord;
import digital.ilia.toclockinapi.services.TimeTrackingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/hits")
public class TimeTrackingRecordResource {

    private TimeTrackingRecordService timeTrackingService;

    @Autowired
    public TimeTrackingRecordResource(TimeTrackingRecordService timeTrackingService) {
        this.timeTrackingService = timeTrackingService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity saveTimeTrackingRecord(@Valid @RequestBody TimeTrackingRecordRequest request, @PathVariable("userId") Long userId) {
        HandlerTimeTrackingRecord handle = timeTrackingService.saveTimeTrackingRecord(request, userId);
        return handle.responseValidationHandleType();
    }

}
