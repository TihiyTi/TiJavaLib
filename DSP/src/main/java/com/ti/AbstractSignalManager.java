package com.ti;

abstract class AbstractSignalManager<TYPE extends Enum<TYPE>> {
    AdvanceSignalBox<TYPE> box;

    AbstractSignalManager(Class<TYPE> typeClass){
        box = new AdvanceSignalBox<>(typeClass);
    }


    protected void linkBeforePipe(SignalConsumer<Number> pipe, TYPE type){
        box.addTypedConsumer(pipe, type);
    }
    protected void linkAfterPipe(SignalPipe<Number,Number> pipe, TYPE type){
        pipe.addMultyConsumer(box, type);
    }

    public AdvanceSignalBox<TYPE> getBox() {
        return box;
    }
}
