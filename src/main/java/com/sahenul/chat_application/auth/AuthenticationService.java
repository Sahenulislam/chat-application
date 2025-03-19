package com.sahenul.chat_application.auth;


import com.sahenul.chat_application.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;

    private final JwtUtil jwtUtil;



    public ResponseEntity<?> verifyGoogleToken(Map<String, String> request) {

        String accessToken = request.get("accessToken");

        String url = "https://oauth2.googleapis.com/tokeninfo?id_token=" + accessToken;

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Map> response = null;

        try {
            response = restTemplate.exchange(url, HttpMethod.GET, null, Map.class);

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid Google access token");
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
}
