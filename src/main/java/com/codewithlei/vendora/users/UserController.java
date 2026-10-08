package com.codewithlei.vendora.users;

import com.codewithlei.vendora.security.Authentication.AuthToken;
import com.codewithlei.vendora.users.dto.newUser.RequestNewUserDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthToken> register(@RequestBody @Valid RequestNewUserDTO request) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.register(request));
    }
}
