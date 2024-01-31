package sd.usermicroservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sd.usermicroservice.dto.JwtAuthenticationResponse;
import sd.usermicroservice.dto.LoginDto;
import sd.usermicroservice.dto.UserDto;
import sd.usermicroservice.entities.User;
import sd.usermicroservice.services.AuthenticationService;
import sd.usermicroservice.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(this.userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(this.userService.getUserById(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addUser(@RequestBody UserDto userDto) {
        if (userService.existsUserByName(userDto.getName())) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
        User newUser = userService.addUser(userDto);

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        JwtAuthenticationResponse response = authenticationService.register(userDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        JwtAuthenticationResponse response = authenticationService.login(loginDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
        if (!userService.existsUserById(id)) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        User updatedUser = userService.updateUser(id, userDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        if (!userService.existsUserById(id)) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        userService.deleteUserById(id);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
