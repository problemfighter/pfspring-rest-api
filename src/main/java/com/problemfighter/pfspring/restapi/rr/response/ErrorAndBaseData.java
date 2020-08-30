package com.problemfighter.pfspring.restapi.rr.response;


import java.util.LinkedHashMap;
import java.util.Map;

public class ErrorAndBaseData extends BaseData {
    public ErrorData error;

    public void initErrorData() {
        if (error == null) {
            error = new ErrorData();
        }
    }

    public void addErrorMessage(String message) {
        initErrorData();
        error.message = new I18nMessage(message).setTextToKey(message);
    }

    public void addErrorReason(String key, String explanation) {
        initErrorData();
        if (error.details == null) {
            error.details = new LinkedHashMap<>();
        }
        error.details.put(key, new I18nMessage(explanation).setTextToKey(explanation));
    }

    public void addI18nReason(LinkedHashMap<String, String> details) {
        for (Map.Entry<String, String> entry : details.entrySet()) {
            addErrorReason(entry.getKey(), entry.getValue());
        }
    }

    public void updateErrorMessageKey(String key) {
        if (error != null && error.message != null) {
            error.message.key = key;
        }
    }

}
