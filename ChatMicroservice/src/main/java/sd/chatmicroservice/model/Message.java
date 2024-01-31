package sd.chatmicroservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Message {
    private String senderId;
    private String receiverId;
    private String message;
    private Status status;
}