package first;

/**
 * @version 1, 21.11.2022
 * @author Filip Schepers, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Denis Schaffer
 */

import java.math.BigInteger;
import java.util.Arrays;

public class Fractrain {
    public static void main(String[] args) {
        Fraction[] L = {new Fraction(17, 91), new Fraction(78, 85),
                new Fraction(19, 51), new Fraction(23, 38),
                new Fraction(29, 33), new Fraction(77, 29),
                new Fraction(95, 23), new Fraction(77, 19),
                new Fraction(1, 17), new Fraction(11, 13),
                new Fraction(13, 11), new Fraction(15, 2),
                new Fraction(1, 7), new Fraction(55, 1)};
        System.out.println(Arrays.toString(L));


        Fraction b = new Fraction(8, 1);
        int z = 1;
        Fraction groesste = b;
        for (int i = 1; i < 8000; i++) {//8000-te Stelle ausgeben
            for (Fraction f : L) {
                Fraction c = b.multiply(f);
                if (c.isInteger()) {
                    b = c;
                    System.out.println(b);
                    if (groesste.numerator.compareTo(b.numerator) == -1)//Das Größte berechnen
                    {
                        groesste = b;
                        z = i;//Stelle des größten berechnen

                    }
                    break;
                }
            }

        }
        System.out.println(groesste);
        System.out.println(z);

    }
}
