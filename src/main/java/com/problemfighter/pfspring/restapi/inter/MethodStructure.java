package com.problemfighter.pfspring.restapi.inter;

import com.problemfighter.pfspring.restapi.rr.request.RequestBulkData;
import com.problemfighter.pfspring.restapi.rr.request.RequestData;
import com.problemfighter.pfspring.restapi.rr.response.BulkResponse;
import com.problemfighter.pfspring.restapi.rr.response.DetailsResponse;
import com.problemfighter.pfspring.restapi.rr.response.MessageResponse;

public interface MethodStructure<M, D> {

    MessageResponse create(RequestData<D> data);
    BulkResponse<D> bulkCreate(RequestBulkData<D> data);
//    PageableResponse<M> list();
//    PageableResponse<D> detailList();
    DetailsResponse<D> details(Long id);
//    MessageResponse update(RequestBulkData<D> data);
//    BulkResponse<D> bulkUpdate(RequestBulkData<D> data);
//    BulkResponse<Long> bulkDelete(RequestBulkData<Long> ids);
//    BulkResponse<Long> hardDelete(RequestBulkData<Long> ids);
//    MessageResponse delete(Long id);
}
