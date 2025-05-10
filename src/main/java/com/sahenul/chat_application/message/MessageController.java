package com.sahenul.chat_application.message;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
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

    @GetMapping("/conversation")
    public ResponseEntity<?> getConversation(
            @RequestParam(required = false) Long partnerId,
            @RequestParam(required = false) Long groupId) {

        if (partnerId != null) {
            return ResponseEntity.ok().body(messageService.getConversation(partnerId));
        } else if (groupId != null) {
            return ResponseEntity.ok().body(messageService.getGroupConversation(groupId));
        } else {
            return ResponseEntity.badRequest().body("Either partnerId or groupId must be provided.");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }

    //for testing purpose
    @PostMapping
    public ResponseEntity<?> sendMessage(@RequestParam(name = "receiverId") Long receiverId, @RequestParam(name = "message") String message) {
        messageService.sendMessageToUser(null, receiverId, message);
        return ResponseEntity.ok().build();
    }
}
