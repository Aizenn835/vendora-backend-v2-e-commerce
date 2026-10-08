package com.codewithlei.vendora.users;

import com.codewithlei.vendora.common.validation.UserValidation;
import com.codewithlei.vendora.security.Authentication.AuthToken;
import com.codewithlei.vendora.security.JwtAuthService;
import com.codewithlei.vendora.users.dto.newUser.RequestNewUserDTO;
import com.codewithlei.vendora.users.enums.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserValidation userValidation;
    private final UserRepository userRepository;
    private final JwtAuthService jwtAuthService;
    private final String defaultPfp;
    private final UserMapper userMapper;

    public UserService(@Value("${app.default.pfp}")String defaultPfp,
                       PasswordEncoder passwordEncoder,
                       UserValidation userValidation,
                       UserRepository userRepository,
                       JwtAuthService jwtAuthService,
                       UserMapper userMapper) {
        this.defaultPfp = defaultPfp;
        this.passwordEncoder = passwordEncoder;
        this.userValidation = userValidation;
        this.userRepository = userRepository;
        this.jwtAuthService = jwtAuthService;
        this.userMapper = userMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public AuthToken register(RequestNewUserDTO request){
        userValidation.validateUser(request.getEmail());

        UserEntity user = userMapper.mapToEntity(request);
        user.setPfpUrl(defaultPfp);
        user.setRole(Role.CUSTOMER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserEntity saved = userRepository.saveAndFlush(user);

        String token = jwtAuthService.generateToken(saved.getEmail() , saved.getRole().name());
        return new AuthToken(token);

    }
}
