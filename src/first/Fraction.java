package first;

/**
 * @version 1.1, 23.03.2023
 * @author Filip Schepers, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Denis Schaffer
 */

import java.math.BigInteger;

public class Fraction extends Number implements Comparable<Fraction> {

    public final static Fraction NaN = new Fraction(0, 0); //Darstellung des Bruchs 0/0 als NaN
    protected BigInteger numerator; //Wert für den Zähler
    protected BigInteger denominator; //Wert für den Nenner

    public Fraction(BigInteger n, BigInteger d) {
        numerator = n;
        denominator = d;
        //Runden bzw. Vereinfachen des Bruches
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
    } //Alternativer Konstruktor für long-Werte

    public Fraction(String n, String d) {
        this(BigInteger.valueOf(Long.parseLong(n)), BigInteger.valueOf(Long.parseLong(d)));
    } //Alternativer Konstruktor für String-Werte

    public BigInteger getNumerator() {
        return numerator;
    } //Rückgabe des Zählers

    public BigInteger getDenominator() {
        return denominator;
    } //Rückgabe des Nenners

    public Fraction add(Fraction r) { // Additions-Methode
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

    public Fraction subtract(Fraction r) { //Subtraktions-Methode
        BigInteger speicher = this.denominator.multiply(r.denominator);
        BigInteger a = this.numerator.multiply(r.denominator);
        BigInteger b = r.numerator.multiply(this.denominator);
        return new Fraction(a.subtract(b), speicher);
    }

    public Fraction multiply(Fraction r) { //Multiplikations-Methode
        BigInteger nNumerator = new BigInteger(String.valueOf(0));
        BigInteger nDenominator = new BigInteger(String.valueOf(0));
        nNumerator = this.numerator.multiply(r.numerator);
        nDenominator = this.denominator.multiply(r.denominator);
        return new Fraction(nNumerator, nDenominator);
    }

    public Fraction divide(Fraction r) { //Divisions-Methode
        Fraction f = new Fraction(r.denominator, r.numerator);
        return this.multiply(f);
    }

    public String toString() {
        return this.numerator.toString() + "/" + this.denominator.toString();
    } //Umwandlung des Bruchs in einen String

    public boolean isInteger() {
        return this.numerator.mod(this.denominator).signum() == 0;
    } //Prüfung, ob es sich um einen Int-Wert handelt

    @Override
    public int compareTo(Fraction o) {
        if (((this.numerator.doubleValue() / this.denominator.doubleValue()) < (o.numerator.doubleValue() / o.denominator.doubleValue()))) {
            return -1;
        } else if (((this.numerator.doubleValue() / this.denominator.doubleValue()) == (o.numerator.doubleValue() / o.denominator.doubleValue()))) {
            return 0;
        } else {
            return 1;
        }
    }//Methode zum Vergleichen

    @Override
    public int intValue() {
        return this.numerator.divide(this.denominator).intValue();
    } //Umwandlung zu Int-Wert

    @Override
    public long longValue() {
        return this.numerator.divide(this.denominator).longValue();
    }//Umwandlung zu Long-Wert

    @Override
    public float floatValue() {
        return this.numerator.floatValue() / this.denominator.floatValue();
    }//Umwandlung zu Float-Wert

    @Override
    public double doubleValue() {
        return this.numerator.doubleValue() / this.denominator.doubleValue();
    }//umwandlung zu Double-Wert
}
