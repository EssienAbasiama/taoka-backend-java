package com.chatapp.taoka.Services.impl;

import com.chatapp.taoka.Exception.EntityNotFoundException;
import com.chatapp.taoka.Exception.UserNotFoundException;
import com.chatapp.taoka.Model.Friends;
import com.chatapp.taoka.Model.User;
import com.chatapp.taoka.Repository.UserRepository;
import com.chatapp.taoka.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }


    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Email Doesn't exist"));
    }

    public List<User> getAllFriend(String email) {
        return findByEmail(email).getFriends();
    }


    public User acceptFriendRequest(String accountOwnerEmail, String newFriendEmail){
        User user = findByEmail(newFriendEmail);
        User newUser = findByEmail(newFriendEmail);
        List<User> friends = user.getFriends();
        friends.add(newUser);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllFriendRequest(String email) {
        return findByEmail(email).getFriendRequest();
    }

    @Override
    public String verifyEmail(String email) {
        User user = findByEmail(email);
        user.setVerified(true);
        userRepository.save(user);
        return "The Email " + email+" Has been Verified";
    }

    public String sendFriendRequest(String email) {
        return "Hello";
    }
}