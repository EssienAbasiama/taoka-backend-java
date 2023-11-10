package com.chatapp.taoka.Services;

import com.chatapp.taoka.Model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserDetailsService userDetailsService();
    List<User> getAllFriend(String email);

    User acceptFriendRequest(String accountOwnerEmail, String newFriendEmail);

    List<User> getAllFriendRequest(String email);

    String verifyEmail(String email);
}