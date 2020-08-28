package com.mikhailb.stockscreener.account.service;

import com.mikhailb.stockscreener.account.service.model.CreateUserAccountRq;
import com.mikhailb.stockscreener.account.service.model.CreateUserAccountRs;

public interface UserAccountService {

    /**
     * creates a new account
     * @param userAccount
     * @return
     */
    CreateUserAccountRs createAccount(CreateUserAccountRq userAccount);


}
