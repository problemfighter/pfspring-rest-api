package com.problemfighter.pfspring.restapi.exception;

import com.problemfighter.pfspring.restapi.rr.ResProcessor;
import com.problemfighter.pfspring.restapi.rr.response.MessageResponse;
import org.springframework.http.HttpStatus;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

public class HttpToApiException {


    private int getResponseCode(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            return Integer.parseInt(status.toString());
        }
        return 0;
    }


    public MessageResponse processError(HttpServletRequest request) {
        int statusCode = getResponseCode(request);
        if (statusCode == HttpStatus.NOT_FOUND.value()) {
            return ResProcessor.notFound();
        } else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
            return ResProcessor.badRequest();
        } else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
            return ResProcessor.unauthorized();
        } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
            return ResProcessor.forbidden();
        } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            return ResProcessor.codeError();
        }
        return ResProcessor.unknownError();
    }

    public static MessageResponse handleException(HttpServletRequest request) {
        return new HttpToApiException().processError(request);
    }

}
