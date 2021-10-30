package digital.ilia.toclockinapi.resources;

import digital.ilia.toclockinapi.dtos.request.TimeTrackingRecordRequest;
import digital.ilia.toclockinapi.services.TimeTrackingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/times")
public class TimeTrackingRecordResource {

    private TimeTrackingRecordService timeTrackingService;

    @Autowired
    public TimeTrackingRecordResource(TimeTrackingRecordService timeTrackingService) {
        this.timeTrackingService = timeTrackingService;
    }

    @PostMapping("/{userId}")
    public String saveTimeTrackingRecord(@Valid @RequestBody TimeTrackingRecordRequest request, @PathVariable("userId") Long userId) {
        timeTrackingService.saveTimeTrackingRecord(request, userId);
        return "Created";
    }


}
