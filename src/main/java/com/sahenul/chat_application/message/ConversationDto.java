package com.sahenul.chat_application.message;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversationDto {
    private Long messageId;
    private String chatName;
    private LocalDateTime localDateTime;
    private boolean isGroup;
}
