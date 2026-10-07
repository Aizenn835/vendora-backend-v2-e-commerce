package com.codewithlei.vendora.users;

import com.codewithlei.vendora.users.dto.newUser.RequestNewUserDTO;
import com.codewithlei.vendora.users.enums.Roles;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UsersEntity mapToEntity(RequestNewUserDTO request , String pfpImg){
        return UsersEntity.builder()
                .pfpUrl(pfpImg)
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }
}
