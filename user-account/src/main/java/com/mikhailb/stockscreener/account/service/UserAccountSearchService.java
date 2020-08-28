package com.mikhailb.stockscreener.account.service;

import com.mikhailb.stockscreener.account.service.model.GetUserAccountRs;
import com.mikhailb.stockscreener.account.service.model.UserAccountSearchCriteria;
import com.mikhailb.stockscreener.common.types.pagination.PaginationRequest;
import com.mikhailb.stockscreener.common.types.pagination.PaginationResponse;

import java.util.Optional;

public interface UserAccountSearchService {

    Optional<GetUserAccountRs> searchSingle(UserAccountSearchCriteria criteria);

    Iterable<GetUserAccountRs> search(UserAccountSearchCriteria criteria);

    PaginationResponse<GetUserAccountRs> searchWithPagination(PaginationRequest<UserAccountSearchCriteria> request);

}
