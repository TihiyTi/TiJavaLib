package com.ti;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FinalSaveFilter<IN extends Number> extends SignalService<IN, Number>{
    private ConcurrentLinkedQueue<IN> list = new ConcurrentLinkedQueue<>();

    @Override
    public void putElement(IN element) {
        list.add(element);
//        System.out.println("Size  " + list.size());
    }

    public ConcurrentLinkedQueue<IN> getResult(){
        return list;
    }
}
