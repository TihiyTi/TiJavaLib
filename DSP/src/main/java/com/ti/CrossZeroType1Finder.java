package com.ti;

import java.util.ArrayList;
import java.util.List;

public class CrossZeroType1Finder extends SignalService {

    private List<Number> buffer = new ArrayList<>();
    private int count = 0;
    private int delayInSample;
    private Direction direction;

    public CrossZeroType1Finder(int num, Direction direction){
        delayInSample = num;
        this.direction = direction;
        for (int i = 0; i < delayInSample; i++) {
            buffer.add(0);
        }
    }

    @Override
    public void putElement(Number element) {
        if(check(element)){
//            System.out.println("Find Zero " + direction.name()+" "+(count - delayInSample));
            nextConsumer.putElement((count - delayInSample));
        }
    }

    private Boolean check(Number element) {
        Double el = element.doubleValue();
        buffer.add(el);
        count++;
        buffer.remove(0);
        boolean res;
        if(direction.equals(Direction.UP)){
            res = buffer.get(0).doubleValue() < 0 && buffer.stream().skip(1).allMatch(e -> e.doubleValue() >= 0);
        }else {
            res = buffer.get(0).doubleValue() > 0 && buffer.stream().skip(1).allMatch(e -> e.doubleValue() <= 0);
        }
        return res;
    }

    enum Direction{
        UP,DOWN
    }
}
