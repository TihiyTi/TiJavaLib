package com.ti.commlite.implem;

import com.ti.comm.core.checkers.ProtocolCheckable;
import com.ti.comm.core.command.AbstractCommand;
import com.ti.comm.core.protocol.Protocol;
import com.ti.comm.core.protocol.SerialControllable;
import com.ti.commlite.core.checkers.CommandSplittableLite;
import com.ti.commlite.core.protocol.AbstractProtocolLite;

import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ByteUnit
        extends AbstractProtocolLite<Byte,Byte>
        implements SerialControllable<Byte,Byte>{

    public ByteUnit() {
        super(null, null);
    }

    @Override
    public void parse(ConcurrentLinkedDeque<Byte> deque){
        System.out.println("Unsupported");
    }

    @Override
    public ByteBuffer createResponseToByte(Byte aByte) {
        return null;
    }

    @Override
    public Byte createByteToRequest(ByteBuffer buffer) {
        return null;
    }

    @Override
    public void serviceRequest(Byte aByte) {

    }

    @Override
    public void toServiceResponse(Byte aByte) {

    }

    @Override
    public void addProtocol(Protocol<Byte, Byte> protocol) {

    }
}
