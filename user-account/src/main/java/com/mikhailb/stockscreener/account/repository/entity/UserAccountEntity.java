package com.mikhailb.stockscreener.account.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "user_account", schema = "us_acc")
public class UserAccountEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID accountId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "locked_account")
    private boolean locked;

    @OneToMany(mappedBy = "userAccount", cascade = {CascadeType.ALL})
    private Set<UserPhoneEntity> phones;

    @OneToMany(mappedBy = "userAccount", cascade = {CascadeType.ALL})
    private Set<UserEmailEntity> emails;

}
