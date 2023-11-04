package com.chatapp.taoka.dao.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SigninRequest {
    private String email;
    private String password;
}
