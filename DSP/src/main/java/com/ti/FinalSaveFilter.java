package com.ti;

import java.util.ArrayList;
import java.util.List;

public class FinalSaveFilter<IN extends Number> extends SignalService<IN, Number>{
    private List<IN> list = new ArrayList<>();

    @Override
    public void putElement(IN element) {
        list.add(element);
    }

    public List<IN> getResult(){
        return list;
    }
}
