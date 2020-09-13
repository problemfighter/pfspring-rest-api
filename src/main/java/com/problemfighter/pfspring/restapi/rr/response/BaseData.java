package com.problemfighter.pfspring.restapi.rr.response;


import com.problemfighter.pfspring.restapi.exception.ResponseCode;

public class BaseData {
    public Status status;
    public String code;

    public void success() {
        this.code = ResponseCode.success;
        this.status = Status.success;
    }

    public void error() {
        this.code = ResponseCode.error;
        this.status = Status.error;
    }
}
