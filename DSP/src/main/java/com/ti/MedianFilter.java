package com.ti;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MedianFilter extends SignalService {
    private int range;
    private List<Number> buffer = new ArrayList<>();

    private int threshold = 0;
    private double avarage = 0;

    private boolean isHeatNeed = false;

    public MedianFilter(int range) {
        this.range = range;
        for (int i = 0; i < range; i++) {
            buffer.add(0);
        }
    }

    public MedianFilter(int range, int threshold) {
        this.range = range;
        this.threshold = threshold;
        for (int i = 0; i < range; i++) {
            buffer.add(0);
        }
    }

    @Override
    public void putElement(Number element) {
        nextConsumer.putElement(apply(element.intValue()));
    }

    /**
     * Returns ...
     * <P>
     * @param
     * @param
     * @return
     */
    public Number apply(Number element){

        if(isHeatNeed){heat(element); isHeatNeed = false;}

        if(threshold != 0){
            avarage = buffer.stream().mapToInt(Number::intValue).average().orElse(0);
            if((Math.abs(element.doubleValue()- avarage) - threshold) > 0){
                buffer.add((int)avarage);
            }else {
                buffer.add(element);
            }
        }else{
            buffer.add(element);
        }

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
