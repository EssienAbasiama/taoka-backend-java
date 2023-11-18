package com.chatapp.taoka.dao.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FriendRequestData {
    private String targetEmail;
}
