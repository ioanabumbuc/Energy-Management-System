package sd.devicemicroservice.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sd.devicemicroservice.dto.UserDto;
import sd.devicemicroservice.entities.UserId;
import sd.devicemicroservice.repo.UserIdRepo;

import java.util.List;

@Service
@Transactional
public class UserIdService {
    private final UserIdRepo userIdRepo;

    @Autowired
    public UserIdService(UserIdRepo userIdRepo) {
        this.userIdRepo = userIdRepo;
    }

    public List<UserId> getUserIds(){
        return userIdRepo.findAll();
    }

    public UserId addUserId(UserDto userDto){
        UserId user = UserId.builder()
                .userId(userDto.getUserId())
                .build();
        return userIdRepo.save(user);
    }

    public void deleteByUserId(Long id){
        userIdRepo.deleteByUserId(id);
    }

}
