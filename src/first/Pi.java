package first;

/**
 * @author Denis Schaffer
 * @version 1.0, 31.10.2022
 */

public class Pi {
    public static void main(String[] args) {
        System.out.println("Wir wollen Pi errechnen.");
        long n = MyIO.readLong("Gib Anzahl der Durchläufe (hohe Zahlen erhöhen Genauigkeit): ");
        System.out.println("Berechneter Wert: " + pi(n));
    }

    public static double pi(long n) {
        double res = 0;
        long p, ng = n, r = 0;
        while (n != 0) {
            res += 6d / (n * n);
            n--;
            if (ng >= 10000000) { //ab hier nur für die Prozent Anzeige (nur Sinnvoll bei n > 10 000 000)
                p = 100 - (n * 100) / ng;
                if (p >= r) {
                    System.out.print("\rProgress: " + p + "%");
                    r += 1;
                }
            }//bis hier
        }
        System.out.println();
        return Math.sqrt(res);
    }
}
