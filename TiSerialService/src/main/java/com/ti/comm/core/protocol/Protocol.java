package com.ti.comm.core.protocol;

import com.ti.comm.dev.DeviceInterface;

import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentLinkedDeque;

public interface Protocol<RESPONSE, REQUEST> {
    //Call from Controller to send RESPONSE from AbstractProtocol to Sender
    void sendResponse(RESPONSE response);

    //
    ByteBuffer createResponseToByte(RESPONSE response);
    REQUEST createByteToRequest(ByteBuffer buffer);

    default void parse(ConcurrentLinkedDeque<Byte> deque){
        System.out.println("Unsupported method in this protocol");
    }
    default void setDevice(DeviceInterface device){
        System.out.println("Unsupported method in this protocol");
    }

    @Deprecated
    default boolean checkProtocol(ConcurrentLinkedDeque<Byte> deque){
        System.out.println("Unsupported in new version");
        return false;
    }
    @Deprecated
    default void parseQueue(ConcurrentLinkedDeque<Byte> deque){
        System.out.println("Unsupported in new version");
    }
}
