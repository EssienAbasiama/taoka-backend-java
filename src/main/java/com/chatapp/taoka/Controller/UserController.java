package com.chatapp.taoka.Controller;

import com.chatapp.taoka.Model.User;
import com.chatapp.taoka.Services.UserService;
import com.chatapp.taoka.Services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class UserController {

    public final UserServiceImpl userService;
    @PostMapping("/getAllFriends")
    public ResponseEntity<?> getFriends(@RequestBody Map<String,String> request) {
        String email = request.get("email");

        try {
            User user = userService.findByEmail(email);
            List<User> friends = userService.getAllFriend(user.getEmail());
            return ResponseEntity.ok(friends);
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Email Does not Exist");
        }
    }

    @PostMapping("/acceptFriendRequest")
    public ResponseEntity<?> acceptFriendRequest(@RequestBody Map<String,String> request) {
        try{
            String newFriendEmail = request.get("newFriendEmail");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // Retrieve user details if the authentication is not null and principal is UserDetails
            if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();

                String accountOwnerEmail = userDetails.getUsername(); // Get username
                if (newFriendEmail.isEmpty()){
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Friend's Email Can't be Empty");
                }
                if (accountOwnerEmail.equals(newFriendEmail)) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User Can't add himself as Friend");
                }

                return ResponseEntity.ok(userService.acceptFriendRequest(accountOwnerEmail, newFriendEmail));

            }else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You need to be LoggedIn before accessing this" +
                        " EndPoint");
            }
        }
        catch (UsernameNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Email Does not Exist");
        }
    }

    @GetMapping("/getAllFriendRequest")
    public ResponseEntity<?> getAllFriendRequest(@RequestBody String email) {
        return ResponseEntity.ok(userService.getAllFriendRequest(email));
    }

    @GetMapping("/verifyEmail")
    public ResponseEntity<?> verifyEmail(@RequestBody String email) {
        return ResponseEntity.ok(userService.verifyEmail(email));
    }

    @GetMapping("/sendFriendRequest")
    public ResponseEntity<?> sendFriendRequest(@RequestBody Map<String,String> request) {
        String email = request.get("email");
        User newFriend = userService.findByEmail(email);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User ownerUser = userService.findByEmail(userDetails.getUsername());
            ownerUser.getFriends().add(newFriend);
            newFriend.getFriendRequest().add(ownerUser);
            return ResponseEntity.status(HttpStatus.OK).body("Friend Request Sent to " + newFriend);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You need to be LoggedIn before accessing this" +
                    " EndPoint");
        }
    }
}