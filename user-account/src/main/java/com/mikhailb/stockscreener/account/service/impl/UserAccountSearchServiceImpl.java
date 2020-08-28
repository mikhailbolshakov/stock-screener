package com.mikhailb.stockscreener.account.service.impl;

import com.mikhailb.stockscreener.account.repository.UserAccountRepository;
import com.mikhailb.stockscreener.account.repository.entity.UserAccountCriteria;
import com.mikhailb.stockscreener.account.repository.entity.UserAccountEntity;
import com.mikhailb.stockscreener.account.service.UserAccountSearchService;
import com.mikhailb.stockscreener.account.service.model.GetUserAccountRs;
import com.mikhailb.stockscreener.account.service.model.UserAccountSearchCriteria;
import com.mikhailb.stockscreener.common.types.pagination.PaginationRequest;
import com.mikhailb.stockscreener.common.types.pagination.PaginationResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserAccountSearchServiceImpl implements UserAccountSearchService {

    private final UserAccountRepository repository;
    private final ModelMapper mapper;

    @Autowired
    public UserAccountSearchServiceImpl(UserAccountRepository repository,
                                        ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<GetUserAccountRs> searchSingle(UserAccountSearchCriteria criteria) {
        return repository
                .findByPhone(criteria.getPhoneNumber())
                .stream()
                .findFirst()
                .map(a -> mapper.map(a, GetUserAccountRs.class));
    }

    @Override
    public Iterable<GetUserAccountRs> search(UserAccountSearchCriteria criteria) {

        return searchWithPagination(new PaginationRequest<>(1, Integer.MAX_VALUE, false, criteria)).getItems();

    }

    @Override
    public PaginationResponse<GetUserAccountRs> searchWithPagination(PaginationRequest<UserAccountSearchCriteria> request) {

        PaginationRequest<UserAccountCriteria> rq = new PaginationRequest<>(request.getPageIndex(),
                                                                            request.getItemsOnPage(),
                                                                            request.isNumberOfPages(),
                                                                            mapper.map(request.getCriteria(), UserAccountCriteria.class));

        PaginationResponse<UserAccountEntity> rsEntity = repository.findByCriteria(rq);

        PaginationResponse<GetUserAccountRs> rs = new PaginationResponse<>(rsEntity.getPageCount(),
                rsEntity.getItems()
                        .stream()
                        .map(a -> mapper.map(a, GetUserAccountRs.class))
                        .collect(Collectors.toList()));

        return rs;

    }
}
