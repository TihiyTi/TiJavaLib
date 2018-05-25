package com.ti;

public interface PipeNumberInterface extends SignalConsumer<Number>, SignalProvider<Number>{
    void addConsumer(SignalConsumer consumer);
    void putElement(Number element);
}
