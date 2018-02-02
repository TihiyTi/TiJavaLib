package com.ti.commlite.implem.command;

import com.ti.comm.core.command.AbstractCommand;
import com.ti.commlite.core.command.AbstractSawCommand;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MechaSawCommand extends AbstractSawCommand<MechaSignalType> {
    private int reo_1 = 0;
    private int ecg_1 = 0;
    private int currentControl_1 = 0;
    private int reo_2 = 0;
    private int ecg_2 = 0;
    private int currentControl_2 = 0;

    public MechaSawCommand(){
    }

    public MechaSawCommand(int reo_1, int ecg_1, int currentControl_1, int reo_2, int ecg_2, int currentControl_2) {
        this.reo_1 = reo_1;
        this.ecg_1 = ecg_1;
        this.currentControl_1 = currentControl_1;
        this.reo_2 = reo_2;
        this.ecg_2 = ecg_2;
        this.currentControl_2 = currentControl_2;
    }

    public AbstractCommand parseByteBuffer(ByteBuffer buffer) {
        buffer.rewind();
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        reo_1 = buffer.getInt();
        ecg_1 = buffer.getInt();
        currentControl_1 = buffer.getInt();
        reo_2 = buffer.getInt();
        ecg_2 = buffer.getInt();
        currentControl_2 = buffer.getInt();
        return this;
    }

    @Override
    public ByteBuffer createByteBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(24);
        buffer.putInt(reo_1);
        buffer.putInt(ecg_1);
        buffer.putInt(currentControl_1);
        buffer.putInt(reo_2);
        buffer.putInt(ecg_2);
        buffer.putInt(currentControl_2);
        return buffer;
    }

    @Override
    public int getData(MechaSignalType type){
        if(type.equals(MechaSignalType.ECG1)){
            return ecg_1;
        }else if (type.equals(MechaSignalType.ECG2)){
            return ecg_2;
        }else if (type.equals(MechaSignalType.CUR1)){
            return currentControl_1;
        }else if (type.equals(MechaSignalType.CUR2)){
            return currentControl_2;
        }else if (type.equals(MechaSignalType.REO1)){
            return reo_1;
        }else if (type.equals(MechaSignalType.REO2)){
            return reo_2;
        }else {
            return 0;
        }
    }

}
