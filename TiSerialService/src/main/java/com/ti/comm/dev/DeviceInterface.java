package com.ti.comm.dev;

import com.ti.comm.core.protocol.AbstractProtocol;

import java.nio.ByteBuffer;

public interface DeviceInterface {
    void sendDataArray(ByteBuffer sendBuffer);
    <RESPONSE, REQUEST> void setProtocol(AbstractProtocol<RESPONSE, REQUEST> protocol);
}
