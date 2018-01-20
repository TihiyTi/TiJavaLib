package com.ti;

public interface SignalProvider<N extends Number> {
    void addConsumer(SignalConsumer<N> consumer);
}
