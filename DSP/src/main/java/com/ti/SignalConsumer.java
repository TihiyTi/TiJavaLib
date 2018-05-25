package com.ti;

public interface SignalConsumer<IN>{
    void putElement(IN element);
    default void putDoubles(Double ... elements){}
}
