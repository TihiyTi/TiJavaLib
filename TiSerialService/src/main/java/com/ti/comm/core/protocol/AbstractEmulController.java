package com.ti.comm.core.protocol;

import com.ti.comm.core.command.AbstractCommand;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEmulController implements SerialControllable<AbstractCommand,AbstractCommand>, Runnable{
    List<Protocol<AbstractCommand,AbstractCommand>> list = new ArrayList<>();

    @Override
    public void serviceRequest(AbstractCommand command) {

    }

    @Override
    public void toServiceResponse(AbstractCommand command) {
        list.forEach(x->x.sendResponse(command));
    }

    @Override
    public void addProtocol(Protocol<AbstractCommand, AbstractCommand> protocol) {
        list.add(protocol);
    }

    @Override
    public void run() {
        doIt();
    }

    protected abstract void doIt();
}
