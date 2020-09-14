package com.problemfighter.pfspring.restapi.rr.response;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class BulkErrorValidEntities<D, E> extends BulkResponse<D> {
    public List<E> entityList = new ArrayList<>();

    public BulkErrorValidEntities<D, E> addToList(E item) {
        entityList.add(item);
        return this;
    }

    public Boolean isValidEntities() {
        return entityList.size() != 0;
    }

    public List<E> getEntities() {
        return this.entityList;
    }

}
