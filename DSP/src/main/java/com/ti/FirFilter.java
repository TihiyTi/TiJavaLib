package com.ti;

import com.google.common.primitives.Doubles;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FirFilter extends SignalService{
    private double[] kernel;
    private int bufferSize;
    private List<Number> buffer = new ArrayList<>();

    private boolean isHeatNeed = false;

    public FirFilter(double ... kernel) {
        this.kernel = kernel;
        bufferSize = kernel.length;
        List<Double> kernelList = Doubles.asList(kernel);
        kernelList.forEach(x-> buffer.add(0));
    }

    @Override
    public void putElement(Number element) {
        Number el = element.doubleValue();
        nextConsumer.putElement(apply(element));
    }

    public Number apply(Number element) {
        Double el = element.doubleValue();
        if(isHeatNeed){heat(el); isHeatNeed = false;}

        if(kernel == null){
            return el;
        }else{
            buffer.add(el);
            buffer.remove(0);
            double[] bufferArray = buffer.stream().mapToDouble(Number::doubleValue).toArray();
            Double result = 0.;
            for (int i = 0; i < bufferSize; i++) {
                result = (result + bufferArray[i]*kernel[bufferSize-1-i]);
            }
            return result;
        }
    }

    public void enableHeatNeed(){
        isHeatNeed = true;
    }
    private void heat(Number el ){
        buffer.clear();
        for (double ignored : kernel) {
            buffer.add(el);
        }
    }
}
