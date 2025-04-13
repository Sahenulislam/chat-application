package com.sahenul.chat_application.auth;


import com.sahenul.chat_application.auth.dto.LoginDto;
import com.sahenul.chat_application.auth.dto.SignupDto;
import com.sahenul.chat_application.user.User;
import com.sahenul.chat_application.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<?> loginByGoogle(Map<String, String> request) {

        String accessToken = request.get("accessToken");

        String url = "https://oauth2.googleapis.com/tokeninfo?id_token=" + accessToken;

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Map> response = null;

        try {
            response = restTemplate.exchange(url, HttpMethod.GET, null, Map.class);

        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid Google access token"));
        }

        Map<String, Object> googleUser = new HashMap<>((Map<String, Object>) response.getBody());

        String email = (String) googleUser.get("email");
        String name = (String) googleUser.get("name");

        String jwtToken = jwtUtil.generateToken(email);

        return ResponseEntity.ok(Map.of(
                "email", email,
                "name", name,
                "token", jwtToken
        ));
    }

    public ResponseEntity<?> login(LoginDto loginDto) {

        if (loginDto.getEmail() == null) {
            throw new ArithmeticException("Email must not be null");
        }
        if (loginDto.getPassword() == null) {
            throw new ArithmeticException("Password must not be null");
        }

        User user = userService.findByEmail(loginDto.getEmail());

        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("message", "User not found"));
        }

        if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            loginDto.setJwtToken(jwtUtil.generateToken(user.getEmail()));
            return ResponseEntity.ok(loginDto);
        } else {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid password"));
        }
    }

    public void signup(SignupDto signupDto) {

        if (signupDto.getEmail() == null) {
            throw new ArithmeticException("Email must not be null");
        }
        if (signupDto.getPassword() == null) {
            throw new ArithmeticException("Password must not be null");
        }
        if (signupDto.getUserName() == null) {
            throw new ArithmeticException("User name must not be null");
        }

        if (userService.findByEmail(signupDto.getEmail()) != null) {
            throw new ArithmeticException("Email already exists");
        }

        User user = User.builder()
                .name(signupDto.getFullName())
                .email(signupDto.getEmail())
                .password(passwordEncoder.encode(signupDto.getPassword()))
                .userName(signupDto.getUserName())
                .build();

        userService.create(user);

    }
}
