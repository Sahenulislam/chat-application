package com.sahenul.chat_application.auth;

import com.sahenul.chat_application.auth.dto.LoginDto;
import com.sahenul.chat_application.auth.dto.SignupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupDto signupDto) {
        authenticationService.signup(signupDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "User created"));
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        return authenticationService.login(loginDto);
    }

    @PostMapping("/login/google")
    public ResponseEntity<?> loginByGoogle(@RequestBody Map<String, String> request) {
        return authenticationService.loginByGoogle(request);
    }

}
