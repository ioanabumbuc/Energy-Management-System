package sd.monitoringcommunicationmicroservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {
    static final String topicExchangeName = "exchange";

    static final String queueName = "queue";

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(topicExchangeName);
    }

    @Bean
    Binding binding(Queue queue, DirectExchange directExchange) {
        return BindingBuilder.bind(queue).to(directExchange).with("queue");
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setUri("amqps://ydzrozkt:LLZ2mz8v9rmT2fPLuPYkEEdQ5WToClKd@rat.rmq2.cloudamqp.com/ydzrozkt");
        connectionFactory.setUsername("ydzrozkt");
        connectionFactory.setPassword("LLZ2mz8v9rmT2fPLuPYkEEdQ5WToClKd");
        connectionFactory.setPort(5671);
        connectionFactory.setVirtualHost("ydzrozkt");
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setConfirmCallback(((correlationData, acknowledge, cause) -> {
            if (!acknowledge) {
                System.out.println("Failed to send message: " + cause);
            }
        }));
        return template;
    }
}

