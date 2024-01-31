package sd.devicemicroservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sd.devicemicroservice.entities.Device;

import java.util.List;

public interface DeviceRepo extends JpaRepository<Device, Long> {

    List<Device> findAllByUserId(Long userId);

    void deleteAllByUserId(Long userId);
}
