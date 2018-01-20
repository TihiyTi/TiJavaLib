package com.ti;

abstract class SignalService<IN extends Number, OUT extends Number> implements SignalConsumer<IN>,SignalProvider<OUT> {

    SignalConsumer<OUT> nextConsumer;

    @Override
    public void addConsumer(SignalConsumer<OUT> consumer) {
        this.nextConsumer = consumer;
    }

    @Override
    public abstract void putElement(IN element);
}
