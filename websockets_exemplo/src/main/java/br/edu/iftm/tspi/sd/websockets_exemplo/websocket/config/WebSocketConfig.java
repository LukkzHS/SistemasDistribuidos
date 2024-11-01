package br.edu.iftm.tspi.sd.websockets_exemplo.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import br.edu.iftm.tspi.sd.websockets_exemplo.websocket.handler.TextoWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(textoWebSocketHandler(), "/ws").setAllowedOrigins("*");
    }

    @Bean
    public TextoWebSocketHandler textoWebSocketHandler() {
        return new TextoWebSocketHandler();
    }
}
