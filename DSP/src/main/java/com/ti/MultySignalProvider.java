package com.ti;

public interface MultySignalProvider<N extends Number, TYPE extends Enum<TYPE>> {
    void addTypedConsumer(SignalConsumer<N> consumer, TYPE type);
    void addToQueue(Enum type, N element);
}
