package first;

/**
 * @author Filip Schepers, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Denis Schaffer
 * @version 1, 07.11.2022
 */
public class FifthRoot {
    public static void main(String[] args) {
        double n = MyIO.readDouble("Zahl x: ");
        System.out.println("Wurzel fünf aus " + n + " ist " + fifthRoot(n));
    }

    public static double fifthRoot(double x) {
        int n = 1;
        double maxError = 1e-15, xn = 2.0, xnPlus1 = 1;
        while (MyMath.abs(xnPlus1 - xn) >= maxError) {
            xn = xnPlus1;
            xnPlus1 = xn - (xn * xn * xn * xn * xn - x) / (3 * xn * xn * xn * xn);
            System.out.println(" n = " + n + " ,xnPlus1=" + xnPlus1);
        }
        return xnPlus1;
    }
}