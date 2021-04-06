package com.shariful.mb.accountservice.utilities.config;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQ {

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(Constant.TOPIC_KEY);
    }

    @Bean
    Queue accountQueue() {
        return new Queue(Constant.ACCOUNT_KEY);
    }

    @Bean
    Queue transactionQueue() {
        return new Queue(Constant.TRANSACTION_KEY);
    }

    @Bean
    Binding bindingAccount(TopicExchange exchange) {
        return BindingBuilder.bind(accountQueue()).to(exchange).with(Constant.ACCOUNT_ROUTING_KEY);
    }

    @Bean
    Binding bindingTransaction(TopicExchange exchange) {
        return BindingBuilder.bind(transactionQueue()).to(exchange).with(Constant.ACCOUNT_TRANSACTION_KEY);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
