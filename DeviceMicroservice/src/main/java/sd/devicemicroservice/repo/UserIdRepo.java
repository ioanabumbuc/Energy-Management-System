package sd.devicemicroservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sd.devicemicroservice.entities.UserId;

public interface UserIdRepo extends JpaRepository<UserId, Long> {
    void deleteByUserId(Long id);

    boolean existsByUserId(Long userId);
}
