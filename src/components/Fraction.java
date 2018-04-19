package components;

import utility.BoardObject;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Stellt einen Bruch dar.
 *
 * @version 1.0
 * @author Daniel Banciu (198632), Felix Ruess (199261), Lukas Reichert (199034)
 */
public class Fraction extends Number implements Comparable<Fraction>, BoardObject, Serializable {

    private BigInteger numerator;
    private BigInteger denominator;

    /**
     * Konstruktor.
     * Inititialisiert den Zaehler und Nenner aus jeder Art von Zahl(Alle Datentypen wie int, double, etc.).
     *
     * @param numerator Zaehler
     * @param denominator Nenner
     */
    public Fraction(Number numerator, Number denominator) {
        this(new BigInteger(numerator + ""), new BigInteger(denominator + ""));
    }

    /**
     * Konstruktor.
     * Inititialisiert den Zaehler und Nenner.
     *
     * @param numerator Zaehler
     * @param denominator Nenner
     */
    public Fraction(BigInteger numerator, BigInteger denominator) {
        this.numerator = numerator;
        this.denominator = denominator;

        this.fractionCreate();
    }

    /**
     * Ueberprueft ob die Eingabe gueltig ist und kuerzt den Bruch.
     */
    private void fractionCreate(){
        switch (this.denominator.compareTo(new BigInteger(0 + ""))) {
            case 0:
            case -1:
                // this.denominator = 0.0d / 0.0;
                break;
            default:
                break;
        }
        this.curtail(this.numerator, this.denominator);
    }

    @Override
    public int compareTo(Fraction o) {
        if(doubleValue() == o.doubleValue()){
            return 0;
        }else if (doubleValue() > o.doubleValue()) {
            return 1;
        }else {
            return -1;
        }
    }

    @Override
    public long longValue() {
        return (long) intValue();
    }

    @Override
    public int intValue() {
        return numerator.intValue() / denominator.intValue();
    }

    @Override
    public float floatValue() {
        return (float) doubleValue();
    }

    @Override
    public double doubleValue() {
        return numerator.doubleValue() / denominator.doubleValue();
    }

    /**
     * Gibt den Nenner zurück.
     *
     * @return denominator {BigInteger}
     */
    public BigInteger getDenominator() {
        return denominator;
    }

    /**
     * Gibt den Zaehler zurück.
     *
     * @return numerator {BigInteger}
     */
    public BigInteger getNumerator() {
        return numerator;
    }

    /**
     * Addiert den uebergebenen Bruch mit diesem.
     *
     * @param r Bruch der addiert werden soll.
     *
     * @return {Fraction} Das Ergebnis.
     */
    public Fraction add(Fraction r) {
        BigInteger denominator = r.getDenominator().multiply(this.denominator);
        BigInteger numerator = this.numerator.multiply(r.getDenominator()).add(r.getNumerator().multiply(this.denominator));

        return new Fraction(numerator, denominator);
    }

    /**
     * Subtrahiert den uebergebenen Bruch mit diesem.
     *
     * @param r Bruch der subtrahiert werden soll.
     *
     * @return {Fraction} Das Ergebnis.
     */
    public Fraction subtract(Fraction r) {
        BigInteger denominator = r.getDenominator().multiply(this.denominator);
        BigInteger numerator;
        if (this.numerator.signum() == -1) {
            numerator = this.numerator.multiply(r.getDenominator()).add(new BigInteger(Math.abs(r.getNumerator().intValue()) + "").multiply(this.denominator));
        }else {
            numerator = this.numerator.multiply(r.getDenominator()).subtract(r.getNumerator().multiply(this.denominator));
        }
        return new Fraction(numerator, denominator);
    }

    /**
     * Multipliziert den uebergebenen Bruch mit diesem.
     *
     * @param r Bruch mit dem multipliziert werden soll.
     *
     * @return {Fraction} Das Ergebnis.
     */
    public Fraction multiply(Fraction r) {
        BigInteger denominator = r.getDenominator().multiply(this.denominator);
        BigInteger numerator = this.numerator.multiply(r.getNumerator());

        return new Fraction(numerator, denominator);
    }

    /**
     * Dividiert den uebergebenen Bruch mit diesem.
     *
     * @param r Bruch durch dem geteilt wird.
     *
     * @return {Fraction} Das Ergebnis.
     */
    public Fraction divide(Fraction r) {
        BigInteger denominator = this.denominator.multiply(r.getNumerator());
        BigInteger numerator = this.numerator.multiply(r.getDenominator());
        return new Fraction(numerator, denominator);
    }

    /**
     * Stellt den Bruch als "Bruch" dar.
     *
     * @return {String}
     */
    public String toString() {
        return numerator + (denominator.equals(new BigInteger("1")) ? "" : ("/" + denominator));
    }

    /**
     * Ueberprueft ob der Bruch ein Int ist.
     *
     * @return {boolean}
     */
    public boolean isInteger() {
        return denominator.equals(new BigInteger(1 + ""));
    }

    /**
     * Kuerzt den Bruch wenn moeglich und soweit moeglich.
     *
     * @param numerator Zaehler
     * @param denominator Nenner
     */
    private void curtail(BigInteger numerator, BigInteger denominator) {
        BigInteger ggt = numerator.gcd(denominator);
        if (!ggt.equals(new BigInteger(1 + ""))) {
            this.numerator = numerator.divide(ggt);
            this.denominator = denominator.divide(ggt);
        }
    }

    @Override
    public String display() {
        return toString();
    }
}
