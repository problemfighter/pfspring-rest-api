package com.problemfighter.pfspring.restapi.rr.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "details", description = "status success response")
public class DetailsResponse<T> extends ErrorAndBaseData {
    public T data;
}

