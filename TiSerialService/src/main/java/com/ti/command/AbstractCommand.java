package com.ti.command;

public abstract class AbstractCommand<COMMAND_TYPE> implements Requestable, Responsable{

    protected COMMAND_TYPE type;

    public COMMAND_TYPE is(){
        return type;
    }

    public abstract void debugPrint();
}
