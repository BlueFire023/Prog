package first;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 12.12.22
 */

import java.util.Arrays;
import java.util.function.*;
import java.util.stream.*;


public class FractionStreams {

    public static void main(String[] args) {
        Fraction[] f = new Fraction[(29 * 29) - 57];
        int count = 0;
        for (int i = -9; i <= 19; i++) {
            for (int j = -9; j <= 19; j++) {
                if (!(i == 0 || j == 0)) {
                    f[count] = new Fraction(i, j);
                    count++;
                }
            }
        }

        Float[] fl = new Float[(29 * 29) - 57];
        for (int i = 0; i < (29 * 29) - 57; i++) {
            fl[i] = f[i].floatValue();
        }

        Double[] dz = new Double[(29 * 29) - 57];
        for (int i = 0; i < (29 * 29) - 57; i++) {
            dz[i] = Math.sin(f[i].doubleValue());
        }
        System.out.println("Fraction Liste :");
        System.out.print(Arrays.toString(f));
        System.out.println();
        System.out.println("Float Liste :");
        System.out.print(Arrays.toString(fl));
        System.out.println();
        System.out.println("Sortierte und aussortierte Liste:");
        Stream.of(fl).distinct().sorted().forEach(d -> {
            System.out.print(" " + d);
        });
        System.out.println();
        System.out.println("Elemente quadriert, ohne Doppelte, als Brüche: ");
        plot((Fraction x) -> x.multiply(x), f);
        System.out.println();
        System.out.println("Elemente in zufälliger Reihenfolge, bei denen Zähler und NennerPrimzahlen sind: ");
        Fraction[] fz = new Fraction[f.length];
        int r;
        for (Fraction fraction : f) {
            while (true) {
                r = MyMath.rand(0, f.length - 1);
                if (fz[r] == null) {
                    fz[r] = fraction;
                    break;
                }
            }
        }
        Stream.of(fz).forEach(d -> {
            if (MyMath.isPrime(d.numerator.longValue()) && MyMath.isPrime(d.denominator.longValue())) {
                System.out.print(" " + d + "->" + d.numerator + "/" + d.denominator + " ");
            }
        });
        System.out.println();
        System.out.println("Elemente sortiert, die ganze Zahlen dargestellt: ");
        Stream.of(f).sorted().forEach(d -> {
            if (d.isInteger()) {
                System.out.print(" " + d + "->" + d.numerator + "/" + d.denominator);
            }
        });
        System.out.println();
        System.out.println("Sin-Werte aller Elemente, als double Werte, sortiert, ohne Doppelte: ");
        Stream.of(dz).distinct().sorted().forEach(d -> {
            System.out.print(" " + d);
        });
        System.out.println();
    }

    public static void plot(UnaryOperator<Fraction> f, Fraction[] fl) {
        for (Fraction fraction : fl) {
            System.out.print(" " + fraction + " -> " + f.apply(fraction) + " ");
        }
    }
}
