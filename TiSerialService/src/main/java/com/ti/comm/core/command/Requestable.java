package com.ti.comm.core.command;

import java.nio.ByteBuffer;

public interface Requestable {
    AbstractCommand parseByteBuffer(ByteBuffer buffer);
}
