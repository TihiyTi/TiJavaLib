package com.ti;

import com.ti.comm.core.command.AbstractCommand;
import com.ti.comm.dev.ComPortWorker;
import com.ti.comm.dev.DeviceInterface;
import com.ti.comm.implem.saw.SawController;
import com.ti.comm.implem.saw.SawProtocol;
import com.ti.comm.implem.saw.SawSynchroByteProtocolChecker;
import com.ti.comm.implem.simple.ByteController;
import com.ti.comm.implem.simple.ByteFlowProtocol;
import org.junit.Assert;
import org.junit.Test;

public class SawProtocolTest {
    @Test(timeout = 30000)
    public void sawProtocolTest(){
        PropertiesService.setGlobalPropertyFileName(SawProtocolTest.class.getSimpleName());
        DeviceInterface device = new ComPortWorker();
        SerialService<AbstractCommand,AbstractCommand> service = new SerialService<>(device);
        SawSynchroByteProtocolChecker checker =  new SawSynchroByteProtocolChecker(24);
        SawProtocol protocol = new SawProtocol(checker, checker);
        SawController controller =  new SawController();

        service.setProtocol(protocol);
        service.addController(controller);

        controller.send();
        controller.send();
        controller.send();
        controller.send();
        while(controller.data.size() < 24);
        Assert.assertArrayEquals(controller.data.stream().mapToInt(i -> i).toArray(), new int[]{1,2,0,1,2,0, 1,2,0,1,2,0, 1,2,0,1,2,0, 1,2,0,1,2,0});
        for (int i = 0; i < 10000; i++) {
            controller.send();
        }
        while(controller.data.size() < 60024);
        ((ComPortWorker)device).closePort();
        Assert.assertEquals(controller.data.size(), 60024);
    }

    @Test(timeout = 1000)
    public void twoServiceByteToSawTest(){
        PropertiesService.setGlobalPropertyFileName(SawProtocolTest.class.getSimpleName());
        DeviceInterface device = new ComPortWorker();
        SerialService<AbstractCommand, AbstractCommand> service = new SerialService<>(device);
        SawSynchroByteProtocolChecker checker =  new SawSynchroByteProtocolChecker(24);
        SawProtocol protocol = new SawProtocol(checker, checker);
        SawController controller =  new SawController();
        service.setProtocol(protocol);
        service.addController(controller);

        ByteController inputController = new ByteController();
        ByteFlowProtocol inputProtocol =  new ByteFlowProtocol();
        inputController.addProtocol(inputProtocol);
        inputProtocol.addController(inputController);
        inputProtocol.setSender(device);

        inputController.send(new byte[]{1, 0,0,0,1,0,0,0,2,0,0,0,0, 0,0,0,1,0,0,0,2,0,0,0,0});
        while(controller.data.size() < 6);
//        System.out.println(controller.data.toString());
        Assert.assertArrayEquals(controller.data.stream().mapToInt(i -> i).toArray(), new int[]{1,2,0,1,2,0});
    }
}
