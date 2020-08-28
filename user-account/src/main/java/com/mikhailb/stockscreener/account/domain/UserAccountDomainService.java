package com.mikhailb.stockscreener.account.domain;

import com.mikhailb.stockscreener.account.domain.model.UserAccount;

public interface UserAccountDomainService {

    /**
     * creates builder to build a new object
     * @return
     */
    UserAccountBuilder getBuilder();

    /**
     * saves (create or update) user account
     * @param userAccount
     * @return
     */
    UserAccount save(UserAccount userAccount);

}
