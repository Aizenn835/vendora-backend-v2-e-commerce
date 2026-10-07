package com.codewithlei.vendora.security.Authentication;

import com.codewithlei.vendora.users.dto.userData.RequestLoginDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthToken> login(@RequestBody @Valid RequestLoginDTO request){
        return ResponseEntity.ok(authService.login(request));
    }

}
