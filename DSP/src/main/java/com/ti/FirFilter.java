package com.ti;

import com.google.common.primitives.Doubles;

import java.util.ArrayList;
import java.util.List;

public class FirFilter<IN extends Number, OUT extends Number> extends SignalService<IN, OUT>{
    private double[] kernel;
    private int bufferSize;
    private List<Number> buffer = new ArrayList<>();


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

    private OUT apply(IN element) {
        Double el = element.doubleValue();
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
}
