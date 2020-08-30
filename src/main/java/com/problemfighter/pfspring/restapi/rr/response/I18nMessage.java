package com.problemfighter.pfspring.restapi.rr.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class I18nMessage {
    public String text;
    public String key;

//    TODO: Will Implement Later
//    public String code;

    public I18nMessage() {
    }

    public I18nMessage(String text) {
        this.text = text;
    }

    public I18nMessage(String text, String key) {
        this.text = text;
        this.key = key;
    }

    public I18nMessage setText(String text) {
        this.text = text;
        return this;
    }

    public I18nMessage setKey(String key) {
        this.key = key;
        return this;
    }


    public I18nMessage setTextToKey(String text) {
        this.key = textToKey(text);
        return this;
    }

    public String textToKey(String text) {
        if (text == null || text.isBlank() || text.isEmpty()) {
            return null;
        }
        text = text.toLowerCase();
        text = text.replace(" ", ".");
        return text;
    }

    public static I18nMessage message(String text) {
        if (text == null) {
            return null;
        }
        return new I18nMessage(text).setTextToKey(text);
    }
}
