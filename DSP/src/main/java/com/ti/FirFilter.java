package com.ti;

import com.google.common.primitives.Doubles;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FirFilter<IN extends Number, OUT extends Number> extends SignalService<IN, OUT>{
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
    public void putElement(IN element) {
        Number el = element.doubleValue();
        nextConsumer.putElement(apply(element));
    }

    public OUT apply(IN element) {
        Double el = element.doubleValue();
        if(isHeatNeed){heat(el); isHeatNeed = false;}

        if(kernel == null){
            return (OUT)el;
        }else{
            buffer.add(el);
            buffer.remove(0);
            double[] bufferArray = buffer.stream().mapToDouble(Number::doubleValue).toArray();
            Double result = 0.;
            for (int i = 0; i < bufferSize; i++) {
                result = (result + bufferArray[i]*kernel[bufferSize-1-i]);
            }
            return (OUT)result;
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
