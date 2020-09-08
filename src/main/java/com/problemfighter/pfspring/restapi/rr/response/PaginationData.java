package com.problemfighter.pfspring.restapi.rr.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaginationData {
    public Long total;
    public Integer totalPage;
    public Integer page;
    public Integer itemPerPage;

    public PaginationData setTotal(Long total) {
        this.total = total;
        return this;
    }

    public PaginationData setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
        return this;
    }

    public PaginationData setPage(Integer page) {
        this.page = page;
        return this;
    }

    public PaginationData setItemPerPage(Integer itemPerPage) {
        this.itemPerPage = itemPerPage;
        return this;
    }
}
