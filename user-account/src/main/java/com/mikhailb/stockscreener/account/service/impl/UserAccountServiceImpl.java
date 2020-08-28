package com.mikhailb.stockscreener.account.service.impl;

import com.mikhailb.stockscreener.account.domain.UserAccountDomainService;
import com.mikhailb.stockscreener.account.domain.UserEmailDomainService;
import com.mikhailb.stockscreener.account.domain.UserPhoneDomainService;
import com.mikhailb.stockscreener.account.domain.model.UserAccount;
import com.mikhailb.stockscreener.account.service.UserAccountService;
import com.mikhailb.stockscreener.account.service.model.CreateUserAccountRq;
import com.mikhailb.stockscreener.account.service.model.CreateUserAccountRs;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountDomainService userAccountDomainService;
    private final UserPhoneDomainService phoneDomainService;
    private final UserEmailDomainService emailDomainService;

    private final ModelMapper mapper;

    @Autowired
    public UserAccountServiceImpl(UserAccountDomainService userAccountDomainService,
                                  UserPhoneDomainService phoneDomainService,
                                  UserEmailDomainService emailDomainService,
                                  ModelMapper mapper) {
        this.userAccountDomainService = userAccountDomainService;
        this.phoneDomainService = phoneDomainService;
        this.emailDomainService = emailDomainService;

        this.mapper = mapper;
    }

    @Override
    public CreateUserAccountRs createAccount(CreateUserAccountRq userAccountRq) {

        CreateUserAccountRs rs = new CreateUserAccountRs();

        UserAccount account = userAccountDomainService
                .getBuilder()
                .newUserAccount(userAccountRq.getFirstName(), userAccountRq.getLastName())
                .withEmail(userAccountRq.getEmail())
                .withMobilePhone(userAccountRq.getMobilePhone())
                .build();

        account = userAccountDomainService.save(account);

        rs.setUserAccountId(account.getAccountId());

        return rs;
    }
}
