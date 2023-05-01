package ru.tinkoff.edu.java.bot.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Value("${rabbitmq.direct-exchange}")
    private String directExchangeName;

    @Value("${rabbitmq.queue}")
    private String queueName;

    @Value("${rabbitmq.routing-key}")
    private String routingKey;

    @Value("${rabbitmq.dlx-postfix}")
    private String dlxPostfix;

    @Value("${rabbitmq.dlq-postfix}")
    private String dlqPostfix;

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(directExchangeName);
    }

    @Bean
    public Queue queue() {
  //      return new Queue(queueName);
        return QueueBuilder.durable(queueName)
                .withArgument("x-dead-letter-exchange", directExchangeName + dlxPostfix)
                .withArgument("x-dead-letter-routing-key", queueName + dlqPostfix)
                .build();
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange directExchange) {
        return BindingBuilder.bind(queue).to(directExchange).with(routingKey);
    }

    @Bean
    public DirectExchange deadLetterDirectExchange() {
        return new DirectExchange(directExchangeName + dlxPostfix);
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(queueName + dlqPostfix).build();
    }

    @Bean
    public Binding deadLetterBinding(Queue deadLetterQueue, DirectExchange deadLetterDirectExchange) {
        return BindingBuilder.bind(deadLetterQueue)
                .to(deadLetterDirectExchange).with(queueName + dlqPostfix);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
