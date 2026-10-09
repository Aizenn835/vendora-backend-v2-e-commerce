package com.codewithlei.vendora.users;

import com.codewithlei.vendora.users.dto.newUser.RequestNewUserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserEntity mapToEntity(RequestNewUserDTO request){
        return UserEntity.builder()
                .username(request.getUsername())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }
}
