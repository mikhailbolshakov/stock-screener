package com.mikhailb.stockscreener.common.types.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginationRequest<T> {

    private int pageIndex;
    private int itemsOnPage;
    private boolean numberOfPages;
    private T criteria;

}
