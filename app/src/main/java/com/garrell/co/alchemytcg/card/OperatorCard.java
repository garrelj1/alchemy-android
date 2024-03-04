package com.garrell.co.alchemytcg.card;

public class OperatorCard implements Describable {

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

    private OperatorCard(Operator op) {
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
