package com.problemfighter.pfspring.restapi.rr.response;


import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DetailsResponse<T> extends ErrorAndBaseData {
    public T data;
}

