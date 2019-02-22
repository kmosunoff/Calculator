package com.example.kmosunoff.calculator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

public class RationalFraction {
    private BigInteger numerator, denominator;

    private RationalFraction(BigInteger numerator, BigInteger denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
        this.reduce();
    }

    public RationalFraction(String number) {
        BigDecimal d = new BigDecimal(number);
        denominator = BigInteger.valueOf(1);

        if (!d.equals(BigDecimal.valueOf(0.0))) {
            while (!isIntegerValue(d)) {
                d = d.multiply(BigDecimal.valueOf(10));
                denominator = denominator.multiply(BigInteger.valueOf(10));
            }
        }
        numerator = d.toBigInteger();
        this.reduce();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RationalFraction that = (RationalFraction) o;
        return this.numerator.multiply(that.denominator).equals(this.denominator.multiply(that.numerator));
    }

    @Override
    public int hashCode() {
        return Objects.hash(numerator, denominator);
    }

    @Override
    public String toString() {
        return numerator.toString() + "/" + denominator.toString();
    }

    public void reduce() {
        BigInteger gcd = numerator.gcd(denominator);
        numerator = numerator.divide(gcd);
        denominator = denominator.divide(gcd);
        if (denominator.compareTo(BigInteger.valueOf(0)) < 0) {
            numerator = numerator.negate();
            denominator = denominator.negate();
        }
    }

    public RationalFraction plus(RationalFraction another) {
        BigInteger commonDenominator = this.denominator.multiply(another.denominator);
        BigInteger newNumerator = this.numerator.multiply(another.denominator).add(another.numerator.multiply(this.denominator));
        return new RationalFraction(newNumerator, commonDenominator);
    }
    public RationalFraction multiply(RationalFraction another) {
        BigInteger newNumerator = this.numerator.multiply(another.numerator);
        BigInteger newDenominator = this.denominator.multiply(another.denominator);
        return new RationalFraction(newNumerator, newDenominator);
    }
    public RationalFraction minus(RationalFraction another) {
        return this.plus(another.negate());
    }
    public RationalFraction divide(RationalFraction another) {
        if (another.equals(new RationalFraction(BigInteger.valueOf(0),BigInteger.valueOf(1)))) {
            return null;
        }
        return this.multiply(new RationalFraction(another.denominator, another.numerator));
    }
    public boolean lessThan(RationalFraction another) {
        return this.numerator.multiply(another.denominator).compareTo(this.denominator.multiply(another.numerator)) < 0;
    }
    public boolean greaterThan(RationalFraction another) {
        return this.numerator.multiply(another.denominator).compareTo(this.denominator.multiply(another.numerator)) > 0;
    }

    public RationalFraction negate() {
        return new RationalFraction(numerator.negate(), denominator);
    }

    private boolean isIntegerValue(BigDecimal bd) {
        return bd.stripTrailingZeros().scale() <= 0;
    }

    public String toDecimalString() {
        BigDecimal answer = new BigDecimal(numerator);
        answer = answer.divide(new BigDecimal(denominator), 5, BigDecimal.ROUND_HALF_UP);
        return answer.toString();
    }
}

