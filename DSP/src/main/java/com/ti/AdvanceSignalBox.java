package com.ti;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

public class AdvanceSignalBox<N extends Number, T  extends Enum<T>> implements MultySignalProvider<N,T> {

    private Map<T, BlockingQueue<N>>  map             = new HashMap<>();
    private Map<T, Set<SignalConsumer<N>>>    mapOfConsumer   = new HashMap<>();
    public List<T> listOfType;

    public AdvanceSignalBox(Class<T> enumType) {
        enumValues(enumType);
    }

    @Override
    public void addTypedConsumer(SignalConsumer<N> consumer, T type) {
        mapOfConsumer.get(type).add(consumer);
    }
    @Override
    public void addToQueue(Enum type, N element){
        BlockingQueue<N> queue = map.get(type);
        try {
            queue.put(element);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void enumValues(Class<T> enumType) {
        List<T> list = EnumSet.allOf(enumType).stream().sorted().collect(Collectors.toList());
        for (T c : list) {
//            System.out.println(c.name());
            BlockingQueue<N> queue = new LinkedBlockingQueue<>();
            Set<SignalConsumer<N>> set = new HashSet<>();
            map.put(c, queue);
            mapOfConsumer.put(c, set);
            runNotificationService(set, queue, c);
        }
        listOfType = list;
    }

    private void runNotificationService(Set<SignalConsumer<N>> consumerSet, BlockingQueue<N> queue, Enum t){
        Executors.newSingleThreadScheduledExecutor().execute(()->{
            while(true){
                try {
                    //todo добавить возможность буфферезированной обработки
                    N element = queue.take();
//                    System.out.println("Extract "+element.doubleValue()+"  from  "+ t.name());
//                    System.out.println("CSS = "+consumerSet.size()+"  from  "+ t.name());
                    consumerSet.forEach(x-> x.putElement(element));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Deprecated
    // TODO: 19.11.2017 remove after test
    public BlockingQueue getQ(Enum type){
        return map.get(type);
    }
}
