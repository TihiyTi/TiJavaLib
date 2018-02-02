package com.ti.comm.dev;

import com.ti.comm.core.protocol.Protocol;

import java.nio.ByteBuffer;

public interface DeviceInterface {
    void sendDataArray(ByteBuffer sendBuffer);
    <RESPONSE, REQUEST> void setProtocol(Protocol<RESPONSE, REQUEST> protocol);
}
