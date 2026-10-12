package com.codewithlei.vendora.common.validation;

import com.codewithlei.vendora.seller.exception.InvalidSellerStatusException;
import com.codewithlei.vendora.users.UserEntity;
import com.codewithlei.vendora.users.UserRepository;
import com.codewithlei.vendora.users.enums.Role;
import com.codewithlei.vendora.users.exception.ProfileImageNullException;
import com.codewithlei.vendora.users.exception.UserAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class UserValidation {
    private final UserRepository userRepository;


    public void validateUser(String email){
        if(userRepository.existsByEmail(email)){
            throw new UserAlreadyExistException();
        }
    }
    public void validateRole(UserEntity user){
        if(user.getRole() != Role.SELLER){
            throw new InvalidSellerStatusException("Only approved seller can view seller products.");
        }
    }
}
