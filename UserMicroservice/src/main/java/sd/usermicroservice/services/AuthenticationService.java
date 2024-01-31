package sd.usermicroservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sd.usermicroservice.dto.JwtAuthenticationResponse;
import sd.usermicroservice.dto.LoginDto;
import sd.usermicroservice.dto.UserDto;
import sd.usermicroservice.entities.Role;
import sd.usermicroservice.entities.User;
import sd.usermicroservice.repo.UserRepo;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepo userRepo;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse register(UserDto request) {
        var user = User
                .builder()
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        user = userService.addUser(user);
        var roles = new ArrayList<String>();
        roles.add(Role.ADMIN.name());
        var jwt = jwtService.generateToken(user, roles);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }


    public JwtAuthenticationResponse login(LoginDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getName(), request.getPassword()));
        var user = userRepo.findByName(request.getName())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var roles = new ArrayList<String>();
        roles.add(user.getRole().name());
        var jwt = jwtService.generateToken(user, roles);
        return JwtAuthenticationResponse.builder()
                .token(jwt)
                .userId(user.getId())
                .role(user.getRole())
                .build();
    }

}