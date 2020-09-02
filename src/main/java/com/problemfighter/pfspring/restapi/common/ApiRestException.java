package com.problemfighter.pfspring.restapi.common;

import com.problemfighter.pfspring.restapi.rr.ResponseProcessor;

/**
 * Created by Touhid Mia on 11/09/2014.
 */
public class ApiRestException extends RuntimeException {

    public Object errorMessage;

    public ApiRestException() {
        super("Api Processor Exception");
    }

    public ApiRestException(String message) {
        super(message);
    }

    public ApiRestException error(Object errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public Object getError() {
        if (this.errorMessage == null) {
            return ResponseProcessor.unknownError();
        }
        return this.errorMessage;
    }

    public static void throwException(Object errorMessage) throws ApiRestException {
        throw new ApiRestException().error(errorMessage);
    }

    public static void notFound() {
        throwException(ResponseProcessor.notFound());
    }

    public static void unauthorized() {
        throwException(ResponseProcessor.unauthorized());
    }

    public static void otherError(String errorMessage) {
        throwException(ResponseProcessor.otherError(errorMessage));
    }

    public static void error(String message) {
        throwException(ResponseProcessor.errorMessage(message));
    }
}
