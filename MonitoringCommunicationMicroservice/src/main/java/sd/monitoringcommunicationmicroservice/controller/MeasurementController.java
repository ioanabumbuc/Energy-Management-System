package sd.monitoringcommunicationmicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sd.monitoringcommunicationmicroservice.dto.DeviceDateDto;
import sd.monitoringcommunicationmicroservice.entities.Measurement;
import sd.monitoringcommunicationmicroservice.services.MeasurementService;
import sd.monitoringcommunicationmicroservice.services.WebSocketService;

import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {

    private final MeasurementService measurementService;
    private final WebSocketService webSocketService;

    @Autowired
    public MeasurementController(MeasurementService measurementService, WebSocketService webSocketService) {
        this.measurementService = measurementService;
        this.webSocketService = webSocketService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Measurement>> getMeasurementsByDeviceID(@PathVariable("id") Long deviceId){
        List<Measurement> measurements = this.measurementService.getMeasurementsByDeviceID(deviceId);
        return new ResponseEntity<>(measurements, HttpStatus.OK);
    }

    @PostMapping("/date")
    public ResponseEntity<List<Measurement>> getMeasurementsByDate(@RequestBody DeviceDateDto deviceDateDto){
        List<Measurement> measurements =
                this.measurementService.getMeasurementsByDate(deviceDateDto.getDate(), deviceDateDto.getDeviceId());
        return new ResponseEntity<>(measurements, HttpStatus.OK);
    }

    @GetMapping("/notification")
    public ResponseEntity<?> sendNotification(){
        this.webSocketService.sendMessageToUser(5L, "This is a notification example");
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMeasurementsByDeviceId(@PathVariable("id")Long deviceId){
        measurementService.deleteMeasurementsByDeviceId(deviceId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteMeasurementsByDeviceAndTimestamp(@RequestBody DeviceDateDto deviceDateDto){
        measurementService.deleteMeasurementsByDeviceIdAndTimestamp(deviceDateDto.getDeviceId(), deviceDateDto.getDate());
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
