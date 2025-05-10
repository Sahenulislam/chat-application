package com.sahenul.chat_application.message;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth/test")
public class TestController {

    @GetMapping
    public ResponseEntity<?> test() {
       return ResponseEntity.ok("hi ");
    }
}
