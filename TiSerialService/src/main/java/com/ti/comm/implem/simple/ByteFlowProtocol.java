package com.ti.comm.implem.simple;

import com.ti.comm.core.protocol.AbstractProtocol;

import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ByteFlowProtocol extends AbstractProtocol<ByteBuffer,ByteBuffer> {

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
    public void parseQueue(ConcurrentLinkedDeque<Byte> deque){
        ByteBuffer buffer = ByteBuffer.allocate(deque.size());
        deque.forEach(buffer::put);
        upByteBuffer(buffer);
    }

}
