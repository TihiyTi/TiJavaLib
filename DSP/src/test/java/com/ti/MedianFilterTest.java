package com.ti;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class MedianFilterTest {
    @Test
    public void testMedianFilter(){
        int[] inArray = new int[]{1, 2, 3, 4, 20, 5, 6, 7, 8};
        MedianFilter median = new MedianFilter(5);

        int[] outArray = Arrays.stream(inArray).map(x-> median.apply(x).intValue()).toArray();
        Assert.assertArrayEquals(new int[]{0, 0, 1, 2, 3, 4, 5, 6, 7}, outArray);
    }

    @Test
    public void testMedianFilterWithHeat(){
        int[] inArray = new int[]{1, 2, 3, 4, 20, 5, 6, 7, 8};
        MedianFilter median = new MedianFilter(5);
        median.enableHeatNeed();
        int[] outArray = Arrays.stream(inArray).map(x-> median.apply(x).intValue()).toArray();
        Assert.assertArrayEquals(new int[]{1, 1, 1, 2, 3, 4, 5, 6, 7}, outArray);
    }

    @Test
    public void testMedianFilterWithTreshold(){
        int[] inArray = new int[]{1, 2, 100, 3, 4, 20, 5, 100, 6, 7, 8};
        MedianFilter median = new MedianFilter(5, 50);
        median.enableHeatNeed();
        double[] outArray = Arrays.stream(inArray).mapToDouble(x-> median.apply(x).doubleValue()).toArray();
        Assert.assertArrayEquals(new double[]{1.0, 1.0, 1.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 6.0, 6.}, outArray, 0.01);
    }


}