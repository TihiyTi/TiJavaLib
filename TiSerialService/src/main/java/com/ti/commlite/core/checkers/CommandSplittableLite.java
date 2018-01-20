package com.ti.commlite.core.checkers;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public interface CommandSplittableLite {
//    void setCommandSizeMap(Map<Byte, Integer> map);
    List<ByteBuffer> parseQueue(ConcurrentLinkedDeque<Byte> deque);

    ByteBuffer appendSyncToBuffer(ByteBuffer bufferWithOutHead);
//    ByteBuffer getSyncSequence();
}
