package com.problemfighter.pfspring.restapi.rr.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.LinkedHashMap;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorData {

    public I18nMessage message;
    @ApiModelProperty(example = "key: explanation")
    public LinkedHashMap<String, I18nMessage> details;

}
