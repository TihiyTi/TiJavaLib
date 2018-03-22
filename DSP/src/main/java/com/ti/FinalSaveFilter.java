package com.ti;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FinalSaveFilter<IN extends Number> extends SignalService<IN, Number>{

    private static final Logger LOG = LogManager.getLogger("DataLogger");

    private ConcurrentLinkedQueue<IN> list = new ConcurrentLinkedQueue<>();

//  Debug functional
    private String name = "";
    private boolean print = false;

    public FinalSaveFilter(String name) {
        this.name = name;
        print = true;
    }
//  Debug functional

    public FinalSaveFilter(){}

    @Override
    public void putElement(IN element) {
        list.add(element);
        if(print){
            LOG.trace(name + "   "+ element.toString());
            System.out.println(name +"  "+ list.size()+ "  " + element.toString());
        }
    }

    public ConcurrentLinkedQueue<IN> getResult(){
        return list;
    }
}
