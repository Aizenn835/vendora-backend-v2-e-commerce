package com.codewithlei.vendora.users;

import com.codewithlei.vendora.common.storage.ImageService;
import com.codewithlei.vendora.common.validation.UserValidation;
import com.codewithlei.vendora.security.Authentication.AuthToken;
import com.codewithlei.vendora.security.JwtAuthService;
import com.codewithlei.vendora.users.dto.newUser.RequestNewUserDTO;
import com.codewithlei.vendora.users.enums.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserValidation userValidation;
    private final UserRepository userRepository;
    private final JwtAuthService jwtAuthService;
    private final ImageService imageService;
    private final UserMapper userMapper;

    @Transactional(rollbackFor = Exception.class)
    public AuthToken register(MultipartFile file , RequestNewUserDTO request)throws IOException {
        userValidation.validateProfile(file);
        userValidation.validateUser(request.getEmail());
        String image = imageService.uploadPfp(file , "profile");

        try{
            UsersEntity user = userMapper.mapToEntity(request , image);
            user.setRoles(Roles.CUSTOMER);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            UsersEntity saved = userRepository.saveAndFlush(user);

            String token = jwtAuthService.generateToken(saved.getEmail() , saved.getRoles().name());
            return new AuthToken(token);
        }catch(RuntimeException e){
            imageService.deleteImage(image);
            throw e;
        }
    }
}
