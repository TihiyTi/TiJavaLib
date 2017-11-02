package com.ti.comm.implem.simple;

import com.ti.comm.core.protocol.AbstractProtocol;

import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentLinkedDeque;

public class IntFlowProtocol extends AbstractProtocol<Integer,Integer> {

    @Override
    public ByteBuffer createResponseToByte(Integer intValue) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(intValue);
        return buffer;
    }

    @Override
    public Integer createByteToRequest(ByteBuffer buffer) {
        return buffer.getInt();
    }

    @Override
    public boolean checkProtocol(ConcurrentLinkedDeque<Byte> deque){
        return true;
    }
    @Override
    public void parseQueue(ConcurrentLinkedDeque<Byte> deque){
        if(deque.size()>4){
            ByteBuffer buffer = ByteBuffer.allocate(4);
            buffer.put(deque.poll());
            buffer.put(deque.poll());
            buffer.put(deque.poll());
            buffer.put(deque.poll());
            serialControllableList.forEach(x->x.serviceRequest(createByteToRequest(buffer)));
        }
    }
}
