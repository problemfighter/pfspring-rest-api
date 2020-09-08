package com.problemfighter.pfspring.restapi.rr.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageableResponse<T> extends ErrorAndBaseData {
    public List<T> data;
    public PaginationData pagination;

    public PaginationData addPagination(Integer page, Integer itemPerPage){
        if (pagination == null){
            pagination = new PaginationData();
        }
        pagination.page = page;
        pagination.itemPerPage = itemPerPage;
        return pagination;
    }
}
