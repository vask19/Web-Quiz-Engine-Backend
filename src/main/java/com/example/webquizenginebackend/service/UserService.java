package com.example.webquizenginebackend.service;

import com.example.webquizenginebackend.dto.UserDTO;
import com.example.webquizenginebackend.entity.Users;
import com.example.webquizenginebackend.facade.UserFacade;
import com.example.webquizenginebackend.payload.request.SignupRequest;
import com.example.webquizenginebackend.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserFacade userFacade;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserFacade userFacade, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userFacade = userFacade;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO findUserByUsername(String username){
        Optional<Users> user = userRepository.findUserByUsername(username);
        return user.map(userFacade::userToUserDTO).orElse(null);



    }


    public Users createUser(SignupRequest userIn){
        Users user = new Users();
        user.setUsername(userIn.getEmail());
        user.setPassword(passwordEncoder.encode(userIn.getPassword()));
        try {
            return userRepository.save(user);
        }catch (Exception e) {
            return null;
        }
    }

}
