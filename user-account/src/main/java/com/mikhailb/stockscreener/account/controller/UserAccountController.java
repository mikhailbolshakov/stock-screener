package com.mikhailb.stockscreener.account.controller;

import com.mikhailb.stockscreener.account.service.UserAccountSearchService;
import com.mikhailb.stockscreener.account.service.UserAccountService;
import com.mikhailb.stockscreener.account.service.model.CreateUserAccountRq;
import com.mikhailb.stockscreener.account.service.model.CreateUserAccountRs;
import com.mikhailb.stockscreener.account.service.model.GetUserAccountRs;
import com.mikhailb.stockscreener.account.service.model.UserAccountSearchCriteria;
import com.mikhailb.stockscreener.common.types.pagination.PaginationRequest;
import com.mikhailb.stockscreener.common.types.pagination.PaginationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/accounts")
public class UserAccountController {

    private final UserAccountService userAccountService;
    private final UserAccountSearchService searchService;
    private final Logger logger = LoggerFactory.getLogger(UserAccountController.class);

    @Autowired
    public UserAccountController(UserAccountService userAccountService,
                                 UserAccountSearchService searchService) {
        this.userAccountService = userAccountService;
        this.searchService = searchService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    // TODO: add aspect to catch error
    public CreateUserAccountRs create(@RequestBody CreateUserAccountRq request) {
        try {
            return userAccountService.createAccount(request);
        }
        catch(Throwable e) {
            logger.error("UserAccountController.create error", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping("/search/byphone")
    public GetUserAccountRs getUserAccountByPhone(@RequestParam() String phone) {
        try {

            UserAccountSearchCriteria cr = new UserAccountSearchCriteria();

            if(!StringUtils.isEmpty(phone)) {
                cr.setPhoneNumber(phone);
            }
            else
                throw new IllegalArgumentException("User account phone is empty");

            return searchService
                    .searchSingle(cr)
                    .orElse(new GetUserAccountRs());

        }
        catch(Throwable e) {
            logger.error("HUserAccountController.getUserAccountByPhone error", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping("/search")
    public PaginationResponse<GetUserAccountRs> getUserAccountsByCriteria(@RequestParam(required = false) String lastName,
                                                                                    @RequestParam(required = false) String phone,
                                                                                    @RequestParam(required = false) String email,
                                                                                    @RequestParam(required = false) Integer pageIndex,
                                                                                    @RequestParam(required = false) Integer itemsOnPage) {
        try {


            UserAccountSearchCriteria cr = new UserAccountSearchCriteria();

            if(!StringUtils.isEmpty(lastName))
                cr.setLastNameMask(lastName);

            if(!StringUtils.isEmpty(phone))
                cr.setPhoneNumber(phone);

            if(!StringUtils.isEmpty(email))
                cr.setEmail(email);

            return searchService.searchWithPagination(new PaginationRequest<>(pageIndex, itemsOnPage, true, cr));

        }
        catch(Throwable e) {
            logger.error("HUserAccountController.getUserAccountsByCriteria error", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }


}
