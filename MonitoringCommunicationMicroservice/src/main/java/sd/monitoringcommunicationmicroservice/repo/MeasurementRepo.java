package sd.monitoringcommunicationmicroservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sd.monitoringcommunicationmicroservice.entities.Measurement;

import java.util.List;

public interface MeasurementRepo extends JpaRepository<Measurement, Long> {
    List<Measurement> findByDeviceId(Long deviceId);

    void deleteByDeviceId(Long deviceId);

    void deleteByTimestampAndDeviceId(String timestamp, Long deviceId);

}
