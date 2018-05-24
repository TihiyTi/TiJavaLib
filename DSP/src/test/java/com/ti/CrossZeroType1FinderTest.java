package com.ti;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class CrossZeroType1FinderTest {

    @Test
    public void crossZeroType1Test(){
        List<Integer> intList = Arrays.asList(-1, 0, 7, 4, 7, 2, 7, 4, -2, 3, 1, -3, 4, -3, 4, 1, -3, 5, -1, 6);
        FinalSaveFilter fin = new FinalSaveFilter();

        SignalPipe pipe = new SignalPipe();
        SignalService crossZeroFilter = new CrossZeroType1Finder(3, CrossZeroType1Finder.Direction.UP);
        pipe.addSignalServices(crossZeroFilter);

        pipe.addConsumer(fin);

        intList.forEach(pipe::putElement);

        System.out.println(fin.getResult().toString());
        System.out.println("complete");

//        Assert.assertArrayEquals(
//                fin.getResult().stream().mapToDouble(Number::doubleValue).toArray(),
//                new double[]{2.0, 6.0, 12.0, 18.0, 24.0, 30.0, 36.0, 42.0}, 0.001);
//
    }
}