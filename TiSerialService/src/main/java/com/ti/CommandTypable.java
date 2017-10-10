package com.ti;

import com.ti.command.AbstractCommand;

import java.util.Map;

public interface CommandTypable {
    // TODO: 13.03.2017 make abstract class and move same method to abstract class
    enum Direction{
        RESPONSE,REQUEST;
    }

    boolean check(byte head);
    byte getHeadByte();
    int getCommandSize();
    Map<Byte, Integer> getCommandSizeMap();
    AbstractCommand getCommand();
    boolean equalCommand(AbstractCommand command);
}
