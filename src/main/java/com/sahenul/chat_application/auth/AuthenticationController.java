package com.sahenul.chat_application.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private final JwtUtil jwtUtil;


    @PostMapping("/login")
    public ResponseEntity<?> verifyGoogleToken(@RequestBody Map<String, String> request) {
        String accessToken = request.get("accessToken");

        // Validate token with Google API
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);

        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);

        String url = "https://www.googleapis.com/oauth2/v2/userinfo";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(401).body("Invalid Google access token");
        }

//        Map<String, Object> googleUser = response.getBody();
//
//        String email = (String) googleUser.get("email");
//        String name = (String) googleUser.get("name");
//
//        // Generate custom JWT for your application
//        String jwtToken = jwtUtil.generateToken(email);
//
//        return ResponseEntity.ok(Map.of(
//                "email", email,
//                "name", name,
//                "token", jwtToken
//        ));
        return null;
    }

}
