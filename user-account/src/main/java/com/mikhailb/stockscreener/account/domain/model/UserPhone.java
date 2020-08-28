package com.mikhailb.stockscreener.account.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserPhone {

    private UUID id;
    private PhoneType phoneType;
    private String phoneNumber;
    private boolean main;
    private boolean confirmed;

}
