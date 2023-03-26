package first;

/**
 * @author Denis Schaffer
 * @version 1, 12.10.2022
 */

public class IntSums {
    public static void main(String[] args) {
        int[] a = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29};
        int[] b = {3, 5, 11, 17, 31, 41, 59, 67, 83, 109};
        int[] c = {5, 11, 31, 59, 127, 179, 277, 331, 431};
        System.out.println(addAll(a));
        System.out.println(addAll(b));
        System.out.println(addAll(c));
    }

    public static int addAll(int... a) {
        int count = 0;
        for (int i : a) {
            count += i;
        }
        return count;
    }
}
