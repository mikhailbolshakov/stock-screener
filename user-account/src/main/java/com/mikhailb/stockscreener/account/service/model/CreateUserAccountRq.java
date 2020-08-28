package com.mikhailb.stockscreener.account.service.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserAccountRq {

    private String firstName;
    private String lastName;
    private String mobilePhone;
    private String email;

}
