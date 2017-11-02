package com.ti.comm.implem.saw;

import com.ti.comm.core.protocol.AbstractSerialController;
import com.ti.comm.core.command.AbstractCommand;
import com.ti.comm.implem.command.SawCommand;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class SawController extends AbstractSerialController<AbstractCommand, AbstractCommand> implements Runnable{
    public BlockingQueue<Integer> data = new LinkedBlockingQueue<>();
    @Override
    public void serviceRequest(AbstractCommand command) {
        SawCommand sawCommand = (SawCommand) command;
        data.add(sawCommand.getReo_1());
        data.add(sawCommand.getEcg_1());
        data.add(sawCommand.getCurrentControl_1());
        data.add(sawCommand.getReo_2());
        data.add(sawCommand.getEcg_2());
        data.add(sawCommand.getCurrentControl_2());
    }

    public void send(int[] data){
        toServiceResponse(new SawCommand(data[0], data[1], data[2], data[3], data[4], data[5]));
    }
    public void send(){
        toServiceResponse(new SawCommand(1,2, 0, 1, 2, 0));
    }

    @Override
    public void run() {
        System.out.println("Send");
        send();
    }
    public void runSendService(){
        Executors.newSingleThreadScheduledExecutor().
                scheduleWithFixedDelay(this, 0L, 2000L, TimeUnit.MILLISECONDS);
    }
}
