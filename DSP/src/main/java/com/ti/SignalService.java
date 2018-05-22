package com.ti;

abstract class SignalService implements SignalConsumer, SignalProvider {

    SignalConsumer nextConsumer;

    @Override
    public void addConsumer(SignalConsumer consumer) {
        this.nextConsumer = consumer;
    }

    @Override
    public abstract void putElement(Number element);
}
