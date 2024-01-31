package sd.monitoringcommunicationmicroservice.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sd.monitoringcommunicationmicroservice.dto.DeviceMessage;
import sd.monitoringcommunicationmicroservice.dto.MeasurementDto;
import sd.monitoringcommunicationmicroservice.entities.Measurement;
import sd.monitoringcommunicationmicroservice.services.MeasurementService;

@Component
public class MessageReader {
    private final MeasurementService measurementService;

    @Autowired
    public MessageReader(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @RabbitListener(queues = "queue")
    public void listenForMeasurements(String message) {
        System.out.println("Message read from queue: " + message);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            MeasurementDto dto = objectMapper.readValue(message, MeasurementDto.class);

            measurementService.readMeasurement(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "deviceQueue")
    public void listenForDevice(String message) {
        System.out.println("Message read from deviceQueue: " + message);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            DeviceMessage deviceMessage = objectMapper.readValue(message, DeviceMessage.class);

            measurementService.readDeviceMessage(deviceMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
