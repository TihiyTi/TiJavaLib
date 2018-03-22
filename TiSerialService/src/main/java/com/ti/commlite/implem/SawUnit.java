package com.ti.commlite.implem;

import com.ti.AdvanceSignalBox;
import com.ti.FirFilter;
import com.ti.comm.core.checkers.ProtocolCheckable;
import com.ti.comm.core.command.AbstractCommand;
import com.ti.comm.core.protocol.Protocol;
import com.ti.comm.core.protocol.SerialControllable;
import com.ti.commlite.core.checkers.CommandSplittableLite;
import com.ti.commlite.core.command.AbstractSawCommand;
import com.ti.commlite.core.command.SignalParamGetter;
import com.ti.commlite.core.protocol.AbstractProtocolLite;

import java.nio.ByteBuffer;
import java.util.EnumSet;
import java.util.function.Supplier;

public class SawUnit<COMMAND_TYPE extends AbstractCommand>
        extends AbstractProtocolLite<AbstractCommand, AbstractCommand>
        implements SerialControllable<AbstractCommand, AbstractCommand>{
    private Supplier<COMMAND_TYPE> supplier;


    private AdvanceSignalBox box;

    public SawUnit(ProtocolCheckable protocolChecker, CommandSplittableLite commandSplitter, Supplier<COMMAND_TYPE> supplier) {
        super(protocolChecker, commandSplitter);
        this.supplier = supplier;
        controller = this;
    }

    @Override
    public ByteBuffer createResponseToByte(AbstractCommand command) {
        return command.createByteBuffer();
    }

    @Override
    public AbstractCommand createByteToRequest(ByteBuffer buffer) {
        return supplier.get().parseByteBuffer(buffer);
    }

    @Override
    public void serviceRequest(AbstractCommand abstractCommand) {
        box.listOfType.forEach(x-> {
            int data = ((AbstractSawCommand)abstractCommand).getData((Enum) x);
            // TODO: 18.03.2018 вынести фильтрацию в AdvanceSignalBox, добавить еще один list помимо listOfType
            if(((SignalParamGetter)x).isExternal()){
                box.addToQueue((Enum)x, data);
            }
        });
    }

    @Override
    public void toServiceResponse(AbstractCommand abstractCommand) {
        sendResponse(abstractCommand);
    }

    @Override
    public void addProtocol(Protocol<AbstractCommand, AbstractCommand> protocol) {
        System.out.println("Dont apply to UNIT, it's already Controller and Protocol");
    }

    public void setBox(AdvanceSignalBox box) {
        this.box = box;
    }

}
