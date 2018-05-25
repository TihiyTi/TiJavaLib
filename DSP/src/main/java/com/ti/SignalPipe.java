package com.ti;

import java.util.*;

public class SignalPipe implements PipeNumberInterface {
    private List<SignalConsumer> serviceList = new ArrayList<>();

    private FinalConsumer finalConsumer = new FinalConsumer(); ///
    private Set<SignalConsumer> consumerSet = new HashSet<>();
    private MultySignalProvider<? extends Enum> boxConsumer;
    private Enum boxConsumerType;

    private int inputPipeCount = 0;

    @Override
    public void putElement(Number element) {
        inputPipeCount++;
//        System.out.println("Input "+ inputPipeCount +" : " +element.toString());
        serviceList.get(0).putElement(element);
    }

    /**
     * Add consumer for pipe
     * @param consumer - comsumer
     */
    @Override
    public void addConsumer(SignalConsumer consumer) {
        consumerSet.add(consumer);
    }

    public void addMultyConsumer(MultySignalProvider<? extends Enum> boxConsumer, Enum type){
        this.boxConsumer = boxConsumer;
        boxConsumerType = type;
    }

    /**
     * Add SignalServices such as filter or ather algorithm
     * @param services - list of filters
     */
    @SafeVarargs
    public final void addSignalServices(SignalService... services){
        for (int i = 0; i < services.length - 1; i++) {
            services[i].addConsumer(services[i+1]);
        }
        services[services.length - 1].addConsumer(finalConsumer);
        serviceList.addAll(Arrays.asList(services));
    }

    private class FinalConsumer implements SignalConsumer<Number>{
        @Override
        public void putElement(Number element) {
            consumerSet.forEach(x -> x.putElement(element));
            if(boxConsumer!=null){
//                System.out.println("OutPut "+ inputPipeCount +" : " +element.toString());
                boxConsumer.addToQueue(boxConsumerType, element);
            }
        }
    }
}
