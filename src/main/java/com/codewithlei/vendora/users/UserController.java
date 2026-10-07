package com.codewithlei.vendora.users;

import com.codewithlei.vendora.security.Authentication.AuthToken;
import com.codewithlei.vendora.users.dto.newUser.RequestNewUserDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(value = "/register" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AuthToken> register(@RequestPart("dto") @Valid RequestNewUserDTO request,
                                              @RequestPart("image") MultipartFile file) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.register(file , request));
    }
}
