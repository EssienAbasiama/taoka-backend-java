package com.chatapp.taoka.Controller;

import com.chatapp.taoka.Model.User;
import com.chatapp.taoka.Services.impl.GroupServiceImpl;
import com.chatapp.taoka.Services.impl.UserServiceImpl;
import com.chatapp.taoka.dao.request.GroupCreationRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/group")
public class GroupController {

    public final GroupServiceImpl groupService;
    public final UserServiceImpl userService;

    @PostMapping("/create")
    public ResponseEntity<?> createGroup(GroupCreationRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // Retrieve user details if the authentication is not null and principal is UserDetails
            if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();

                String author = userDetails.getUsername(); // Get username
                if (author.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized, Login again");
                } else if (StringUtils.isEmpty(request.getTitle())) {
                    throw new IllegalArgumentException("Title is Required");
                } else if (request.getGroupAuthor() == null) {
                    throw new IllegalArgumentException("Group Author is Required");
                } else if (request.getMembers().isEmpty()) {
                    throw new IllegalArgumentException("Group Member is Required");
                } else if (request.getMembers().size() == 2) {
                    throw new IllegalArgumentException("Group Members must be More than TWO (2)");
                }
                User groupAuthor = userService.findByEmail(author);
                return ResponseEntity.status(HttpStatus.OK).body(groupService.createGroup(request, groupAuthor));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You need to be LoggedIn before accessing this" +
                        " EndPoint");
            }
        } catch (UsernameNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Email Does not Exist");
        }
    }
}
