package javaspring.BBS.handler;

import javaspring.BBS.HttpHandshakeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistration;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {



    private final HttpHandshakeInterceptor handshakeInterceptor;
    @Autowired
    public WebSocketConfig(HttpHandshakeInterceptor handshakeInterceptor){

        this.handshakeInterceptor=handshakeInterceptor;
    }
    @Bean
    public WebSocketHandler webSocketHandler() {
        return new WebSocketHandler();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry){
        registry.addHandler(new WebSocketHandler(),"/ws/groupList")
                .addInterceptors(handshakeInterceptor)
                .setAllowedOrigins("*");
    }

}
