package sd.devicesimulator.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sd.devicesimulator.model.Measurement;
import sd.devicesimulator.rabbitmq.MessageSender;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

@Service
public class MeasurementService {
    private final MessageSender messageSender;
    private final String sensorFilePath = "C:/Users/ioana/Documents/an4/sem1/DS/ds2023_30444_bumbuc_ioana_assignment_1/sensor.csv";
    private final LocalDateTime timestamp = new Timestamp(System.currentTimeMillis()).toLocalDateTime();
    private final Long deviceId = 4L;
    private static int entryNb = 0;
   private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Autowired
    public MeasurementService(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Scheduled(fixedRate = 300)
    public void sendMeasurement() {
        try (BufferedReader br = new BufferedReader(new FileReader(sensorFilePath))) {

            String line = Files.readAllLines(Paths.get(sensorFilePath)).get(entryNb);

            entryNb++;

            if (line != null) {
                Double sensorValue = Double.parseDouble(line);
                String newTimestamp =
                        timestamp.plusMinutes(10L * entryNb).format(formatter); //.toString();

                Measurement measurement = new Measurement(newTimestamp, deviceId, sensorValue);
                //System.out.println(measurement);
                messageSender.sendMessage(measurement.toString());
            } else {
                System.out.println("End of file reached. No more measurements to send.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Measurement createMeasurement() {
        Measurement measurement = new Measurement(1L, 40.0);
        messageSender.sendMessage(measurement.toString());
        return measurement;
    }

}
