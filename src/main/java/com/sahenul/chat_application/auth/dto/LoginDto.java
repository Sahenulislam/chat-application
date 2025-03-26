package com.sahenul.chat_application.auth.dto;


import lombok.Data;

@Data
public class LoginDto{
    private String email;
    private String password;
    private String jwtToken;
}
