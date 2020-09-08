package com.problemfighter.pfspring.restapi.rr.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.LinkedHashMap;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorData {

    public I18nMessage message;
    public LinkedHashMap<String, I18nMessage> details;

}
