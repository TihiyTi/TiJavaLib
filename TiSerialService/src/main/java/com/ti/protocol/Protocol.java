package com.ti.protocol;

import java.nio.ByteBuffer;

public interface Protocol<RESPONSE, REQUEST> {
    //Call from Controller to send RESPONSE from AbstractProtocol to Sender
    void sendResponse(RESPONSE response);

    //
    ByteBuffer createResponseToByte(RESPONSE response);
    REQUEST createByteToRequest(ByteBuffer buffer);
}
