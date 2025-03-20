package br.com.rotafood.api.application.ws;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import br.com.rotafood.api.infra.security.TokenService;
import br.com.rotafood.api.application.dto.merchant.MerchantUserDto;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class CommandPrintWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private ConnectionFactory springConnectionFactory;

    private ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        String query = session.getUri().getQuery();
        String token = query.split("=")[1];

        MerchantUserDto merchantUserDto = tokenService.getMerchantUser(token);
        String merchantId = merchantUserDto.merchantId().toString();

        sessions.put(merchantId, session);

        Connection rabbitConnection = springConnectionFactory.createConnection().getDelegate();
        Channel channel = rabbitConnection.createChannel();

        String queueName = "queue.merchant." + merchantId;

        channel.basicConsume(queueName, true, (consumerTag, message) -> {
            String body = new String(message.getBody());
            WebSocketSession merchantSession = sessions.get(merchantId);
            if (merchantSession != null && merchantSession.isOpen()) {
                merchantSession.sendMessage(new TextMessage(body));
            }
        }, consumerTag -> {});
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.values().remove(session);
    }
}
