package sd.usermicroservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sd.usermicroservice.entities.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    boolean existsByName(String name);

    User getUserByName(String name);

    Optional<User> findByName(String name);

}
