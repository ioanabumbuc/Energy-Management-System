package sd.usermicroservice.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sd.usermicroservice.entities.Role;
import sd.usermicroservice.entities.User;
import sd.usermicroservice.repo.UserRepo;
import sd.usermicroservice.services.UserService;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeedDataConfig implements CommandLineRunner {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {

        if (userRepo.count() == 0) {

            User admin = User
                    .builder()
                    .name("admin")
                    .password(passwordEncoder.encode("123"))
                    .role(Role.ADMIN)
                    .build();

            userService.addUser(admin);
            log.debug("created ADMIN user - {}", admin);
        }
    }

}