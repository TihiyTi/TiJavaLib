package com.ti.commlite.core.command;

import com.ti.comm.core.command.AbstractCommand;

public abstract class AbstractSawCommand<T  extends Enum<T>> extends AbstractCommand {
    public abstract int getData(T type);
}
