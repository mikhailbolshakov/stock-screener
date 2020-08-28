package com.mikhailb.stockscreener.account.domain;

import com.mikhailb.stockscreener.account.domain.model.UserAccount;

public interface UserAccountValidator {

    void validate(UserAccount userAccount);

}
