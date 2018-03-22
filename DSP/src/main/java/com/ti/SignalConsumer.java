package com.ti;

public interface SignalConsumer<N extends Number>{
    void putElement(N element);
    default void putDoubles(Double ... elements){}
}
