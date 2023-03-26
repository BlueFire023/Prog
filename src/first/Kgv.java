package first;

/**
 * @version 1.0, 31.10.2022
 * @author Denis Schaffer
 */

import java.math.BigInteger;

public class Kgv {
    public static void main(String[] args) {
        BigInteger x = MyIO.readBigInteger("Gib x: ");
        BigInteger y = MyIO.readBigInteger("Gib y: ");
        System.out.println("Kgv: " + kgv(x, y) + " Rest: " + x.mod(y));
    }

    public static BigInteger kgv(BigInteger x, BigInteger y) {
        return x.multiply(y).divide(Ggt.ggt(x, y));
    }
}
