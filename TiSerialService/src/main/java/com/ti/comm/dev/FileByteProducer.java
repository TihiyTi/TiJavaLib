package com.ti.comm.dev;

import com.ti.FileService;
import com.ti.comm.core.protocol.AbstractProtocol;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FileByteProducer implements DeviceInterface{
    private ConcurrentLinkedDeque<Byte> deque = new ConcurrentLinkedDeque<Byte>();

    private AbstractProtocol protocol;
    private Path filePath;
    private FileService service;

    public FileByteProducer(Path filePath, ReadType type) {
        this.filePath = filePath;
        service =  new FileService(filePath);
        if(type == ReadType.READ_CONTINIOUS){
            Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(()->{
                byte[] buf = service.readBytes().array();
                for (byte element: buf){
                    deque.add(element);
                }
                if(protocol.checkProtocol(deque)){
                    protocol.parseQueue(deque);
                }
            }, 0L, 100L, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void sendDataArray(ByteBuffer sendBuffer) {

    }

    @Override
    public <RESPONSE, REQUEST> void setProtocol(AbstractProtocol<RESPONSE, REQUEST> protocol) {
        this.protocol = protocol;
        // TODO: 12.11.2017 судя по всему подписка протокола на sender дублируется в SerialService
        protocol.setSender(this);
    }

    public enum ReadType{
        READ_ALL, READ_CONTINIOUS
    }
}
