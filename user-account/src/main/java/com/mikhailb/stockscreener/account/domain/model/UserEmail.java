package com.mikhailb.stockscreener.account.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserEmail {

    private UUID id;
    private String email;
    private boolean main;
    private boolean confirmed;

}
