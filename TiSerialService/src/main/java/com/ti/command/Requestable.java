package com.ti.command;

import java.nio.ByteBuffer;

public interface Requestable {
    AbstractCommand parseByteBuffer(ByteBuffer buffer);
}
