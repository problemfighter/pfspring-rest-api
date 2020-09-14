package com.problemfighter.pfspring.restapi.inter;

import com.problemfighter.pfspring.restapi.rr.request.RequestBulkData;
import com.problemfighter.pfspring.restapi.rr.request.RequestData;
import com.problemfighter.pfspring.restapi.rr.response.BulkResponse;
import com.problemfighter.pfspring.restapi.rr.response.DetailsResponse;
import com.problemfighter.pfspring.restapi.rr.response.MessageResponse;
import com.problemfighter.pfspring.restapi.rr.response.PageableResponse;

public interface RestApiAction<M, D, U> {

    default MessageResponse create(RequestData<D> data){return null;}
    default BulkResponse<D> bulkCreate(RequestBulkData<D> data){return null;}
    default PageableResponse<M> list(Integer page, Integer size, String sort, String field, String search){return null;}
    default PageableResponse<D> detailList(Integer page, Integer size, String sort, String field, String search){return null;}
    default PageableResponse<M> trash(Integer page, Integer size, String sort, String field, String search){return null;}
    default DetailsResponse<D> details(Long id){return null;}
    default MessageResponse update(RequestData<U> data){return null;}
    default BulkResponse<U> bulkUpdate(RequestBulkData<U> data){return null;}
    default MessageResponse bulkDelete(RequestBulkData<Long> data){return null;}
    default MessageResponse hardDelete(RequestBulkData<Long> data){return null;}
    default MessageResponse delete(Long id){return null;}
    default MessageResponse bulkRestore(RequestBulkData<Long> data){return null;}
}
