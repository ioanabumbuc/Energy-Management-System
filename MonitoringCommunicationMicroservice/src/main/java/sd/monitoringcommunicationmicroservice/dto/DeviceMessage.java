package sd.monitoringcommunicationmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceMessage {
    private Long userId;
    private Double maxConsumption;
}
