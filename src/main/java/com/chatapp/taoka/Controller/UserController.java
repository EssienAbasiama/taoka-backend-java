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
@RequestMapping("api/v1/users")
public class UserController {

    public final UserServiceImpl userService;

    @GetMapping("/get-me")
    public ResponseEntity<?> getProfile() {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // Retrieve user details if the authentication is not null and principal is UserDetails
            if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();

                String email = userDetails.getUsername(); // Get username
                if (email.isEmpty()){
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized, Login again");
                }
                User user = userService.findByEmail(email);
                return ResponseEntity.ok(user);
            }else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You need to be LoggedIn before accessing this" +
                        " EndPoint");
            }
        }
        catch (UsernameNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Email Does not Exist");
        }
    }

    @GetMapping("/getallusers")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @GetMapping("/get-friends")
    public ResponseEntity<?> getFriends() {

        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // Retrieve user details if the authentication is not null and principal is UserDetails
            if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();

                String email = userDetails.getUsername(); // Get username
                if (email.isEmpty()){
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized, Login again");
                }
                User user = userService.findByEmail(email);
                List<User> friends = user.getFriends();
                return ResponseEntity.ok(friends);
            }else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You need to be LoggedIn before accessing this" +
                        " EndPoint");
            }
        }
        catch (UsernameNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Email Does not Exist");
        }
    }

    @GetMapping("/get-requests")
    public ResponseEntity<?> getAllFriendRequest() {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // Retrieve user details if the authentication is not null and principal is UserDetails
            if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();

                String email = userDetails.getUsername(); // Get username
                if (email.isEmpty()){
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized, Login again");
                }
                return ResponseEntity.ok(userService.getAllFriendRequest(email));
            }else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You need to be LoggedIn before accessing this" +
                        " EndPoint");
            }
        }
        catch (UsernameNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Email Does not Exist");
        }





    }

    @GetMapping("/verifyEmail")
    public ResponseEntity<?> verifyEmail(@RequestBody String email) {
        return ResponseEntity.ok(userService.verifyEmail(email));
    }

    @GetMapping("/sendFriendRequest/{toEmail}")
    public ResponseEntity<?> sendFriendRequest(@PathVariable String toEmail) {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // Retrieve user details if the authentication is not null and principal is UserDetails
            if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String email = userDetails.getUsername(); // Get username
                if (email.isEmpty()){
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized, Login again");
                }

                if(userService.sendFriendRequest(email, toEmail) != null) {
                    return ResponseEntity.status(HttpStatus.OK).body("Friend " +
                            "Request " +
                            " Sent to "+ email);
                } return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You need to be Authorized to access this endPoint");

            }else {
                System.out.println("You need to be logged In first");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You need to be LoggedIn before accessing this" +
                        " EndPoint");
            }
        }
        catch (UsernameNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Email Does not Exist");
        }
    }

    @GetMapping("/acceptFriendRequest/{newFriendEmail}")
    public ResponseEntity<?> acceptFriendRequest(@PathVariable String newFriendEmail) {
        System.out.println("I want to Send Friend Request");
        try{
//            String newFriendEmail = request.get("newFriendEmail");
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
}