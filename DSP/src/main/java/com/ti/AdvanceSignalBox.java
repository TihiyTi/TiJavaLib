package com.ti;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

public class AdvanceSignalBox<T  extends Enum<T>> implements MultySignalProvider<T> {

//-----------------TRACE block  -----------------
    private static final Logger LOG = LogManager.getLogger("TiSerialServiceLogger");
    private int totalAddedElement = 0;
//-----------------TRACE block  -----------------

    private final Map<T, BlockingQueue<Number>>  map             = new HashMap<>();
    private final Map<T, Set<SignalConsumer<Number>>>    mapOfConsumer   = new HashMap<>();
    public List<T> listOfType;

    public AdvanceSignalBox(Class<T> enumType) {
        enumValues(enumType);
    }

    @Override
    public void addTypedConsumer(SignalConsumer consumer, T type) {
        LOG.debug("SignalConsumer " +consumer.toString() + " with type "+ type.name() + " addToMap");
        mapOfConsumer.get(type).add(consumer);
    }
    @Override
    public void addToQueue(Enum type, Number element){

//-----------------TRACE block  -----------------
        totalAddedElement++;
//        if(totalAddedElement % 10000 == 0){
//            LOG.trace(map.get(type).size() + " elements added to box with type "+ type.name());
//        }
//-----------------TRACE block  -----------------

        BlockingQueue<Number> queue = map.get(type);
        try {
//            if(type.name().equals("POINT_ONE")){
//                System.out.println("AddToBox " +type.name()+" : " +element.toString());
//            }

            queue.put(element);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void enumValues(Class<T> enumType) {
        List<T> list = EnumSet.allOf(enumType).stream().sorted().collect(Collectors.toList());
        for (T c : list) {
//            System.out.println(c.name());
            BlockingQueue<Number> queue = new LinkedBlockingQueue<>();
            Set<SignalConsumer<Number>> set = new HashSet<>();
            map.put(c, queue);
            mapOfConsumer.put(c, set);
            runNotificationService(mapOfConsumer, queue, c);
        }
        listOfType = list;
    }

    private void runNotificationService(Map<T, Set<SignalConsumer<Number>>>    map, BlockingQueue<Number> queue, T t){
        Executors.newSingleThreadScheduledExecutor().execute(()->{
//            Double[] array = new Double[20];
            int index = 0;
            while(true){
                try {
                    //todo добавить возможность буфферезированной обработки
                    Number element = queue.take();
//                    array[index] = element.doubleValue();
//                    index++;
//                    if(index == 5){
//                        index = 0;
//                        map.get(t).forEach(x-> x.putDoubles(array));
//                    }
//                    if(t.name() == "REO1"){
//                        System.out.println("Extract "+element.doubleValue()+"  from  "+ t.name());
//                    }
                    map.get(t).forEach(x-> x.putElement(element));

//                    System.out.println("CSS = "+consumerSet.size()+"  from  "+ t.name());
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
