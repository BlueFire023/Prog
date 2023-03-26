package first;

/**
 * @version 1.0, 31.10.2022
 * @author Denis Schaffer
 */

import java.math.BigInteger;

public class Ggt {

    public static void main(String[] args) {
        BigInteger x, y;
        x = MyIO.readBigInteger("Erste Zahl: ");
        y = MyIO.readBigInteger("Zweite Zahl: ");
        System.out.println("Der größte gemeinsame Teiler ist: " + ggt(x, y));
    }

    public static int ggt(int x, int y) {
        return y == 0 ? x : ggt(y, x % y);
    }

    public static BigInteger ggt(BigInteger x, BigInteger y) {
        return y.equals(BigInteger.ZERO) ? x : ggt(y, x.mod(y));
    }

    public static long ggt(long x, long y) {
        return y == 0 ? x : ggt(y, x % y);
    }
}
