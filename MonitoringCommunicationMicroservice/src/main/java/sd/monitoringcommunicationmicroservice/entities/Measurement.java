package sd.monitoringcommunicationmicroservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "timestamp", nullable = false)
    private String timestamp;
    @Column(name = "deviceId", nullable = false)
    private Long deviceId;
    @Column(name = "measurementValue", nullable = false)
    private Double consumedEnergy;

    @Override
    public String toString() {
        return "Measurement{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", deviceId=" + deviceId +
                ", measurementValue=" + consumedEnergy +
                '}';
    }
}
