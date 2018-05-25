package com.ti;

public interface SignalProvider<OUT>{
    void addConsumer(SignalConsumer<OUT> consumer);
}
