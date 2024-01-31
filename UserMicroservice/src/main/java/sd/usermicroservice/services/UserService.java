package sd.usermicroservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sd.usermicroservice.dto.LoginDto;
import sd.usermicroservice.dto.UserDto;
import sd.usermicroservice.entities.User;
import sd.usermicroservice.repo.UserRepo;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;


    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepo.findByName(username).orElseThrow(
                        () -> new UsernameNotFoundException("Username not found."));
            }
        };
    }

    public List<User> getUsers() {
        return userRepo.findAll();
    }

    public User getUserById(Long id) {
        return userRepo.findById(id).orElse(null);
    }


    public User addUser(UserDto userDto) {
        User user = User
                .builder()
                .name(userDto.getName())
                .role(userDto.getRole())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();
        return userRepo.save(user);
    }

    public User addUser(User user) {
        return userRepo.save(user);
    }

    public User updateUser(Long userId, UserDto userDto) {
        User user = User
                .builder()
                .name(userDto.getName())
                .role(userDto.getRole())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .id(userId)
                .build();
        return userRepo.save(user);
    }

    public void deleteUserById(Long id) {
        userRepo.deleteById(id);
    }

    public Optional<User> login(LoginDto loginDto) {
        User user = userRepo.getUserByName(loginDto.getName());
        if (user == null) {
            return Optional.empty();
        }
        if (!user.checkPassword(loginDto.getPassword())) {
            return Optional.empty();
        }
        return Optional.of(user);

    }

    public boolean existsUserById(Long id) {
        return userRepo.existsById(id);
    }

    public boolean existsUserByName(String name) {
        return userRepo.existsByName(name);
    }


}
