package com.ti;

public interface SignalConsumer{
    void putElement(Number element);
    default void putDoubles(Double ... elements){}
}
