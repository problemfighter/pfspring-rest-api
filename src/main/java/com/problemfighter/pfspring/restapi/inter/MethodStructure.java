package com.problemfighter.pfspring.restapi.inter;

import com.problemfighter.pfspring.restapi.rr.request.RequestBulkData;
import com.problemfighter.pfspring.restapi.rr.request.RequestData;
import com.problemfighter.pfspring.restapi.rr.response.BulkResponse;
import com.problemfighter.pfspring.restapi.rr.response.DetailsResponse;
import com.problemfighter.pfspring.restapi.rr.response.MessageResponse;
import com.problemfighter.pfspring.restapi.rr.response.PageableResponse;

public interface MethodStructure<M, D, U> {

    MessageResponse create(RequestData<D> data);
    BulkResponse<D> bulkCreate(RequestBulkData<D> data);
    PageableResponse<M> list(Integer page, Integer size, String sort, String field, String search);
    PageableResponse<D> detailList(Integer page, Integer size, String sort, String field, String search);
    PageableResponse<M> trash(Integer page, Integer size, String sort, String field, String search);
    DetailsResponse<D> details(Long id);
    MessageResponse update(RequestData<U> data);
    BulkResponse<U> bulkUpdate(RequestBulkData<U> data);
    MessageResponse bulkDelete(RequestBulkData<Long> data);
    MessageResponse hardDelete(RequestBulkData<Long> data);
    MessageResponse delete(Long id);
    MessageResponse bulkRestore(RequestBulkData<Long> data);
}
