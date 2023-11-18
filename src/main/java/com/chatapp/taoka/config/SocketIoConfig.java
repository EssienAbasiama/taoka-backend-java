package com.chatapp.taoka.config;


import com.corundumstudio.socketio.*;
//import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
@org.springframework.context.annotation.Configuration
public class SocketIoConfig {
    @Bean
    public SocketIOServer socketIOServer() {
        Configuration config = new Configuration();
        config.setHostname("localhost"); // Change this to your server's hostname or IP address
        config.setPort(9090); // Choose a port for your Socket.IO server
        SocketIOServer server = new SocketIOServer(config);
        server.start();
        // Log statement to verify the server starts
        System.out.println("Socket.IO server started!");

        return server;
    }
    @Bean
    public FriendRequestListener friendRequestListener() {
        return new FriendRequestListener();
    }
}
