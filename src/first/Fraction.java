package first;

/**
 * @version 1.1, 23.03.2023
 * @author Filip Schepers, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Denis Schaffer
 */

import java.math.BigInteger;

public class Fraction extends Number implements Comparable<Fraction> {

    public final static Fraction NaN = new Fraction(0, 0);
    protected BigInteger numerator;
    protected BigInteger denominator;

    public Fraction(BigInteger n, BigInteger d) {
        numerator = n;
        denominator = d;
        while (true) {
            if (denominator.signum() == 0) {
                numerator = BigInteger.ZERO;
                break;
            } else if (denominator.signum() < 0) {
                this.numerator = numerator.negate();
                this.denominator = denominator.negate();
            } else {
                BigInteger di = numerator.gcd(denominator);
                numerator = numerator.divide(di);
                denominator = denominator.divide(di);
                break;
            }
        }
    }

    public Fraction(long n, long d) {
        this(BigInteger.valueOf(n), BigInteger.valueOf(d));
    }

    public Fraction(String n, String d) {
        this(BigInteger.valueOf(Long.parseLong(n)), BigInteger.valueOf(Long.parseLong(d)));
    }

    public BigInteger getNumerator() {
        return numerator;
    }

    public BigInteger getDenominator() {
        return denominator;
    }

    public Fraction add(Fraction r) {
        if (this.equals(NaN)) {
            return r;
        } else if (r.equals(NaN)) {
            return this;
        } else {
            BigInteger speicher = this.denominator.multiply(r.denominator);
            BigInteger a = this.numerator.multiply(r.denominator);
            BigInteger b = r.numerator.multiply(this.denominator);
            return new Fraction(a.add(b), speicher);
        }
    }

    public Fraction subtract(Fraction r) {
        BigInteger speicher = this.denominator.multiply(r.denominator);
        BigInteger a = this.numerator.multiply(r.denominator);
        BigInteger b = r.numerator.multiply(this.denominator);
        return new Fraction(a.subtract(b), speicher);
    }

    public Fraction multiply(Fraction r) {
        BigInteger nNumerator = new BigInteger(String.valueOf(0));
        BigInteger nDenominator = new BigInteger(String.valueOf(0));
        nNumerator = this.numerator.multiply(r.numerator);
        nDenominator = this.denominator.multiply(r.denominator);
        return new Fraction(nNumerator, nDenominator);
    }

    public Fraction divide(Fraction r) {
        Fraction f = new Fraction(r.denominator, r.numerator);
        return this.multiply(f);
    }

    public String toString() {
        return this.numerator.toString() + "/" + this.denominator.toString();
    }

    public boolean isInteger() {
        return this.numerator.mod(this.denominator).signum() == 0;
    }

    @Override
    public int compareTo(Fraction o) {
        if (((this.numerator.doubleValue() / this.denominator.doubleValue()) < (o.numerator.doubleValue() / o.denominator.doubleValue()))) {
            return -1;
        } else if (((this.numerator.doubleValue() / this.denominator.doubleValue()) == (o.numerator.doubleValue() / o.denominator.doubleValue()))) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int intValue() {
        return this.numerator.divide(this.denominator).intValue();
    }

    @Override
    public long longValue() {
        return this.numerator.divide(this.denominator).longValue();
    }

    @Override
    public float floatValue() {
        return this.numerator.floatValue() / this.denominator.floatValue();
    }

    @Override
    public double doubleValue() {
        return this.numerator.doubleValue() / this.denominator.doubleValue();
    }
}
