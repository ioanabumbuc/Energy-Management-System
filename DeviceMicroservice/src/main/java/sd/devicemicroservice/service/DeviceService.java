package sd.devicemicroservice.service;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sd.devicemicroservice.dto.DeviceDto;
import sd.devicemicroservice.dto.DeviceMessage;
import sd.devicemicroservice.entities.Device;
import sd.devicemicroservice.rabbitmq.MessageSender;
import sd.devicemicroservice.repo.DeviceRepo;
import sd.devicemicroservice.repo.UserIdRepo;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DeviceService {

    private final DeviceRepo deviceRepo;
    private final UserIdRepo userIdRepo;
    private final MessageSender messageSender;

    @Autowired
    public DeviceService(DeviceRepo deviceRepo, UserIdRepo userIdRepo, MessageSender messageSender) {
        this.deviceRepo = deviceRepo;
        this.userIdRepo = userIdRepo;
        this.messageSender = messageSender;
    }

    public List<Device> getDevices() {
        return deviceRepo.findAll();
    }

    public Device getDeviceById(Long id) {
        return deviceRepo.findById(id).orElse(null);
    }

    public List<Device> getDevicesByUserId(Long id) {
        return deviceRepo.findAllByUserId(id);
    }

    public Optional<Device> addDevice(DeviceDto deviceDto) {
        if (!userIdRepo.existsByUserId(deviceDto.getUserId())) {
            return Optional.empty();
        }
        Device device = Device.builder()
                .address(deviceDto.getAddress())
                .description(deviceDto.getDescription())
                .maxConsumption(deviceDto.getMaxConsumption())
                .userId(deviceDto.getUserId())
                .build();
        DeviceMessage deviceMessage = new DeviceMessage(device.getUserId(), device.getMaxConsumption());
        messageSender.sendMessage(deviceMessage.toString());
        return Optional.of(deviceRepo.save(device));
    }

    public Device updateDevice(Long deviceId, DeviceDto deviceDto) {
        Device device = Device.builder()
                .id(deviceId)
                .address(deviceDto.getAddress())
                .description(deviceDto.getDescription())
                .maxConsumption(deviceDto.getMaxConsumption())
                .userId(deviceDto.getUserId())
                .build();
        DeviceMessage deviceMessage = new DeviceMessage(device.getUserId(), device.getMaxConsumption());
        messageSender.sendMessage(deviceMessage.toString());
        return deviceRepo.save(device);
    }

    public void deleteDeviceById(Long id) {
        deviceRepo.deleteById(id);
    }

    public void deleteDeviceByUserId(Long id) {
        deviceRepo.deleteAllByUserId(id);
    }

    public boolean existsDeviceById(Long id) {
        return deviceRepo.existsById(id);
    }

    @PostConstruct
    public void sendDeviceMaxConsumption() {
        Device device = getDeviceById(1L);
        if (device == null) {
            return;
        }
        DeviceMessage deviceMessage = new DeviceMessage(device.getUserId(), device.getMaxConsumption());
        messageSender.sendMessage(deviceMessage.toString());
    }

}
