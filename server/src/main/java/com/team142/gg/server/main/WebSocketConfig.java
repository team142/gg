package com.team142.gg.server.main;

import com.team142.gg.server.view.GameWebSocket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(greetingHandler(), "/greeting");
    }

    @Bean
    public WebSocketHandler greetingHandler() {
        return new GameWebSocket();
    }
}