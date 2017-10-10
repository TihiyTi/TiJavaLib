package com.ti.protocol;

import com.ti.PropertiesService;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ComPortWorker {
    private static final Logger LOG = LogManager.getLogger(ComPortWorker.class.getName());

    private static String PORT_NAME = "portName";
    private static String BAUD_RATE = "baudRate";

    private SerialPort port;
    private int count = 0;

    private AbstractProtocol protocol;

    public ComPortWorker() {
        String portName = PropertiesService.getGlobalProperty(PORT_NAME);
        if(portName==null){
            portName = "COM7";
            LOG.info("COM Port name not found in property file. Default name "+ portName);
            PropertiesService.setGlobalProperty(PORT_NAME,portName);
        }
        String baudRateString = PropertiesService.getGlobalProperty(BAUD_RATE);
        Integer baudRate = SerialPort.BAUDRATE_115200;
        if(baudRateString==null){
            LOG.info("COM Port baud rate not found in property file. Default rate "+ baudRate);
            PropertiesService.setGlobalProperty(BAUD_RATE,String.valueOf(baudRate));
        }else{
            baudRate = Integer.valueOf(baudRateString);
        }
        openPort(portName, baudRate);
    }

    public void openPort(String portName, int speed) {
        port = new SerialPort(portName);
        try {
            port.openPort();
            LOG.info("Port "+portName+" opened with rate "+ speed);
            port.setParams(speed, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            port.setEventsMask(SerialPort.MASK_RXCHAR);
            port.addEventListener(new SimpleProtocolListener());
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
//        finally {
//            try {
//                System.out.println("Close port");
//                port.closePort();
//            } catch (SerialPortException e) {
//                e.printStackTrace();
//            }
//        }
    }
    public void reopenPort(String portName){
        try {
            System.out.println("Close port");
            port.closePort();
            LOG.info("Port "+port.getPortName()+ " closed");
            openPort(portName, SerialPort.BAUDRATE_115200);
            LOG.info("Port "+portName+" reopened with FIX rate "+ SerialPort.BAUDRATE_115200);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    public void sendData(Number data){
        try {
            port.writeByte(data.byteValue());
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    public void sendDataArray(ByteBuffer buffer){
        if(port.isOpened()){
            try {
                port.writeBytes(buffer.array());
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        }else{
            // TODO: 13.03.2017 добавить логирования события
            System.out.println("Port NO open. Data don't send.");
        }
    }

    public void setProtocol(AbstractProtocol protocol) {
        this.protocol = protocol;
        protocol.setSender(this);
    }

    class SimpleProtocolListener implements SerialPortEventListener{
        private boolean isProtocolCorrect = false;
        private int skipByteCount = 0;
        private ConcurrentLinkedDeque<Byte> deque = new ConcurrentLinkedDeque<Byte>();
        @Override
        public void serialEvent(SerialPortEvent serialPortEvent) {
            if(serialPortEvent.isRXCHAR() && serialPortEvent.getEventValue()>0){
                byte[] buf = new byte[0];
                try {
                    buf = port.readBytes();
                } catch (SerialPortException e) {
                    e.printStackTrace();
                }
                for (byte element: buf){
                    deque.add(element);
                }
                if(protocol.checkProtocol(deque)){
                    protocol.parseQueue(deque);
                }
            }
        }
    }
    private String loadParam(String param){
        PropertiesService service = new PropertiesService();
        service.setName("SerialService");
        return service.getProperty(param);
    }
    private void saveParam(String param, String value){
        PropertiesService service =  new PropertiesService();
        service.setName("SerialService");
        service.saveProperty(param, value);
    }
}
