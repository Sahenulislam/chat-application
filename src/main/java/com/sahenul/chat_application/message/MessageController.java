package com.sahenul.chat_application.message;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;


    @GetMapping("/conversation-list")
    public ResponseEntity<?> getConversationList(){
        return ResponseEntity.ok().body(messageService.getConversationList());
    }
}
