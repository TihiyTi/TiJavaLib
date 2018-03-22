package com.ti.comm.dev;

import com.ti.FileService;
import com.ti.comm.core.protocol.AbstractProtocol;
import com.ti.comm.core.protocol.Protocol;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FileByteProducer implements DeviceInterface{
    private static final Logger LOG = LogManager.getLogger("TiSerialServiceLogger");

    private ConcurrentLinkedDeque<Byte> deque = new ConcurrentLinkedDeque<Byte>();

    private int totalReadedByte = 0;

    private Protocol protocol;
    private Path filePath;
    private FileService service;
    private ReadType type;
    // TODO: 20.01.2018 remove flag "liteProtocol"  after finally migrate to lite protocols
    // and remove @deprecated method
    public boolean liteProtocol = false;

    boolean fileIsEnd = false;

    public FileByteProducer(Path filePath, ReadType type) {
        this.filePath = filePath;
        this.type = type;
        service =  new FileService(filePath);
    }

    // TODO: 03.02.2018 Add test to this methods
    public void readWithDelay(long milliseconds, int byteToRead){
        if(type == ReadType.READ_CONTINIOUS){
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.scheduleWithFixedDelay(()->{
                ByteBuffer buffer = service.readBytes(byteToRead);
                int readByte = buffer.limit();
                if(readByte==0){
                    fileIsEnd=true;
                    executorService.shutdown();
                }else{
                    buffer.rewind();
                    byte[] buf = new byte[buffer.limit()];
                    buffer.get(buf, 0, buf.length);
                    for (byte element: buf){
                        deque.add(element);
                    }
                    protocol.parse(deque);
                }
            }, 0L, milliseconds, TimeUnit.MILLISECONDS);
        }else{
            System.out.println("Used continuous method without continuous settings!");
        }
    }

    public void read(){
        boolean empty = false;
        while(!empty){
            ByteBuffer buffer = service.readBytes();
            int readByte = buffer.limit();

            totalReadedByte = totalReadedByte + readByte;
            if(totalReadedByte%10000 == 0){
                LOG.trace(""+ totalReadedByte+ " byte was read from file.");
            }

            if(readByte==0){
                empty=true;
            }else{
                buffer.rewind();
//                System.out.println("Read " + readByte);
                byte[] buf = new byte[buffer.limit()];
                buffer.get(buf, 0, buf.length);
//                System.out.println("Read " + buf.length);
                for (byte element: buf){
                    deque.add(element);
                }
                if(liteProtocol){
                    protocol.parse(deque);
                }else {
                    if(protocol.checkProtocol(deque)){
                        protocol.parseQueue(deque);
                    }
                }
            }
        }
    }


    @Override
    public void sendDataArray(ByteBuffer sendBuffer) {

    }

    @Override
    public <RESPONSE, REQUEST> void setProtocol(Protocol<RESPONSE, REQUEST> protocol) {
        this.protocol = protocol;
        // TODO: 12.11.2017 судя по всему подписка протокола на sender дублируется в SerialService
        // TODO: 21.01.2018 remove if_else block after migrate to lite version of protocol
        if(liteProtocol){
            protocol.setDevice(this);
        }else {
            ((AbstractProtocol)protocol).setSender(this);
        }
    }

    public enum ReadType{
        READ_ALL, READ_CONTINIOUS
    }
}
