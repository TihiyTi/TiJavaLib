package com.ti;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DspFilter {
    @Test
    public void signalPipeTest(){
        List<Integer> intList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        FinalSaveFilter<Number> fin = new FinalSaveFilter<>();

        SignalPipe<Number,Number> pipe = getTestPipe();
        pipe.addConsumer(fin);

        intList.forEach(pipe::putElement);

        Assert.assertArrayEquals(
                fin.getResult().stream().mapToDouble(Number::doubleValue).toArray(),
                new double[]{2.0, 6.0, 12.0, 18.0, 24.0, 30.0, 36.0, 42.0}, 0.001);
    }

    /**
     * Тело теста имитирует работу SignalManager
     */
    @Test
    public void test(){
        List<Integer> intList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);

        AbstractSignalManager<TestSignalType> manager = new TestSignalManager(TestSignalType.class);

        FinalSaveFilter<Number> fin = new FinalSaveFilter<>();
        manager.getBox().addTypedConsumer(fin, TestSignalType.SIG_2);

        intList.forEach(x-> manager.getBox().addToQueue(TestSignalType.SIG_1, x));

        while(fin.getResult().size() < 8){
//            System.out.print( "");
        }
        System.out.println(fin.getResult().toString());

    }

    private SignalPipe<Number,Number> getTestPipe(){
        SignalService<Number,Number> fir1 = new FirFilter<>(1);
        SignalService<Number,Number> fir2 = new FirFilter<>(2);
        SignalService<Number,Number> fir3 = new FirFilter<>(1,1,1);

        SignalPipe<Number, Number> pipe = new SignalPipe<>();
        pipe.addSignalServices(fir1, fir2, fir3);
        return pipe;
    }

    class TestSignalManager extends AbstractSignalManager<TestSignalType>{
        SignalPipe<Number,Number> pipe1 = getTestPipe();

        TestSignalManager(Class<TestSignalType> tClass) {
            super(tClass);
            linkBeforePipe(pipe1, TestSignalType.SIG_1);
            linkAfterPipe(pipe1, TestSignalType.SIG_2);
        }
    }
}
