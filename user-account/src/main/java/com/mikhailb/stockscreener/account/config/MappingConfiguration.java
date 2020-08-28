package com.mikhailb.stockscreener.account.config;

import com.mikhailb.stockscreener.account.domain.model.UserAccount;
import com.mikhailb.stockscreener.account.domain.model.UserPhone;
import com.mikhailb.stockscreener.account.repository.entity.UserAccountEntity;
import com.mikhailb.stockscreener.account.repository.entity.UserEmailEntity;
import com.mikhailb.stockscreener.account.repository.entity.UserPhoneEntity;
import com.mikhailb.stockscreener.account.service.model.GetUserAccountRs;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class MappingConfiguration {

    public static Converter<UserPhone, UserPhoneEntity> enumConverter = mappingContext -> {
        mappingContext.getDestination().setTypeId(mappingContext.getSource().getPhoneType().name());
        return mappingContext.getDestination();
    };

    @Bean
    public ModelMapper getMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.createTypeMap(UserAccount.class, UserAccountEntity.class);

        mapper.createTypeMap(UserPhone.class, UserPhoneEntity.class)
                .addMappings(m -> m.skip(UserPhoneEntity::setTypeId))
                .setPostConverter(enumConverter);

        mapper.createTypeMap(UserAccountEntity.class, GetUserAccountRs.class)
                .addMappings(m -> m.skip(GetUserAccountRs::setMobilePhone))
                .addMappings(m -> m.skip(GetUserAccountRs::setEmail))
                .setPostConverter((ctx) -> {

                    LocalDateTime now = LocalDateTime.now();

                    ctx.getSource()
                            .getPhones().stream()
                            .filter(UserPhoneEntity::isMain)
                            .filter(a -> a.getValidFrom().isBefore(now) && a.getValidTo().isAfter(now))
                            .findFirst()
                            .map(UserPhoneEntity::getPhoneNumber)
                            .ifPresent(a -> ctx.getDestination().setMobilePhone(a));

                    ctx.getSource()
                            .getEmails().stream()
                            .filter(UserEmailEntity::isMain)
                            .filter(a -> a.getValidFrom().isBefore(now) && a.getValidTo().isAfter(now))
                            .findFirst()
                            .map(UserEmailEntity::getEmail)
                            .ifPresent(a -> ctx.getDestination().setEmail(a));

                    return ctx.getDestination();
                });

        return mapper;
    }

}
