package sd.monitoringcommunicationmicroservice.services;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendMessageToUser(Long userId, String message) {
        String destination = "/notifications/" + userId;
        System.out.println(destination + message);
        messagingTemplate.convertAndSend(destination, message);
    }

}
