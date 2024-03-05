package com.garrell.co.alchemytcg.card;

public class OperatorCard extends Card implements DescribableCard {

    private final Operator op;

    public enum Operator {
        ADD("+"),
        SUBTRACT("-"),
        MULTIPLY("*"),
        DIVIDE("/");

        private final String sign;

        Operator(String sign) {
            this.sign = sign;
        }

        public String getSign() {
            return sign;
        }
    }

    public OperatorCard(int id, Operator op) {
        super(id);
        this.op = op;
    }

    @Override
    public String describe() {
        return op.sign;
    }

    public Operator getOp() {
        return op;
    }
}
