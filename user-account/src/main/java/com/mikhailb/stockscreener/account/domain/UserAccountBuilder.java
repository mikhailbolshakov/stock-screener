package com.mikhailb.stockscreener.account.domain;

import com.mikhailb.stockscreener.account.domain.model.PhoneType;
import com.mikhailb.stockscreener.account.domain.model.UserAccount;
import com.mikhailb.stockscreener.account.domain.model.UserEmail;
import com.mikhailb.stockscreener.account.domain.model.UserPhone;

public class UserAccountBuilder {

    private UserAccount userAccount;

    public UserAccountBuilder() {
    }

    private void checkInitialized() {

        if (userAccount == null)
            throw new IllegalStateException("Not initialized object. Call newUserAccount first");

    }

    public UserAccountBuilder newUserAccount(String firstName, String lastName) {

        userAccount = new UserAccount();
        userAccount.setFirstName(firstName);
        userAccount.setLastName(lastName);
        userAccount.setLocked(false);

        return this;

    }

    public UserAccountBuilder withMobilePhone(String phoneNumber) {

        checkInitialized();

        UserPhone phone = new UserPhone();
        phone.setPhoneNumber(phoneNumber);
        phone.setPhoneType(PhoneType.Mobile);
        phone.setConfirmed(false);
        phone.setMain(userAccount.getPhones().isEmpty());
        userAccount.getPhones().add(phone);

        return this;
    }

    public UserAccountBuilder withEmail(String emailAddress) {

        checkInitialized();

        UserEmail email = new UserEmail();
        email.setEmail(emailAddress);
        email.setMain(userAccount.getEmails().isEmpty());
        userAccount.getEmails().add(email);

        return this;

    }

    public UserAccount build() {

        checkInitialized();

        UserAccount result = userAccount;
        userAccount = null;

        return result;
    }

}
