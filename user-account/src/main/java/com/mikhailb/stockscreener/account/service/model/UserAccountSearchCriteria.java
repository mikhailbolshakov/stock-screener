package com.mikhailb.stockscreener.account.service.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAccountSearchCriteria {

    private String phoneNumber;
    private String lastNameMask;
    private String email;

}
