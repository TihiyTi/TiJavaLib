package com.ti;

public class AdvanceSignalBoxTester {

    public static void main(String[] args) {
        AdvanceSignalBox<TestEnum> box = new AdvanceSignalBox<>(TestEnum.class);
        System.out.println();
    }

    public enum TestEnum{
        DVA,ODIN,TREE
    }
}
