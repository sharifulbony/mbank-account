package com.shariful.mb.accountservice.services.implementations;
import com.shariful.mb.accountservice.services.interfaces.MessageQueueInterface;
import com.shariful.mb.accountservice.entities.rabbitmq.AccountMap;
import com.shariful.mb.accountservice.entities.rabbitmq.TransactionMap;
import com.shariful.mb.accountservice.utilities.config.Constant;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MessageQueueService implements MessageQueueInterface {

    private final RabbitTemplate rabbitTemplate;

    public MessageQueueService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Async
    @Override
    public void publishCreateAccount(AccountMap accountMap) {
        rabbitTemplate.convertAndSend(Constant.TOPIC_KEY, Constant.ACCOUNT_ROUTING_KEY, accountMap);
    }

    @Async
    @Override
    public void publishCreateTransaction(TransactionMap transactionMap) {
        rabbitTemplate.convertAndSend(Constant.TOPIC_KEY, Constant.ACCOUNT_TRANSACTION_KEY, transactionMap);
    }
}
