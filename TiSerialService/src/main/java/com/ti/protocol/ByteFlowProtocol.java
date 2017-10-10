package com.ti.protocol;

import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ByteFlowProtocol extends AbstractProtocol<ByteBuffer,ByteBuffer>{

    @Override
    public ByteBuffer createResponseToByte(ByteBuffer buffer) {
        return buffer;
    }

    @Override
    public ByteBuffer createByteToRequest(ByteBuffer buffer) {
        return buffer;
    }

    @Override
    public boolean checkProtocol(ConcurrentLinkedDeque<Byte> deque){
        return true;
    }
    @Override
    void parseQueue(ConcurrentLinkedDeque<Byte> deque){
        ByteBuffer buffer = ByteBuffer.allocate(deque.size());
        deque.forEach(buffer::put);
        upByteBuffer(buffer);
    }

}
