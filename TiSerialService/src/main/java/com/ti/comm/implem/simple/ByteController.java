package com.ti.comm.implem.simple;

import com.ti.comm.core.protocol.AbstractSerialController;

import java.nio.ByteBuffer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ByteController extends AbstractSerialController<ByteBuffer,ByteBuffer>{
    public BlockingQueue<Byte> testQueue = new LinkedBlockingQueue<>();

    @Override
    public void serviceRequest(ByteBuffer command) {
        for (byte b : command.array()) {
            testQueue.add(b);
        }
    }

    public void send(byte[] array){
        ByteBuffer buffer = ByteBuffer.wrap(array);
        toServiceResponse(buffer);
    }
}
