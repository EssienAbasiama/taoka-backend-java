package com.chatapp.taoka.Model;


import lombok.Builder;
import lombok.Data;

@Data
public class MessageModel {
    private String message;
    private String fromLogin;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFromLogin() {
        return fromLogin;
    }

    public void setFromLogin(String fromLogin) {
        this.fromLogin = fromLogin;
    }


}
