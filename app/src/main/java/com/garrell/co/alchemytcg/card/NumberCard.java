package com.garrell.co.alchemytcg.card;

public class NumberCard implements Describable {
    private final int number;

    public NumberCard(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String describe() {
        return Integer.toString(number);
    }
}
