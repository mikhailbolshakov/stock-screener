package com.mikhailb.stockscreener.account.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "user_phone", schema = "us_acc")
public class UserPhoneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "type_id")
    private String typeId;

    @Column(name = "phone_number")
    private String phoneNumber;

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
