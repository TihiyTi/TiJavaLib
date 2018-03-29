package com.ti.commlite.implem.command;

import com.ti.commlite.core.command.SignalParamGetter;

public enum MechaSignalType implements SignalParamGetter {
    REO1 (true),
    CUR1(true),
    ECG1(true),
    REO2(true),
    ECG2(true),
    CUR2(true),
    ECG2FILTR(false),
    REO2FILTR(false),
    BREATHMIN(false),
    BREATHMAX(false);


    boolean external;

    MechaSignalType(boolean b) {
        external = b;
    }

    @Override
    public boolean isExternal(){
        return external;
    }
}
