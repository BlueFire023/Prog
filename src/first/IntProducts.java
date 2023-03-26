package first;

/**
 * @author Denis Schaffer
 * @version 1, 12.10.2022
 */

public class IntProducts {
    public static void main(String[] args) {
        int[] a = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29};
        int[] b = {3, 5, 11, 17, 31, 41, 59, 67, 83, 109};
        int[] c = {5, 11, 31, 59, 127, 179, 277, 331, 431, 599};
        System.out.println(multiplayAll(a));
        System.out.println(multiplayAll(b));
        System.out.println(multiplayAll(c));
    }

    public static long multiplayAll(int... a) {
        long product = 1;
        for (int i : a) {
            product *= i;
        }
        return product;
    }
}
