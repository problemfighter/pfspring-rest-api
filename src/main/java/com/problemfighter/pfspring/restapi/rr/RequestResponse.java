package com.problemfighter.pfspring.restapi.rr;

import com.problemfighter.pfspring.restapi.common.DataUtil;

public interface RequestResponse {

    default ReqProcessor req() {
        return new ReqProcessor();
    }

    default ResProcessor res() {
        return ResProcessor.instance();
    }

    default DataUtil du(){
        return DataUtil.instance();
    }
}
