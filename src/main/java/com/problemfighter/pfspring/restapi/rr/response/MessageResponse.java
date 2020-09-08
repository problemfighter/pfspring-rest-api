package com.problemfighter.pfspring.restapi.rr.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.LinkedHashMap;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageResponse extends ErrorAndBaseData {
    public I18nMessage message = null;

    public MessageResponse reason(String key, String explanation) {
        addErrorReason(key, explanation);
        return this;
    }


    public MessageResponse reason(LinkedHashMap<String, String> details) {
        addI18nReason(details);
        return this;
    }

    public MessageResponse errorMessage(String message) {
        addErrorMessage(message);
        return this;
    }

    public MessageResponse status(Status status) {
        this.status = status;
        return this;
    }

    public MessageResponse setCode(String code) {
        this.code = code;
        return this;
    }

    public MessageResponse updateMessageKey(String key) {
        if (message != null) {
            message.key = key;
        }
        return this;
    }
}
