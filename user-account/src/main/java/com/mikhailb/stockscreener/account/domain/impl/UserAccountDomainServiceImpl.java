package com.mikhailb.stockscreener.account.domain.impl;

import com.mikhailb.stockscreener.account.domain.UserAccountBuilder;
import com.mikhailb.stockscreener.account.domain.UserAccountDomainService;
import com.mikhailb.stockscreener.account.domain.UserAccountValidator;
import com.mikhailb.stockscreener.account.domain.model.UserAccount;
import com.mikhailb.stockscreener.account.repository.UserAccountRepository;
import com.mikhailb.stockscreener.account.repository.entity.UserAccountEntity;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class UserAccountDomainServiceImpl implements UserAccountDomainService {

    private final UserAccountValidator validator;
    private final UserAccountRepository repository;
    private final ModelMapper mapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Logger logger = LoggerFactory.getLogger(UserAccountDomainServiceImpl.class);

    public UserAccountDomainServiceImpl(UserAccountValidator validator,
                                        UserAccountRepository repository,
                                        ModelMapper mapper,
                                        KafkaTemplate<String, String> kafkaTemplate) {
        this.validator = validator;
        this.repository = repository;
        this.mapper = mapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public UserAccountBuilder getBuilder() {
        return new UserAccountBuilder();
    }

    private void history(UUID userAccountId, String operation) {

        CompletableFuture
            .runAsync(() -> {
                repository.history(userAccountId, operation);
            })
            .exceptionally(exception -> {
                logger.error("History saving error", exception);
                return null;
            });

    }

    private void sendKafka(String topic, String key, String message) {

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, key, message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                logger.error("Kafka. Sending error:", ex);
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                logger.info(String.format("Kafka. Sent message: %s, offset: %d", message, result.getRecordMetadata().offset()));
            }
        });

    }

    @Override
    public UserAccount save(UserAccount userAccount) {

        UserAccountEntity dto = mapper.map(userAccount, UserAccountEntity.class);

        if (userAccount.getAccountId() == null) {
            repository.createAccount(dto);

            history(dto.getAccountId(), "created");

            sendKafka("user-account", dto.getAccountId().toString(), "created");

        }

        return mapper.map(dto, UserAccount.class);

    }
}
