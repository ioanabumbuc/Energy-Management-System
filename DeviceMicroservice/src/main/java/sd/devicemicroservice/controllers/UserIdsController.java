package sd.devicemicroservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sd.devicemicroservice.dto.UserDto;
import sd.devicemicroservice.entities.UserId;
import sd.devicemicroservice.service.DeviceService;
import sd.devicemicroservice.service.UserIdService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserIdsController {
    private final UserIdService userIdService;
    private final DeviceService deviceService;

    @Autowired
    public UserIdsController(UserIdService userIdService, DeviceService deviceService) {
        this.userIdService = userIdService;
        this.deviceService = deviceService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserId>> getUserIds(){
        return new ResponseEntity<>(userIdService.getUserIds(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<UserId> addUserId(@RequestBody UserDto userDto){
        return new ResponseEntity<>(userIdService.addUserId(userDto), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUserId(@PathVariable("id") Long id){
        userIdService.deleteByUserId(id);
        deviceService.deleteDeviceByUserId(id);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
