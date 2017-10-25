package com.ti;

import com.ti.checkers.SawSynchroByteProtocolChecker;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;


public class SawSynchroByteProtocolCheckerTest {
    private int frameSize = 5;
    private int dataSize = 4;
    @Test
    public void sawSyncroByteProtocolCheckerTest() throws Exception {
        SawSynchroByteProtocolChecker checker = new SawSynchroByteProtocolChecker(dataSize);
        ConcurrentLinkedDeque<Byte> deque = getDeque(true, (byte)1);
        Assert.assertTrue(checker.checkProtocol(deque));
        System.out.println(deque.toString());

        deque = getDeque(true, (byte)127);
        Assert.assertTrue(checker.checkProtocol(deque));
        System.out.println(deque.toString());

    }

    private ConcurrentLinkedDeque<Byte> getDeque(boolean isGood, byte sinc){
        ConcurrentLinkedDeque<Byte> deque = new ConcurrentLinkedDeque<>();
        List<Byte> list = new ArrayList<>();
        byte[] ar = getArray(isGood, sinc);
        for (byte element : ar) {
            list.add(element);
        }
        System.out.println("List for test"+list.toString());
        deque.addAll(list);
        return deque;
    }

    private byte[] getArray(boolean isGood, byte sinc){
        byte[] array = new byte[30];
        for (int i = 0; i < 30; i++) {
            array[i] =( (byte) Math.round(Math.random()*1000));
        }
        if(isGood){
            doGood(array, sinc);
        }
        return array;
    }

    private void doGood(byte[] list, byte sinc){
        int i = (int)Math.round(Math.random()*100) % frameSize;
        System.out.println("First index = "+ i);
//        byte b = (byte)(Math.round(Math.random()*100) % 256);
        while ( i < list.length){
            list[i] = sinc;
            i = i + frameSize;
            sinc++;
        }
    }

}