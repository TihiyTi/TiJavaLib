package com.ti;

import java.util.concurrent.atomic.AtomicBoolean;

public class FlagSetterIfDataIncome<IN extends Number> extends SignalService<IN, Number> {

    private AtomicBoolean state;
    private boolean stateToSet;

    public FlagSetterIfDataIncome(AtomicBoolean state, boolean stateToSet) {
        this.state = state;
        this.stateToSet = stateToSet;
    }


    @Override
    public void putElement(IN element) {
//        state.set(stateToSet);
        if(stateToSet){
            System.out.println("Breath IN");

        }else {
            System.out.println("Breath OUT");
        }
        nextConsumer.putElement(element);
    }
}
