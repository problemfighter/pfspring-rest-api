package com.problemfighter.pfspring.restapi.rr;


import com.problemfighter.java.oc.common.InitCustomProcessor;
import com.problemfighter.java.oc.common.ObjectCopierException;
import com.problemfighter.java.oc.common.ProcessCustomCopy;
import com.problemfighter.java.oc.copier.ObjectCopier;
import com.problemfighter.pfspring.restapi.common.RestSpringContext;
import com.problemfighter.pfspring.restapi.exception.ResponseCode;
import com.problemfighter.pfspring.restapi.exception.ExceptionMessage;
import com.problemfighter.pfspring.restapi.rr.response.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ResponseProcessor {

    private ObjectCopier objectCopier;

    public ResponseProcessor() {
        this.objectCopier = new ObjectCopier();
        this.objectCopier.initCustomProcessor = new InitCustomProcessor() {
            @Override
            public <S, D> ProcessCustomCopy<S, D> init(Class<?> klass, S source, D destination) {
                return (ProcessCustomCopy<S, D>) RestSpringContext.getBean(klass);
            }
        };
    }

    private MessageResponse responseMessage(String message, String errorCode, ErrorData error) {
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.message = I18nMessage.message(message);
        messageResponse.code = errorCode;
        messageResponse.error = error;
        return messageResponse;
    }

    private <E, D> D convertEntityToDTO(E entity, Class<D> dto) throws ObjectCopierException {
        return objectCopier.copy(entity, dto);
    }

    public <E, D> List<D> entityToDTO(List<E> entities, Class<D> dto) {
        List<D> dtoList = new ArrayList<>();
        if (entities != null) {
            for (E entity : entities) {
                try {
                    D dtoObject = objectCopier.copy(entity, dto);
                    if (dtoObject != null) {
                        dtoList.add(dtoObject);
                    }
                } catch (ObjectCopierException ignore) {
                    ignore.printStackTrace();
                }
            }
        }
        return dtoList;
    }

    private <E> Boolean isEmptyEntity(E entity) {
        if (entity == null) {
            return true;
        } else if (entity instanceof Optional) {
            return ((Optional<?>) entity).isEmpty();
        }
        return false;
    }

    private <E> E getEntityValue(E entity) {
        if (entity == null) {
            return null;
        } else if (entity instanceof Optional) {
            Optional<E> optional = (Optional<E>) entity;
            if (optional.isEmpty()) {
                return null;
            } else {
                return optional.get();
            }
        }
        return entity;
    }

    // ===================================================== Refined Codes ============================================================================

    public MessageResponse response(String message, String errorCode) {
        return responseMessage(message, errorCode, null).status(Status.success);
    }

    public MessageResponse response(String message) {
        return response(message, ResponseCode.success);
    }

    public <E, D> PageableResponse<D> response(Page<E> page, Class<D> dto) {
        PageableResponse<D> pageableResponse = new PageableResponse<>();
        pageableResponse.data = entityToDTO(page.getContent(), dto);
        Pageable pageable = page.getPageable();
        pageableResponse.addPagination(pageable.getPageNumber(), pageable.getPageSize())
                .setTotal(page.getTotalElements())
                .setTotalPage(page.getTotalPages());
        pageableResponse.success();
        return pageableResponse;
    }

    public <D> DetailsResponse<D> response(D object) {
        DetailsResponse<D> detailsResponse = new DetailsResponse<>();
        detailsResponse.data = object;
        detailsResponse.success();
        return detailsResponse;
    }

    public <E, D> DetailsResponse<D> response(E source, Class<D> dto, String message) {
        DetailsResponse<D> detailsResponse = new DetailsResponse<>();
        try {
            source = getEntityValue(source);
            if (source != null) {
                detailsResponse.data = convertEntityToDTO(source, dto);
                detailsResponse.success();
            }
        } catch (ObjectCopierException e) {
            detailsResponse.addErrorMessage(e.getMessage());
        }
        if (message != null && detailsResponse.data == null) {
            detailsResponse.addErrorMessage(message);
            detailsResponse.error();
        }
        return detailsResponse;
    }

    public <E, D> DetailsResponse<D> response(E source, Class<D> dto) {
        return response(source, dto, null);
    }

    public <D> BulkResponse<D> response(BulkErrorValidEntities<D, ?> processed, Class<D> dto) {
        BulkResponse<D> bulkResponse = new BulkResponse<>();
        processed.addSuccessDataList(entityToDTO(processed.entityList, dto));
        bulkResponse.status = Status.partial;
        bulkResponse.code = ResponseCode.partial;
        if (processed.success == null || processed.success.size() == 0) {
            bulkResponse.status = Status.error;
            bulkResponse.code = ResponseCode.error;
            processed.success = null;
        } else if (processed.failed == null) {
            bulkResponse.status = Status.success;
            bulkResponse.code = ResponseCode.success;
        }
        bulkResponse.success = processed.success;
        bulkResponse.failed = processed.failed;
        return bulkResponse;
    }

    public static ResponseProcessor instance() {
        return new ResponseProcessor();
    }

    public <E, D> D entityToDTO(E entity, Class<D> dto) {
        try {
            return convertEntityToDTO(entity, dto);
        } catch (ObjectCopierException ignore) {
            return null;
        }
    }

    public MessageResponse error(String message, String errorCode) {
        return response(null, errorCode).errorMessage(message).status(Status.error);
    }

    public MessageResponse error(String message) {
        return error(message, ResponseCode.error);
    }


    // ===================================================== Static Methods ============================================================================

    public static MessageResponse unknownError() {
        return instance().error(ExceptionMessage.unknownError, ResponseCode.unknownError);
    }

    public static MessageResponse notFound(String message) {
        return errorMessage(message).setCode(ResponseCode.notFound);
    }

    public static MessageResponse notFound() {
        return notFound(ExceptionMessage.notFound);
    }

    public static MessageResponse badRequest(String message) {
        return errorMessage(message).setCode(ResponseCode.badRequest);
    }

    public static MessageResponse badRequest() {
        return badRequest(ExceptionMessage.badRequest);
    }

    public static MessageResponse unauthorized() {
        return unauthorized(ExceptionMessage.unauthorized);
    }

    public static MessageResponse unauthorized(String message) {
        return errorMessage(message).setCode(ResponseCode.unauthorized);
    }

    public static MessageResponse forbidden(String message) {
        return errorMessage(message).setCode(ResponseCode.forbidden);
    }

    public static MessageResponse forbidden() {
        return forbidden(ExceptionMessage.forbidden);
    }

    public static MessageResponse codeError(String message) {
        return errorMessage(message).setCode(ResponseCode.codeError);
    }

    public static MessageResponse codeError() {
        return codeError(ExceptionMessage.codeError);
    }

    public static MessageResponse validationError(String message) {
        return instance().error(message, ResponseCode.validationError);
    }

    public static MessageResponse validationError() {
        return validationError(ExceptionMessage.validationError);
    }

    public static MessageResponse errorMessage(String message) {
        return instance().error(message, ResponseCode.error);
    }

    public static MessageResponse successMessage(String message) {
        return instance().response(message, ResponseCode.success);
    }

    public static MessageResponse otherError(String message) {
        return errorMessage(message).setCode(ResponseCode.otherError);
    }

}
