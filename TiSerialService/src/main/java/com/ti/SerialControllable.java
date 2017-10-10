package com.ti;

import com.ti.protocol.Protocol;

public interface  SerialControllable <RESPONSE, REQUEST> {
    //Call from AbstractProtocol to send REQUEST from Controller to application
    void serviceRequest(REQUEST request);
    void toServiceResponse(RESPONSE response);
    void addProtocol(Protocol<RESPONSE, REQUEST> protocol);
}
