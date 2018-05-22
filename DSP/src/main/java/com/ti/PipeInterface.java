package com.ti;

public interface PipeInterface extends SignalConsumer, SignalProvider{
    void addConsumer(SignalConsumer consumer);
    void putElement(Number element);
}
