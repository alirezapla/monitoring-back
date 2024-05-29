package com.example.monitor.management.api.utils.httputil.pagination;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class PaginationDTO implements Serializable {

    private final int page;
    private final int perPage;

    public PaginationDTO(int page, int perPage) {
        this.page = page;
        this.perPage = perPage;
    }

}
