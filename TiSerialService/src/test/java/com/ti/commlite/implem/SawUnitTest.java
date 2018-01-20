package com.ti.commlite.implem;

import com.ti.AdvanceSignalBox;
import com.ti.FinalSaveFilter;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ConcurrentLinkedDeque;

import static org.junit.Assert.*;

public class SawUnitTest {
    @Test
    public void SawUnitExample(){
        SawProtocolParser parser = new SawProtocolParser(8);
        SawUnit<SawCommandTest> unit = new SawUnit<>(parser,parser, SawCommandTest::new);
        AdvanceSignalBox<Integer, SawTypeTest> box = new AdvanceSignalBox<>(SawTypeTest.class);
        unit.setBox(box);

        ConcurrentLinkedDeque<Byte> deque = new ConcurrentLinkedDeque<>();
        deque.addAll(Arrays.asList(
                (byte)0, (byte)0,
                (byte)0, (byte)0, (byte)0, (byte)0, (byte)3, (byte)0,(byte)0, (byte)0,(byte)4,
                (byte)1, (byte)0, (byte)0, (byte)0, (byte)3, (byte)0,(byte)0, (byte)0,(byte)5,
                (byte)2, (byte)0, (byte)0, (byte)0, (byte)3, (byte)0,(byte)0, (byte)0,(byte)4,
                (byte)3, (byte)0, (byte)0, (byte)0, (byte)3, (byte)0,(byte)0, (byte)0,(byte)4,
                (byte)4, (byte)0, (byte)0, (byte)0, (byte)3, (byte)0,(byte)0, (byte)0,(byte)4,
                (byte)5, (byte)0, (byte)0, (byte)0, (byte)3, (byte)0,(byte)0
                ));
        FinalSaveFilter<Integer> data1Saver = new FinalSaveFilter<>();
        FinalSaveFilter<Integer> data2Saver = new FinalSaveFilter<>();
        box.addTypedConsumer(data1Saver, SawTypeTest.DATA1);
        box.addTypedConsumer(data2Saver, SawTypeTest.DATA2);

        unit.parse(deque);
        for (int i = 0; i < 1000000; i++) {}
        Assert.assertArrayEquals(
                data1Saver.getResult().stream().mapToDouble(Number::doubleValue).toArray(),
                new double[]{3,3,3,3,3}, 0.001);
        Assert.assertArrayEquals(
                data2Saver.getResult().stream().mapToDouble(Number::doubleValue).toArray(),
                new double[]{4,5,4,4,4}, 0.001);
    }
}