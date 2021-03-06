package com.ti.commlite.core.protocol;

import com.ti.comm.core.checkers.CommandSplittable;
import com.ti.comm.core.checkers.ProtocolCheckable;
import com.ti.comm.core.protocol.Protocol;
import com.ti.comm.core.protocol.SerialControllable;
import com.ti.comm.dev.DeviceInterface;
import com.ti.commlite.core.checkers.CommandSplittableLite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

public abstract class AbstractProtocolLite<RESPONSE, REQUEST> implements Protocol<RESPONSE, REQUEST> {
    private static final Logger LOG = LogManager.getLogger("TiSerialServiceLogger");

    private ProtocolCheckable protocolChecker;
    private CommandSplittableLite commandSplitter;

    protected SerialControllable<RESPONSE, REQUEST> controller;
    private DeviceInterface device;

    int protocolToControllerRequestCounter = 0;

    public AbstractProtocolLite(ProtocolCheckable protocolChecker, CommandSplittableLite commandSplitter) {
        this.protocolChecker = protocolChecker;
        this.commandSplitter = commandSplitter;
    }

    public void addController(SerialControllable<RESPONSE, REQUEST> controller){
        this.controller = controller;
    }

    @Override
    public void parse(ConcurrentLinkedDeque<Byte> deque){
        if(protocolChecker.checkProtocol(deque)){
            List<ByteBuffer> frameQueue = commandSplitter.parseQueue(deque);
            if(!frameQueue.isEmpty()){
                List<REQUEST> listOfRequest = frameQueue.stream().map(this::createByteToRequest).collect(Collectors.toList());
                listOfRequest.forEach(x->controller.serviceRequest(x));


//-----------------TRACE block  -----------------
                protocolToControllerRequestCounter++;
                if(protocolToControllerRequestCounter % 10 == 0){
                    LOG.trace("protocolToControllerRequestCounter = "+protocolToControllerRequestCounter);
                }
//-----------------TRACE block  -----------------
            }
        }
    }

    @Override
    public void sendResponse(RESPONSE response){
        ByteBuffer bufferWithOutHead = createResponseToByte(response);
        bufferWithOutHead.rewind();
        ByteBuffer buffer = commandSplitter.appendSyncToBuffer(bufferWithOutHead);
        device.sendDataArray(buffer);
    }

    // TODO: 15.04.2018 вынести в Protocol
    public void sendWithOutProtocol(ByteBuffer buffer){
        device.sendDataArray(buffer);
    }

    @Override
    public void setDevice(DeviceInterface device){this.device = device;}



}
