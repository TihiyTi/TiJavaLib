package com.ti;

abstract class SignalService implements SignalConsumer<Number>,SignalProvider<Number> {

    SignalConsumer<Number> nextConsumer;

    @Override
    public void addConsumer(SignalConsumer<Number> consumer) {
        this.nextConsumer = consumer;
    }

    @Override
    public abstract void putElement(Number element);
}
