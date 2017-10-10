package com.ti;

import com.ti.command.AbstractCommand;
import com.ti.protocol.Protocol;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSerialController implements SerialControllable<AbstractCommand,AbstractCommand>{
    List<Protocol<AbstractCommand,AbstractCommand>> list = new ArrayList<>();

    @Override
    abstract public void serviceRequest(AbstractCommand command);

    @Override
    public void toServiceResponse(AbstractCommand command) {
        list.forEach(x->x.sendResponse(command));
    }

    @Override
    public void addProtocol(Protocol<AbstractCommand, AbstractCommand> protocol) {
        list.add(protocol);
    }
}
