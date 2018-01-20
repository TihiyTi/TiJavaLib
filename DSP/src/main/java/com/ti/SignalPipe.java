package com.ti;

import java.util.*;

public class SignalPipe<IN extends Number, OUT extends Number> implements PipeInterface<IN, OUT> {
    private List<SignalConsumer<Number>> serviceList = new ArrayList<>();

    private FinalConsumer finalConsumer = new FinalConsumer(); ///
    private Set<SignalConsumer<OUT>> consumerSet = new HashSet<>();
    private MultySignalProvider<OUT, ? extends Enum> boxConsumer;
    private Enum boxConsumerType;

    @Override
    public void putElement(IN element) {
        serviceList.get(0).putElement(element);
    }

    /**
     * Add consumer for pipe
     * @param consumer - comsumer
     */
    @Override
    public void addConsumer(SignalConsumer<OUT> consumer) {
        consumerSet.add(consumer);
    }

    public void addMultyConsumer(MultySignalProvider<OUT, ? extends Enum> boxConsumer, Enum type){
        this.boxConsumer = boxConsumer;
        boxConsumerType = type;
    }

    /**
     * Add SignalServices such as filter or ather algorithm
     * @param services - list of filters
     */
    @SafeVarargs
    public final void addSignalServices(SignalService<Number, Number>... services){
        for (int i = 0; i < services.length - 1; i++) {
            services[i].addConsumer(services[i+1]);
        }
        services[services.length - 1].addConsumer((SignalConsumer<Number>)finalConsumer);
        serviceList.addAll(Arrays.asList(services));
    }

    private class FinalConsumer implements SignalConsumer<OUT> {
        @Override
        public void putElement(OUT element) {
            consumerSet.forEach(x -> x.putElement(element));
            if(boxConsumer!=null){
                boxConsumer.addToQueue(boxConsumerType, element);
            }
        }
    }
}