package com.mikhailb.stockscreener.account.repository.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAccountCriteria {
    private String phoneNumber;
    private String lastNameMask;
    private String email;

}
