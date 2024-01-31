package sd.devicesimulator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sd.devicesimulator.model.Measurement;
import sd.devicesimulator.services.MeasurementService;

@RestController
@RequestMapping("/deviceSimulator")
public class DeviceSimulatorController {


    private final MeasurementService measurementService;

    @Autowired
    public DeviceSimulatorController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }


    @PostMapping("/sendMeasurement")
    public ResponseEntity<Measurement> sendMeasurement(){
        Measurement measurement = measurementService.createMeasurement();

        return new ResponseEntity<>(measurement, HttpStatus.OK);
    }
}
