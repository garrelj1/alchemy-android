package com.garrell.co.alchemytcg.card;

public class NumberCard extends Card implements DescribableCard {
    private final int number;

    public NumberCard(int id, int number) {
        super(id);
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
