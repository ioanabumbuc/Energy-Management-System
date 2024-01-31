package sd.devicemicroservice.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeviceMessage {
    private Long userId;
    private Double maxConsumption;

    @Override
    public String toString() {
        return "{" +
                "\"userId\":" + userId +
                ", \"maxConsumption\" :" + maxConsumption +
                '}';
    }
}
