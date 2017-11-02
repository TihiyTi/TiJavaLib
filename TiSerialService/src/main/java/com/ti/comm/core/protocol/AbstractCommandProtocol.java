package com.ti.comm.core.protocol;

import com.ti.comm.core.checkers.CommandSplittable;
import com.ti.comm.core.checkers.ProtocolCheckable;
import com.ti.comm.core.command.AbstractCommand;
import com.ti.comm.core.command.CommandTypable;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractCommandProtocol<COMMAND_TYPE extends CommandTypable> extends AbstractProtocol<AbstractCommand, AbstractCommand> {
    private Set<COMMAND_TYPE> commandable = new HashSet<>();

    public AbstractCommandProtocol(ProtocolCheckable protocolChecker, CommandSplittable commandSplitter) {
        super(protocolChecker, commandSplitter);
    }

    protected AbstractCommandProtocol() {
    }

    protected void fillSetOfCommandType(COMMAND_TYPE[] arrayOfCommanType){
        commandable.addAll(Arrays.asList(arrayOfCommanType));
    }

//    protected abstract void supportCommand(AbstractCommand com.ti.comm.core.command);

    @Override
    public ByteBuffer createResponseToByte(AbstractCommand command) {
        return command.createByteBuffer();
    }

    @Override
    public AbstractCommand createByteToRequest(ByteBuffer buffer) {
//        System.out.println("Prepare to create request" + this.toString());
        byte head = buffer.get();
        // TODO: 17.03.2017 Можно сделать разделение по командам IN и OUT 
        List<COMMAND_TYPE> list = commandable.stream().filter(x->x.check(head)).collect(Collectors.toList());
        return list.stream().findFirst().get().getCommand().parseByteBuffer(buffer);
    }
}
