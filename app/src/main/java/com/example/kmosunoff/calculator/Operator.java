package com.example.kmosunoff.calculator;

import java.math.BigInteger;
import java.util.Objects;

public class Operator {

    private boolean isUnary = false;
    private char op;
    private RationalFraction number = null;

    public Operator(char op) {
        this.op = op;
    }

    public Operator(char op, boolean isUnary) {
        this.op = op;
        this.isUnary = isUnary;
    }

    public Operator(char op, RationalFraction number) {
        this.op = op;
        this.number = number;
    }

    public char getOperation() {
        return this.op;
    }

    public boolean isUnary() {
        return isUnary;
    }

    public boolean isTernary() {
        return number != null;
    }

    public RationalFraction getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operator operator = (Operator) o;
        return op == operator.op;
    }

    @Override
    public int hashCode() {

        return Objects.hash(op);
    }

    public void setUnary(boolean b) {
        isUnary = b;
    }
}
