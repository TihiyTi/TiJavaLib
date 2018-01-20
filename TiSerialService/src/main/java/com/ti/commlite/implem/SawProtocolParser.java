package com.ti.commlite.implem;

import com.ti.comm.core.protocol.AbstractProtocol;
import com.ti.commlite.core.checkers.ProtocolParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class SawProtocolParser implements ProtocolParser {
    private static final Logger LOG = LogManager.getLogger("serialServiceLogger");

    private int dataSizeInByte;
    private int wordSizeInByte;
    private int skipByteCount = 0;
    private byte inputCurrentSawValue = 0;
    private byte outputCurrentValue = 0;

    private AbstractProtocol protocol;

    public SawProtocolParser(int dataSizeInByte) {
        this.dataSizeInByte = dataSizeInByte;
        wordSizeInByte = dataSizeInByte + 1; //plus saw byte
    }

    @Override
    public boolean checkProtocol(ConcurrentLinkedDeque<Byte> deque) {
        if(deque.peek() == inputCurrentSawValue +1){
//            inputCurrentSawValue++;
            LOG.trace("First byte "+ deque.peek() +" == "+ (inputCurrentSawValue+1)+" Size = " + deque.size());
            return true;
        }else{
            return findProtocol(deque);
        }
    }

    private boolean findProtocol(ConcurrentLinkedDeque<Byte> deque){
        ByteBuffer buffer = ByteBuffer.allocate(wordSizeInByte *4);
        if(deque.size() > wordSizeInByte *4){
            for (int i = 0; i < wordSizeInByte * 4; i++) {
                buffer.put(deque.poll());
            }
            Integer sawIndex = null;
            for (int i = 0; i < wordSizeInByte; i++) {
                if( (buffer.get(i) == (byte)(buffer.get(i + wordSizeInByte)-1))  &
                        (buffer.get(i + wordSizeInByte) == (byte)(buffer.get(i + 2* wordSizeInByte) - 1))  ){
                    sawIndex = i;
                }
            }
            if(sawIndex == null){
                skipByteCount = skipByteCount + wordSizeInByte *4;
                LOG.trace("Skip "+ wordSizeInByte *4+" byte. Total skipped - "+skipByteCount);
                return false;
            }else{
                inputCurrentSawValue = buffer.get(sawIndex);
                for (int i = wordSizeInByte *4 -1; i > sawIndex - 1; i--) {
                    deque.offerFirst(buffer.get(i));
                }
                skipByteCount = skipByteCount + sawIndex;
                LOG.trace("Protocol found. Skipped - "+skipByteCount +" bytes. Current SAW value " + inputCurrentSawValue);
                return true;
            }
        }else {
            return false;
        }
    }

    @Override
    public List<ByteBuffer> parseQueue(ConcurrentLinkedDeque deque) {
        List<ByteBuffer> list = new ArrayList<>();
        while(deque.size() >= wordSizeInByte){
            inputCurrentSawValue = (byte)deque.poll();
            ByteBuffer buffer = ByteBuffer.allocate(dataSizeInByte);
            for (int i = 0; i < dataSizeInByte; i++) {
                buffer.put((byte)deque.poll());
            }
            list.add(buffer);
        }
        return list;
    }

    @Override
    public ByteBuffer appendSyncToBuffer(ByteBuffer bufferWithOutHead) {
        outputCurrentValue++;
        bufferWithOutHead.rewind();
        return ByteBuffer.allocate(bufferWithOutHead.limit()+1).put(outputCurrentValue).put(bufferWithOutHead);
    }
}
