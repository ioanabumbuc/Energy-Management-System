package sd.devicemicroservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sd.devicemicroservice.dto.DeviceDto;
import sd.devicemicroservice.entities.Device;
import sd.devicemicroservice.service.DeviceService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/devices")
public class DeviceController {
    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Device>> getDevices() {
        return new ResponseEntity<>(this.deviceService.getDevices(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(this.deviceService.getDeviceById(id), HttpStatus.OK);
    }

    @GetMapping("/user={id}")
    public ResponseEntity<List<Device>> getDevicesByUserId(@PathVariable("id") Long id) {
        return new ResponseEntity<>(this.deviceService.getDevicesByUserId(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Device> addDevice(@RequestBody DeviceDto deviceDto) {
        Optional<Device> newDevice = deviceService.addDevice(deviceDto);
        if(newDevice.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(newDevice.get(), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDevice(@PathVariable("id") Long id, @RequestBody DeviceDto deviceDto) {
        if (!deviceService.existsDeviceById(id)) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        Device updatedDevice = deviceService.updateDevice(id, deviceDto);
        return new ResponseEntity<>(updatedDevice, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDevice(@PathVariable("id") Long id) {
        if (!deviceService.existsDeviceById(id)) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        deviceService.deleteDeviceById(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/sendMaxConsumption")
    public ResponseEntity<?> sendDeviceMaxConsumption(){
        deviceService.sendDeviceMaxConsumption();
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
