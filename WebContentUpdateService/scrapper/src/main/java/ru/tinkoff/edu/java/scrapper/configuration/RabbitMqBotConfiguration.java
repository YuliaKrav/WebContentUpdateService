package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.services.BotService;
import ru.tinkoff.edu.java.scrapper.services.RabbitMqBotService;
import ru.tinkoff.edu.java.scrapper.services.ScrapperQueueProducer;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "useQueue", havingValue = "true")
public class RabbitMqBotConfiguration {
    @Bean
    public ScrapperQueueProducer scrapperQueueProducer(RabbitTemplate rabbitTemplate, DirectExchange directExchange) {
        return new ScrapperQueueProducer(rabbitTemplate, directExchange);
    }

    @Bean
    public BotService botService() {
        return new RabbitMqBotService();
    }
}
