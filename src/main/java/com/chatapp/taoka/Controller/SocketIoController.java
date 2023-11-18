package com.chatapp.taoka.Controller;

import com.chatapp.taoka.Model.User;
import com.chatapp.taoka.Services.impl.UserServiceImpl;
import com.chatapp.taoka.dao.request.FriendRequestData;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/socket")
public class SocketIoController {
    private final SocketIOServer socketIoServer;
    public final UserServiceImpl userService;

//    @OnConnect
//    public void onConnect(SocketIOClient client) {
//        System.out.println("Client connected: " + client.getSessionId().toString());
//    }

//    @OnDisconnect
//    public void onDisconnect(SocketIOClient client) {
//        System.out.println("Client disconnected: " + client.getSessionId().toString());
//    }

//    @OnEvent("friend_request")
//    public void onFriendRequest(SocketIOClient client, AckRequest ackRequest, FriendRequestData data) {
//        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//            if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
//                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//                String accountOwnerEmail = userDetails.getUsername();
//
//                // Handle the friend request here
//                handleFriendRequest(accountOwnerEmail, data.getTargetEmail());
//
//                // Send acknowledgment if required
//                if (ackRequest.isAckRequested()) {
//                    ackRequest.sendAckData("Friend request handled successfully");
//                }
//            } else {
//                // Handle unauthenticated request
//                client.sendEvent("unauthorized", "You need to be logged in to send a friend request.");
//            }
//        } catch (Exception e) {
//            // Handle exceptions
//            client.sendEvent("error", "Error processing friend request: " + e.getMessage());
//        }
//    }

//    private void handleFriendRequest(String accountOwnerEmail, String targetEmail) {
//        try {
//            userService.sendFriendRequest(accountOwnerEmail,targetEmail);
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }


}