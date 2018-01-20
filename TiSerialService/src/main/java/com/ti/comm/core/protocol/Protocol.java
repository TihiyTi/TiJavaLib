package com.ti.comm.core.protocol;

import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentLinkedDeque;

public interface Protocol<RESPONSE, REQUEST> {
    //Call from Controller to send RESPONSE from AbstractProtocol to Sender
    void sendResponse(RESPONSE response);

    //
    ByteBuffer createResponseToByte(RESPONSE response);
    REQUEST createByteToRequest(ByteBuffer buffer);

    default void parse(ConcurrentLinkedDeque<Byte> deque){
        System.out.println("Unsupported method");
    };
}
