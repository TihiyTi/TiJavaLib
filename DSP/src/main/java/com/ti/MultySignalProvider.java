package com.ti;

public interface MultySignalProvider<TYPE extends Enum<TYPE>> {
    void addTypedConsumer(SignalConsumer<Number> consumer, TYPE type);
    void addToQueue(Enum type, Number element);
}
