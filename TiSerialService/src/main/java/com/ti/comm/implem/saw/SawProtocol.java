package com.ti.comm.implem.saw;

import com.ti.comm.core.checkers.CommandSplittable;
import com.ti.comm.core.checkers.ProtocolCheckable;
import com.ti.comm.core.command.AbstractCommand;
import com.ti.comm.core.protocol.AbstractProtocol;
import com.ti.comm.implem.command.SawCommand;

import java.nio.ByteBuffer;

public class SawProtocol extends AbstractProtocol<AbstractCommand, AbstractCommand> {

    public SawProtocol(ProtocolCheckable protocolChecker, CommandSplittable commandSplitter){
        super(protocolChecker, commandSplitter);
    }

    @Override
    public ByteBuffer createResponseToByte(AbstractCommand command) {
        return command.createByteBuffer();
    }

    @Override
    public AbstractCommand createByteToRequest(ByteBuffer buffer) {
        return new SawCommand().parseByteBuffer(buffer);
    }
}
