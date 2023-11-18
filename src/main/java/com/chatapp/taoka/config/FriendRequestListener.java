package com.chatapp.taoka.config;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;

public class FriendRequestListener implements ConnectListener, DisconnectListener {

    @Override
    public void onConnect(SocketIOClient client) {
        // Handle connection event
        System.out.println("Client connected: " + client.getSessionId().toString());
    }

    @OnEvent("friend_request")
    public void onFriendRequest(SocketIOClient client, AckRequest ackRequest, String targetEmail) {
        // Handle friend_request event
        System.out.println("Friend request received for: " + targetEmail);
        // Add your logic to process friend requests
    }


    @Override
    public void onDisconnect(SocketIOClient client) {
        // Handle disconnection event
        System.out.println("Client disconnected: " + client.getSessionId().toString());
    }
}

