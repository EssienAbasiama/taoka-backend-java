package com.chatapp.taoka.Services.impl;

import com.chatapp.taoka.Model.User;

import com.chatapp.taoka.Repository.UserRepository;
import com.chatapp.taoka.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<User> getAllUsers() {
        return userRepository.findAll();
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
//      Friends HandShake Occurs
        newUser.getFriends().add(user);
        user.getFriendRequest().remove(newUser);
        userRepository.save(newUser);
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

    public Authentication sendFriendRequest(String accountOwnerEmail, String email) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            User ownerUser = findByEmail(accountOwnerEmail);
            User newFriend = findByEmail(email);
//            newFriend.getFriendRequest().contains(ownerUser);
            if (!newFriend.getFriendRequest().stream().anyMatch(friend -> friend.getId().equals(ownerUser.getId()))){
                newFriend.getFriendRequest().add(ownerUser);
                userRepository.save(newFriend);
            };
        }
        return authentication;
    }



}