package first;

/**
 * @author Denis Schaffer
 * @version 1.0, 31.10.2022
 */

public class PowerTower {
    public static void main(String[] args) {

        double d = MyIO.readDouble("Reele Zahl: ");
        int n = MyIO.readInt("n: ");
        if (n == 0) {
            System.out.println("1.0");
        } else {
            System.out.println(pTower(d, n));
        }
    }

    static double pTower(double base, int n) {
        double result = base;
        for (int i = 0; i < n - 1; i++) {
            result = Math.pow(result, base);
        }
        return result;
    }
}
