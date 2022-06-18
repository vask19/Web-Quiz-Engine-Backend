package com.example.webquizenginebackend.facade;

import com.example.webquizenginebackend.dto.UserDTO;
import com.example.webquizenginebackend.entity.Users;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserFacade {

    public UserDTO userToUserDTO(Users users){
        UserDTO userDTO= new UserDTO();
        userDTO.setPassword(users.getPassword());
        userDTO.setUsername(users.getUsername());

        return userDTO;
    }
}
