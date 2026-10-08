package com.codewithlei.vendora.security;

import com.codewithlei.vendora.users.UserRepository;
import com.codewithlei.vendora.users.UserEntity;
import com.codewithlei.vendora.users.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
