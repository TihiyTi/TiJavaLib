package com.ti;

import com.ti.protocol.Protocol;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSerialController<RESPONSE, REQUEST> implements SerialControllable<RESPONSE, REQUEST>{
    List<Protocol<RESPONSE, REQUEST>> list = new ArrayList<>();

    @Override
    abstract public void serviceRequest(REQUEST command);

    @Override
    public void toServiceResponse(RESPONSE command) {
        list.forEach(x->x.sendResponse(command));
    }

    @Override
    public void addProtocol(Protocol<RESPONSE, REQUEST> protocol) {
        list.add(protocol);
    }
}
