package com.problemfighter.pfspring.restapi.rr.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BulkErrorData<T> implements Serializable {
    public ErrorData error;
    public T object;

    public BulkErrorData() {
    }

    public BulkErrorData(ErrorData error, T object) {
        this.error = error;
        this.object = object;
    }

    public BulkErrorData<T> addError(Object object){
        this.error = (ErrorData) object;
        return this;
    }

    public BulkErrorData<T> addError(ErrorData error){
        this.error = error;
        return this;
    }

    public BulkErrorData<T> addObject(T object){
        this.object = object;
        return this;
    }
}