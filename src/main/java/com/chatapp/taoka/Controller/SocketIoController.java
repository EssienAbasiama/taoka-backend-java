package com.chatapp.taoka.Controller;

import com.chatapp.taoka.Services.impl.UserServiceImpl;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class SocketIoController {
    @Autowired
    private SocketIOServer socketIoServer;
    public final UserServiceImpl userService;

    @OnConnect
    public void onConnect(SocketIOClient client) {
        System.out.println("Client connected: " + client.getSessionId().toString());
    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        System.out.println("Client disconnected: " + client.getSessionId().toString());
    }

    @OnEvent("friend_request")
    public ResponseEntity<?> onFriendRequest(SocketIOClient client, AckRequest ackRequest,String newFriendEmail) {
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
                } // Assuming the request is handled successfully
                if (ackRequest.isAckRequested()) {
                    ackRequest.sendAckData("Request handled successfully");
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
