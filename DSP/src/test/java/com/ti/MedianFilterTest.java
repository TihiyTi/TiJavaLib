package com.ti;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class MedianFilterTest {
    @Test
    public void testMedianFilter(){
        List<Integer> intList = Arrays.asList(1, 2, 3, 4, 20, 5, 6, 7, 8);
        MedianFilter median = new MedianFilter(5);

        intList.stream().map(median::apply).forEach(System.out::println);
    }
}