package com.ti;

public interface PipeInterface<IN extends Number, OUT extends Number> extends SignalConsumer<IN>, SignalProvider<OUT>{
    void addConsumer(SignalConsumer<OUT> consumer);
    void putElement(IN element);
}
