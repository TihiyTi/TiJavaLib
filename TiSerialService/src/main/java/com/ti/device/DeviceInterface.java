package com.ti.device;

import com.ti.protocol.AbstractProtocol;

import java.nio.ByteBuffer;

public interface DeviceInterface {
    void sendDataArray(ByteBuffer sendBuffer);
    <RESPONSE, REQUEST> void setProtocol(AbstractProtocol<RESPONSE, REQUEST> protocol);
}
