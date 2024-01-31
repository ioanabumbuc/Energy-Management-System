package sd.monitoringcommunicationmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MeasurementDto {
    private String timestamp;
    private Long deviceId;
    private Double measurementValue;
}
