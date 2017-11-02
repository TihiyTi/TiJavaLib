package com.ti.comm.core.checkers;

import com.ti.comm.core.protocol.AbstractProtocol;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;

public class CommandSplitter <REQUEST>  implements CommandSplittable<REQUEST> {
    public Map<Byte, Integer> commandSizes;
    boolean partCommand = false;
    private AbstractProtocol protocol;
    byte sync;

    // TODO: 23.10.2017 реализовать возможность синхнонизации несколькими байтами
    public CommandSplitter(byte sync) {
        this.sync = sync;
    }

    @Override
    public void setProtocol(AbstractProtocol protocol) {
        this.protocol = protocol;
    }

    @Override
    public void parseQueue(ConcurrentLinkedDeque<Byte> deque) {
        partCommand = false;
        while(deque.size()>2 && !partCommand){
            splitCommand(deque);
        }
    }

    @Override
    public ByteBuffer getSyncSequence() {
        // TODO: 23.10.2017 буффер можно не создавать, а передавать ссылку на постоянный буфер
        return ByteBuffer.allocate(1).put(sync);
    }

    @Override
    public void setCommandSizeMap(Map<Byte,Integer> map) {
        commandSizes = map;
    }

    private void splitCommand(ConcurrentLinkedDeque<Byte> deque){
        List<Byte> bufferByteList = new ArrayList<>();
        bufferByteList.add(deque.poll());
        Byte command = deque.poll();
        bufferByteList.add(command);
//            System.out.println("Command " + com.ti.comm.core.command);
        if(commandSizes.containsKey(command) && deque.size() >=  commandSizes.get(command)){
            int commandSize = commandSizes.get(command);
            byte[] data = new byte[commandSize];
            for(int i = 0; i < commandSize; i++){
                data[i] = deque.poll();
            }
//                System.out.println("Data " + data.toString());
            ByteBuffer buffer = ByteBuffer.allocate(commandSize+1);
            buffer.put(command);
            buffer.put(data);
//            System.out.println("Split: " + Arrays.toString(buffer.array()));
            buffer.rewind();
            protocol.upByteBuffer(buffer);
        }else{
            for(int i = bufferByteList.size()-1; i > -1; i--){
                deque.offerFirst(bufferByteList.get(i));
            }
            bufferByteList.clear();
            partCommand = true;
        }
    }

}
