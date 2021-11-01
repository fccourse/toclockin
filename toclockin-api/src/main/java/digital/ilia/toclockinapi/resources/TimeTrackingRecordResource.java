package digital.ilia.toclockinapi.resources;

import digital.ilia.toclockinapi.dtos.request.TimeTrackingRecordRequest;
import digital.ilia.toclockinapi.dtos.response.handler.HandlerTimeTrackingRecord;
import digital.ilia.toclockinapi.services.TimeTrackingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/batidas")
public class TimeTrackingRecordResource {

    private TimeTrackingRecordService timeTrackingService;

    @Autowired
    public TimeTrackingRecordResource(TimeTrackingRecordService timeTrackingService) {
        this.timeTrackingService = timeTrackingService;
    }

    @PostMapping
    public ResponseEntity saveTimeTrackingRecord(@RequestParam String email, @Valid @RequestBody TimeTrackingRecordRequest request) {
        HandlerTimeTrackingRecord handle = timeTrackingService.saveTimeTrackingRecord(request, email);
        return handle.responseValidationHandleType();
    }

}
