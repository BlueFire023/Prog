package first;

/**
 * @version 2, 08.11.2022
 * @author Filip Schepers, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Denis Schaffer
 */

public class Sinus {
    public static void main(String[] arg) {
        System.out.println(sinApprox(MyIO.readDouble("Gib Double(x) für Sinus Annäherung ein: ")));
        //System.out.println(sinApprox(Pi.pi(MyIO.readLong("Gib für Pi Annäherung an: "))));
    }

    public static double sinApprox(double x) {
        double ergebnis = x, sp = 0, spabs = 0;
        int z = 1;
        do {
            sp += ergebnis;
            ergebnis *= ((-1 * (x * x)) / ((double) (2 * z * (2 * z + 1))));
            z++;
            if (ergebnis < 0) {
                spabs = MyMath.abs(ergebnis);
            }
        } while (!(spabs < 1e-15));
        return sp;
    }
}