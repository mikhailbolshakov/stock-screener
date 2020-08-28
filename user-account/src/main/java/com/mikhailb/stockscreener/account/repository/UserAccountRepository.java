package com.mikhailb.stockscreener.account.repository;

import com.mikhailb.stockscreener.account.repository.entity.UserAccountCriteria;
import com.mikhailb.stockscreener.account.repository.entity.UserAccountEntity;
import com.mikhailb.stockscreener.common.types.pagination.PaginationRequest;
import com.mikhailb.stockscreener.common.types.pagination.PaginationResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserAccountRepository {

    UserAccountEntity createAccount(UserAccountEntity dto);

    void history(UUID userAccountId, String operation);

    Optional<UserAccountEntity> findById(UUID accountId);

    List<UserAccountEntity> findByPhone(String phone);

    PaginationResponse<UserAccountEntity> findByCriteria(PaginationRequest<UserAccountCriteria> criteria);

}
