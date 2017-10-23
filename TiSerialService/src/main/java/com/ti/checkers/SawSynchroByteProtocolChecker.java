package com.ti.checkers;

import com.ti.protocol.AbstractProtocol;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;

public class SawSynchroByteProtocolChecker implements ProtocolCheckable, CommandSplittable{
    private static final Logger LOG = LogManager.getLogger("serialServiceLogger");

    private int dataSizeInByte;
    private int wordSizeInByte;
    private int skipByteCount = 0;
    private byte inputCurrentSawValue = 0;
    private byte outputCurrentValue = 0;

    private AbstractProtocol protocol;

    public SawSynchroByteProtocolChecker(int dataSizeInByte) {
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
                // TODO: 21.10.2017 проверить когда идет перескок с положительного на отрицательное значение байта
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
//                    buffer.
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
    public void setCommandSizeMap(Map map) {

    }

    @Override
    public void parseQueue(ConcurrentLinkedDeque deque) {
        while(deque.size() > wordSizeInByte){
            inputCurrentSawValue = (byte)deque.poll();
            ByteBuffer buffer = ByteBuffer.allocate(dataSizeInByte);
            for (int i = 0; i < dataSizeInByte; i++) {
                buffer.put((byte)deque.poll());
            }
            protocol.upByteBuffer(buffer);
        }
    }

    @Override
    public ByteBuffer getSyncSequence() {
        outputCurrentValue++;
        return ByteBuffer.allocate(1).put(outputCurrentValue);
    }

    @Override
    public void setProtocol(AbstractProtocol protocol) {
        this.protocol = protocol;
    }
}
