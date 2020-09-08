package com.problemfighter.pfspring.restapi.exception;

import com.problemfighter.pfspring.restapi.common.RestSpringContext;
import com.problemfighter.pfspring.restapi.rr.ResponseProcessor;
import com.problemfighter.pfspring.restapi.rr.response.MessageResponse;
import org.springframework.core.env.Environment;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

public class ExceptionProcessor {

    private Environment environment;

    public ExceptionProcessor() {
        environment = RestSpringContext.environment();
    }

    public static ExceptionProcessor instance() {
        return new ExceptionProcessor();
    }

    public String eng() {
        if (environment != null) {
            return environment.getActiveProfiles()[0];
        }
        return null;
    }

    public String handleHibernateException(Throwable throwable) {
        if (throwable.getCause() != null) {
            return throwable.getCause().getMessage();
        }
        return throwable.getMessage();
    }

    private String exceptionMessageGenerator(Exception exception, String message) {
        if (exception instanceof MethodArgumentTypeMismatchException) {
            return ExceptionMessage.invalidRequestParams;
        }
//        else if (exception.getCause() instanceof HibernateException) {
//            message = handleHibernateException(exception.getCause());
//        }
        return message;
    }

    public MessageResponse handleException(Exception exception) {
        String message = exception.getMessage();
        String code = ErrorCode.unknownError;
        message = exceptionMessageGenerator(exception, message);
        if (eng() != null && eng().equals("local")) {
            exception.printStackTrace();
        }
        MessageResponse messageResponse = ResponseProcessor.errorMessage(message).setCode(code);
        messageResponse.updateErrorMessageKey(null);
        return messageResponse;
    }
}
