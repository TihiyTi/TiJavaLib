package com.ti;

import com.ti.protocol.ComPortWorker;
import com.ti.protocol.AbstractProtocol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SerialService<RESPONSE, REQUEST> implements SerialSettable{
    private ComPortWorker comPort = new ComPortWorker();
    private AbstractProtocol<RESPONSE, REQUEST> protocol;
    private List<AbstractProtocol<RESPONSE, REQUEST>> listChildrenProtocol = new ArrayList<>();

    public void setProtocol(AbstractProtocol<RESPONSE, REQUEST> mainProtocol){
        protocol = mainProtocol;

        protocol.setSender(comPort);
        comPort.setProtocol(protocol);
    }

    @SafeVarargs
    public final void addChildrenProtocol(AbstractProtocol<RESPONSE, REQUEST>... childrenProtocols){
        //todo добавить логирование, если "детские" протоколы добавлены первыми
        listChildrenProtocol.addAll(Arrays.asList(childrenProtocols));
        listChildrenProtocol.forEach(x->protocol.addProtocol(x));
    }
    public void addController(SerialControllable<RESPONSE,REQUEST> controller){
        //todo добавить логирование, если контроллер добавлен, до родительского протокола
        protocol.addController(controller);
        controller.addProtocol(protocol);
        listChildrenProtocol.forEach(controller::addProtocol);
    }

    // TODO: 13.03.2017 метод добавлен для AnalogTester, возможно стоит переписать архитектурно более правильно
    @Override
    public void reopenPort(String port, int rate){
        comPort.reopenPort(port);
    }

}
