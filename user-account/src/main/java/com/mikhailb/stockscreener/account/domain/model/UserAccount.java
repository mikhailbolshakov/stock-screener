package com.mikhailb.stockscreener.account.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UserAccount {

    private UUID accountId;
    private String firstName;
    private String lastName;
    private boolean locked;

    private List<UserPhone> phones = new ArrayList<>();
    private List<UserEmail> emails = new ArrayList<>();

}
