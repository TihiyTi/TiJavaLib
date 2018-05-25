package com.ti;

abstract class SignalService implements SignalConsumer<Number>, SignalProvider<Number> {

    SignalConsumer nextConsumer;

    @Override
    public void addConsumer(SignalConsumer consumer) {
        this.nextConsumer = consumer;
    }

    @Override
    public abstract void putElement(Number element);
}
