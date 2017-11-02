package com.ti.comm.implem.command;

import com.ti.comm.core.command.AbstractCommand;

import java.nio.ByteBuffer;

public class SawCommand extends AbstractCommand {
    private int reo_1 = 0;
    private int ecg_1 = 0;
    private int currentControl_1 = 0;
    private int reo_2 = 0;
    private int ecg_2 = 0;
    private int currentControl_2 = 0;

    // TODO: 23.10.2017 "коммандный байт" должен добавляться в abstractCommand  если
    // type не равен null
    public SawCommand(){
    }

    public SawCommand(int reo_1, int ecg_1, int currentControl_1, int reo_2, int ecg_2, int currentControl_2) {
        this.reo_1 = reo_1;
        this.ecg_1 = ecg_1;
        this.currentControl_1 = currentControl_1;
        this.reo_2 = reo_2;
        this.ecg_2 = ecg_2;
        this.currentControl_2 = currentControl_2;
    }
//    public SawCommand(COMMAND_TYPE commandType) {
//        type = commandType;
//    }
//    public SawCommand(COMMAND_TYPE commandType, int reo, int mio) {
//        type = commandType;
//        this.reo = reo;
//        this.mio = mio;
//    }

    @Override
    public AbstractCommand parseByteBuffer(ByteBuffer buffer) {
        buffer.rewind();
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

    public int getReo_1(){return reo_1;}
    public int getReo_2(){return reo_2;}
    public int getEcg_1(){return ecg_1;}
    public int getEcg_2(){return ecg_2;}
    public int getCurrentControl_1(){return currentControl_1;}
    public int getCurrentControl_2(){return currentControl_2;}

    @Override
    public void debugPrint() {
        System.out.println("Not implemented for SawCommand");
    }
}
