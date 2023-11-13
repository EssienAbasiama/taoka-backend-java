package com.chatapp.taoka.config;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.context.annotation.Bean;

public class SocketIoConfig {
    @Bean
    public SocketIOServer socketIOServer() {
        Configuration config = new Configuration();
        config.setHostname("localhost"); // Change this to your server's hostname or IP address
        config.setPort(4000); // Choose a port for your Socket.IO server

        return new SocketIOServer(config);
    }
}
