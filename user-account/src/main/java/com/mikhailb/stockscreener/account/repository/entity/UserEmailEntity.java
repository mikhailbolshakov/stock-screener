package com.mikhailb.stockscreener.account.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "user_email", schema = "us_acc")
public class UserEmailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "email")
    private String email;

    @Column(name = "main")
    private boolean main;

    @Column(name = "confirmed")
    private boolean confirmed;

    @Column(name = "valid_from")
    private LocalDateTime validFrom;

    @Column(name = "valid_to")
    private LocalDateTime validTo;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private UserAccountEntity userAccount;

}
