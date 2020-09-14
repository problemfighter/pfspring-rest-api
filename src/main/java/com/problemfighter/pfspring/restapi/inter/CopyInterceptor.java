package com.problemfighter.pfspring.restapi.inter;


import com.problemfighter.java.oc.common.ProcessCustomCopy;
import com.problemfighter.pfspring.restapi.inter.model.RestDTO;
import com.problemfighter.pfspring.restapi.inter.model.RestEntity;

public interface CopyInterceptor<E extends RestEntity, D extends RestDTO, U> extends ProcessCustomCopy<E, D> {

    void meAsSrc(U source, E destination);

    void meAsDst(E source, U destination);
}
