package br.com.rotafood.api.merchant.application.ws;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final CommandPrintWebSocketHandler commandPrintWebSocketHandler;

    public WebSocketConfig(CommandPrintWebSocketHandler commandPrintWebSocketHandler) {
        this.commandPrintWebSocketHandler = commandPrintWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(commandPrintWebSocketHandler, "/v1/ws/print")
                .setAllowedOrigins("*");
    }
}
