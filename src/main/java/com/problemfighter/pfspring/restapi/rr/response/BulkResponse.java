package com.problemfighter.pfspring.restapi.rr.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class BulkResponse<D> extends BaseData {
    public List<D> success = null;
    public List<BulkErrorData<D>> failed = null;

    public BulkResponse<D> addFailed(BulkErrorData<D> data) {
        if (failed == null) {
            failed = new ArrayList<>();
        }
        failed.add(data);
        return this;
    }

    public BulkResponse<D> addSuccessDataList(List<D> dataList) {
        if (success == null) {
            success = new ArrayList<>();
        }
        success = dataList;
        return this;
    }

}
