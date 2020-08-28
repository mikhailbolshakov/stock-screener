package com.mikhailb.stockscreener.account.service.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class GetUserAccountRs {

    private UUID accountId;
    private String firstName;
    private String lastName;
    private String mobilePhone;
    private String email;
    private boolean locked;

}
