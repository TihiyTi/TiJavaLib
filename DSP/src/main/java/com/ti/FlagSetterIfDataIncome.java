package com.ti;

import java.util.concurrent.atomic.AtomicBoolean;

public class FlagSetterIfDataIncome extends SignalService{

    private AtomicBoolean state;
    private boolean stateToSet;

    public FlagSetterIfDataIncome(AtomicBoolean state, boolean stateToSet) {
        this.state = state;
        this.stateToSet = stateToSet;
    }


    @Override
    public void putElement(Number element) {
        state.set(stateToSet);
        if(stateToSet){
            System.out.println("Breath IN");
        }else {
            System.out.println("Breath OUT");
        }
        nextConsumer.putElement(element);
    }
}
