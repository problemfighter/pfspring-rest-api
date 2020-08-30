package com.problemfighter.pfspring.restapi.rr;


import com.problemfighter.java.oc.common.InitCustomProcessor;
import com.problemfighter.java.oc.common.ObjectCopierException;
import com.problemfighter.java.oc.common.ProcessCustomCopy;
import com.problemfighter.java.oc.copier.ObjectCopier;
import com.problemfighter.java.oc.reflection.ReflectionProcessor;
import com.problemfighter.pfspring.common.common.SpringContext;
import com.problemfighter.pfspring.restapi.common.ApiProcessorException;
import com.problemfighter.pfspring.restapi.rr.request.RequestBulkData;
import com.problemfighter.pfspring.restapi.rr.request.RequestData;
import com.problemfighter.pfspring.restapi.rr.response.BulkErrorData;
import com.problemfighter.pfspring.restapi.rr.response.BulkErrorValidEntities;
import com.problemfighter.pfspring.restapi.rr.response.MessageResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Optional;

public class ReqProcessor {

    private ObjectCopier objectCopier;
    private ReflectionProcessor reflectionProcessor;
    public static Integer itemPerPage = 15;
    public static String sortField = "id";
    public static Sort.Direction sortOrder = Sort.Direction.DESC;

    public ReqProcessor() {
        this.objectCopier = new ObjectCopier();
        this.objectCopier.initCustomProcessor = new InitCustomProcessor() {
            @Override
            public <S, D> ProcessCustomCopy<S, D> init(Class<?> klass, S source, D destination) {
                return (ProcessCustomCopy<S, D>) SpringContext.getBean(klass);
            }
        };
        this.reflectionProcessor = new ReflectionProcessor();
    }

    public <D> D copySrcToDst(Object source, D destination) {
        try {
            return this.objectCopier.copy(source, destination);
        } catch (ObjectCopierException e) {
            ApiProcessorException.otherError(e.getMessage());
        }
        return null;
    }

    public <D> D copySrcToDst(Object source, Class<D> destination) {
        try {
            return this.objectCopier.copy(source, destination);
        } catch (ObjectCopierException e) {
            ApiProcessorException.otherError(e.getMessage());
        }
        return null;
    }

    public Boolean dataValidate(Object source) {
        LinkedHashMap<String, String> errors = this.objectCopier.validateObject(source);
        if (errors.size() != 0) {
            ApiProcessorException.throwException(ResProcessor.validationError().reason(errors));
            return false;
        }
        return true;
    }

    public void dataValidate(RequestData<?> requestData) {
        dataValidate(requestData.getData());
    }

    public <D> D copySrcToDstValidate(Object source, Class<D> destination) {
        dataValidate(source);
        return copySrcToDst(source, destination);
    }

    public <D> D copySrcToDstValidate(Object source, D destination) {
        if (dataValidate(source)) {
            return copySrcToDst(source, destination);
        }
        return null;
    }

    // Quick Access
    public static ReqProcessor instance() {
        return new ReqProcessor();
    }

    public <D> D copyOnly(Object source, Class<D> destination) throws ApiProcessorException {
        return copySrcToDst(source, destination);
    }

    public <D> D copyOnly(Object source, D destination) throws ApiProcessorException {
        return copySrcToDst(source, destination);
    }

    public <D> D process(Object source, Class<D> destination) {
        return copySrcToDstValidate(source, destination);
    }

    public <D> D process(Object source, D destination) {
        return copySrcToDstValidate(source, destination);
    }

    public <D> D process(RequestData<?> requestData, D destination) {
        return copySrcToDstValidate(requestData.getData(), destination);
    }

    public <D> D process(RequestData<?> requestData, Class<D> destination) {
        return copySrcToDstValidate(requestData.getData(), destination);
    }


    public <D, E> BulkErrorValidEntities<D, E> bulkProcess(RequestBulkData<D> requestData, Class<E> destination) {
        BulkErrorValidEntities<D, E> errorDst = new BulkErrorValidEntities<>();
        for (D object : requestData.getData()) {
            try {
                errorDst.addToList(copySrcToDstValidate(object, destination));
            } catch (ApiProcessorException e) {
                MessageResponse messageResponse = (MessageResponse) e.getError();
                errorDst.addFailed(new BulkErrorData<D>().addError(messageResponse.error).addObject(object));
            }
        }
        return errorDst;
    }

    public <O> Long validateId(Long id, String message) {
        if (id == null && message != null) {
            ApiProcessorException.error(message);
        }
        return id;
    }

    public <O> Long getId(RequestData<O> data) {
        return getIdFieldValue(data.getData());
    }

    public <O> Long validateNGetId(RequestData<O> data, String message) {
        Long id = getIdFieldValue(data.getData());
        return validateId(id, message);
    }

    public <E> E validateNOp2Entity(Optional<E> optional, String message) {
        if (optional.isPresent()) {
            return optional.get();
        } else if (message != null) {
            ApiProcessorException.error(message);
        }
        return null;
    }

    public <O> Long getIdFieldValue(O object) {
        return getFieldValue(object, "id", Long.class);
    }

    private <T, O> T getFieldValue(O object, String name, Class<T> type) {
        try {
            Field field = reflectionProcessor.getFieldFromObject(object, name);
            if (field == null || field.getType() != type) {
                return null;
            }
            return (T) field.get(object);
        } catch (IllegalAccessException ignore) {
            return null;
        }
    }

    public PageRequest paginationNSort(Integer page, Integer size, String sort, String field) {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = itemPerPage;
        }

        Sort.Direction order = sortOrder;
        if (sort != null && sort.equals("asc")) {
            order = Sort.Direction.ASC;
        }

        if (field == null || field.equals("")) {
            field = sortField;
        }
        return PageRequest.of(page, size, order, field);
    }

}
