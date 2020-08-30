package com.problemfighter.pfspring.restapi.common;

import com.problemfighter.pfspring.restapi.rr.ResProcessor;

/**
 * Created by Touhid Mia on 11/09/2014.
 */
public class ApiProcessorException extends RuntimeException {

    public Object errorMessage;

    public ApiProcessorException() {
        super("Api Processor Exception");
    }

    public ApiProcessorException(String message) {
        super(message);
    }

    public ApiProcessorException error(Object errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public Object getError() {
        if (this.errorMessage == null) {
            return ResProcessor.unknownError();
        }
        return this.errorMessage;
    }

    public static void throwException(Object errorMessage) throws ApiProcessorException {
        throw new ApiProcessorException().error(errorMessage);
    }

    public static void notFound() {
        throwException(ResProcessor.notFound());
    }

    public static void unauthorized() {
        throwException(ResProcessor.unauthorized());
    }

    public static void otherError(String errorMessage) {
        throwException(ResProcessor.otherError(errorMessage));
    }

    public static void error(String message) {
        throwException(ResProcessor.errorMessage(message));
    }
}
