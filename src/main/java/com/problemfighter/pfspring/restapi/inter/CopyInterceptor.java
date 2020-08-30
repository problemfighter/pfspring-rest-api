package com.problemfighter.pfspring.restapi.inter;


import com.problemfighter.java.oc.common.ProcessCustomCopy;
import com.problemfighter.pfspring.common.model.DTOCommon;
import com.problemfighter.pfspring.common.model.EntityCommon;

public interface CopyInterceptor<E extends EntityCommon, D extends DTOCommon, U> extends ProcessCustomCopy<E, D> {

    void meAsSrc(U source, E destination);

    void meAsDst(E source, U destination);
}
