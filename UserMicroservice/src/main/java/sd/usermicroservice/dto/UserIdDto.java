package sd.usermicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class UserIdDto {
    private Long id;
    private Long userId;

    public UserIdDto(Long userId) {
        this.userId = userId;
    }

}
