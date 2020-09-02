package com.problemfighter.pfspring.restapi.common;

import com.problemfighter.java.oc.reflection.ReflectionProcessor;
import com.problemfighter.pfspring.restapi.rr.RequestProcessor;
import com.problemfighter.pfspring.restapi.rr.request.RequestBulkData;
import com.problemfighter.pfspring.restapi.rr.response.*;

import java.lang.reflect.Field;
import java.util.*;

public class DataUtil {

    private ReflectionProcessor reflectionProcessor;
    private RequestProcessor requestProcessor;

    public DataUtil() {
        reflectionProcessor = new ReflectionProcessor();
        requestProcessor = new RequestProcessor();
    }

    public <D> List<Long> getAllId(RequestBulkData<D> data){
        return getAllId(data.getData());
    }

    public <D> List<Long> getAllId(List<D> list) {
        return getFieldValues(list, "id", Long.class);
    }

    public <D, L> List<L> getFieldValues(List<D> list, String name, Class<L> klass) {
        List<L> response = new ArrayList<>();
        Field field;
        for (D data : list) {
            try {
                field = reflectionProcessor.getAnyFieldFromObject(data, name);
                if (field != null && field.getType() == klass) {
                    field.setAccessible(true);
                    response.add((L) field.get(data));
                }
            } catch (IllegalAccessException ignore) {

            }
        }
        return response;
    }

    public <D> List<D> markAsDeletedFlag(List<D> dataList, Boolean isDeleted) {
        for (D data: dataList){
            markAsDeletedFlag(data, isDeleted);
        }
        return dataList;
    }

    public <D> Iterable<D> markAsDeleted(Iterable<D> dataList) {
        return markAsDeletedFlag(dataList, true);
    }

    public <D> Iterable<D> markAsUndeleted(Iterable<D> dataList) {
        return markAsDeletedFlag(dataList, false);
    }

    private  <D> Iterable<D> markAsDeletedFlag(Iterable<D> dataList, Boolean isDeleted) {
        for (D data : dataList) {
            markAsDeletedFlag(data, isDeleted);
        }
        return dataList;
    }

    public <D> D getObjectFromList(List<D> dataList, String fieldName, Object dataObject) {
        Field listField, dataField;
        for (D data : dataList) {
            try {
                listField = reflectionProcessor.getAnyFieldFromObject(data, fieldName);
                dataField = reflectionProcessor.getAnyFieldFromObject(dataObject, fieldName);
                if (listField != null && dataField != null && listField.getType() == dataField.getType()) {
                    listField.setAccessible(true);
                    dataField.setAccessible(true);
                    if (listField.get(data).equals(dataField.get(dataObject))) {
                        return data;
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public <E, D> BulkErrorValidEntities<D, E> merge(Iterable<E> mergeTo, RequestBulkData<D> data) {
        BulkErrorValidEntities<D, E> bulkErrorValidEntities = new BulkErrorValidEntities<>();
        List<D> sourceList = data.getData();
        D source;
        for (E entity : mergeTo) {
            source = getObjectFromList(sourceList, "id", entity);
            if (source != null) {
                try {
                    bulkErrorValidEntities.addToList(requestProcessor.copySrcToDstValidate(source, entity));
                } catch (ApiRestException e) {
                    MessageResponse messageResponse = (MessageResponse) e.getError();
                    bulkErrorValidEntities.addFailed(new BulkErrorData<D>().addError(messageResponse.error).addObject(source));
                }
                sourceList.remove(source);
            }
        }
        ErrorData error = new ErrorData();
        error.message = I18nMessage.message("Unable to process update");
        for (D errorSource : sourceList) {
            bulkErrorValidEntities.addFailed(new BulkErrorData<D>().addError(error).addObject(errorSource));
        }
        return bulkErrorValidEntities;
    }

    private  <D> D markAsDeletedFlag(D data, Boolean isDeleted) {
        return updateProperty(data, Map.of("isDeleted", isDeleted));
    }

    public <D> D markAsUndeleted(D data) {
        return markAsDeletedFlag(data, false);
    }

    public <D> D markAsDeleted(D data) {
        return markAsDeletedFlag(data, true);
    }

    public <D> List<D> updateProperty(List<D> dataList, Map<String, Object> fieldValue) {
        for (D data : dataList) {
            updateProperty(data, fieldValue);
        }
        return dataList;
    }

    public <D> D updateProperty(D data, Map<String, Object> fieldValue) {
        Field field;
        for (Map.Entry<String, Object> entry : fieldValue.entrySet()) {
            try {
                field = reflectionProcessor.getAnyFieldFromObject(data, entry.getKey());
                if (field != null) {
                    field.setAccessible(true);
                    field.set(data, entry.getValue());
                }
            } catch (IllegalAccessException ignore) {
            }
        }
        return data;
    }

    public <E> E validateAndOptionToEntity(Optional<E> optional, String message) {
        if (optional.isPresent()) {
            return optional.get();
        } else if (message != null) {
            ApiRestException.error(message);
        }
        return null;
    }

    public static DataUtil instance() {
        return new DataUtil();
    }
}
