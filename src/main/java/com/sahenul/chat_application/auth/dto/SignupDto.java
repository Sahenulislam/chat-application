package com.sahenul.chat_application.auth.dto;


import lombok.Data;

@Data
public class SignupDto {
    private String fullName;
    private String userName;
    private String email;
    private String password;
}
