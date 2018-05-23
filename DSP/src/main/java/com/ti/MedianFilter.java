package com.ti;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MedianFilter extends SignalService {
    private int range;
    private List<Number> buffer = new ArrayList<>();

    private boolean isHeatNeed = false;

    public MedianFilter(int range) {
        this.range = range;
        for (int i = 0; i < range; i++) {
            buffer.add(0);
        }
    }

    @Override
    public void putElement(Number element) {
        nextConsumer.putElement(apply(element.intValue()));
    }

    public Number apply(Number element){

        if(isHeatNeed){heat(element); isHeatNeed = false;}
        buffer.add(element);
        buffer.remove(0);
        List<Number> bufList = buffer.stream().sorted().collect(Collectors.toList());
        return bufList.get(range/2);
    }

    public void enableHeatNeed(){
        isHeatNeed = true;
    }
    private void heat(Number el ){
        buffer.clear();
        for (int i = 0; i < range; i++) {
            buffer.add(el);
        }
    }

}
