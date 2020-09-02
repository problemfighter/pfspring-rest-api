package com.problemfighter.pfspring.restapi.rr;

import com.problemfighter.pfspring.restapi.common.DataUtil;

public interface RequestResponse {

    default RequestProcessor requestProcessor() {
        return new RequestProcessor();
    }

    default ResponseProcessor responseProcessor() {
        return ResponseProcessor.instance();
    }

    default DataUtil dataUtil(){
        return DataUtil.instance();
    }
}
