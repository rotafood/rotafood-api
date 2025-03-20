package br.com.rotafood.api.infra.rabbitmq;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Service;

@Service
public class RabbitQueueManager {

    private final RabbitAdmin rabbitAdmin;

    public RabbitQueueManager(RabbitAdmin rabbitAdmin) {
        this.rabbitAdmin = rabbitAdmin;
    }

    public void createMerchantQueue(String merchantId) {
        Queue queue = QueueBuilder.durable("queue.merchant." + merchantId)
                .withArgument("x-message-ttl", 1200000)
                .withArgument("x-expires", 1200000)
                .build();
        rabbitAdmin.declareQueue(queue);
    }
}
 