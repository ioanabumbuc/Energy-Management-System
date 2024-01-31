package sd.monitoringcommunicationmicroservice.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sd.monitoringcommunicationmicroservice.dto.DeviceMessage;
import sd.monitoringcommunicationmicroservice.dto.MeasurementDto;
import sd.monitoringcommunicationmicroservice.entities.Measurement;
import sd.monitoringcommunicationmicroservice.repo.MeasurementRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MeasurementService {
    private final MeasurementRepo measurementRepo;
    private final WebSocketService webSocketService;
    private static Double lastMeasuredValue = 0.0;
    private static int measurementCount = 0;
    private Double maxConsumption = 0.0;
    private Long userId = -1L;


    @Autowired
    public MeasurementService(MeasurementRepo measurementRepo, WebSocketService webSocketService) {
        this.measurementRepo = measurementRepo;
        this.webSocketService = webSocketService;
    }

    public void readMeasurement(MeasurementDto measurementDto) {
        Measurement measurement = Measurement.builder()
                .timestamp(measurementDto.getTimestamp())
                .deviceId(measurementDto.getDeviceId())
                .consumedEnergy(measurementDto.getMeasurementValue())
                .build();

        if (measurementDto.getMeasurementValue() == 0.0) {
            measurementCount = 0;
        }

        if (measurementCount == 0) {
            lastMeasuredValue = measurementDto.getMeasurementValue();
        }

        if (measurementCount < 5) {
            measurementCount++;
            return;
        }

        double consumedEnergy = measurementDto.getMeasurementValue() - lastMeasuredValue;

        if (consumedEnergy < 0) {
            measurement.setConsumedEnergy(lastMeasuredValue);
        } else {
            measurement.setConsumedEnergy(consumedEnergy);
        }

        if (this.maxConsumption != 0.0
                && this.maxConsumption < measurementDto.getMeasurementValue() - lastMeasuredValue) {
            this.webSocketService.sendMessageToUser(userId,
                    "WARNING - MAX CONSUMPTION (" + this.maxConsumption + ") EXCEEDED!");
        }

        System.out.println("added measurement, last val = " + lastMeasuredValue +
                " measurementCount = " + measurementCount + " measurement: " + measurement);

        lastMeasuredValue = measurementDto.getMeasurementValue();
        measurementCount = 0;

        this.addMeasurement(measurement);
    }

    public void addMeasurement(Measurement measurement) {
        measurementRepo.save(measurement);
    }

    public void readDeviceMessage(DeviceMessage deviceMessage) {
        this.maxConsumption = deviceMessage.getMaxConsumption();
        this.userId = deviceMessage.getUserId();
    }

    public List<Measurement> getMeasurementsByDeviceID(Long deviceId) {
        return measurementRepo.findByDeviceId(deviceId);
    }

    public List<Measurement> getMeasurementsByDate(String date, Long deviceId) {
        return getMeasurementsByDeviceID(deviceId).stream()
                .filter(measurement -> {
                    String measurementDate = measurement.getTimestamp().split(" ")[0];
                    return date.equals(measurementDate);
                })
                .collect(Collectors.toList());
    }

    public void deleteMeasurementsByDeviceId(Long deviceId) {
        measurementRepo.deleteByDeviceId(deviceId);
    }

    public void deleteMeasurementsByDeviceIdAndTimestamp(Long deviceId, String timestamp) {
        measurementRepo.deleteByTimestampAndDeviceId(timestamp, deviceId);
    }

}
