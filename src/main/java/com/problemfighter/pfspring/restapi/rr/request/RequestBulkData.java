package com.problemfighter.pfspring.restapi.rr.request;

import java.io.Serializable;
import java.util.List;

public class RequestBulkData<T> implements Serializable {

    public List<T> data;

    public List<T> getData() {
        return this.data;
    }
}
