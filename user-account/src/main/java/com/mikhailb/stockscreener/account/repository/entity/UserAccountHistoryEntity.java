package com.mikhailb.stockscreener.account.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "user_account_hst", schema = "us_acc")
public class UserAccountHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private String operation;

    @Column(name = "systimestamp")
    private LocalDateTime sysTimestamp;

    @Column(name = "account_id")
    private UUID accountId;

}
