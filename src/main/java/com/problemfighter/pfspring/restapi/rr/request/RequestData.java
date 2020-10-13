package com.problemfighter.pfspring.restapi.rr.request;

import java.io.Serializable;

public class RequestData<T> implements Serializable {
    public T data;

    public T getData() {
        return this.data;
    }
}
