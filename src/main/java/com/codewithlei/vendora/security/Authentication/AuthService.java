package com.codewithlei.vendora.security.Authentication;

import com.codewithlei.vendora.security.JwtAuthService;
import com.codewithlei.vendora.users.UserRepository;
import com.codewithlei.vendora.users.UsersEntity;
import com.codewithlei.vendora.users.dto.userData.RequestLoginDTO;
import com.codewithlei.vendora.users.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtAuthService jwtAuthService;
    private final AuthenticationManager authenticationManager;


    @Transactional(rollbackFor = Exception.class)
    public AuthToken login(RequestLoginDTO request){
        UsersEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(UserNotFoundException::new);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
              request.getEmail(), request.getPassword()
        ));
        String token = jwtAuthService.generateToken(user.getEmail() ,
                                                    user.getRoles().name());
        return new AuthToken(token);
    }
}
