package first;

/**
 * @author Denis Schaffer
 * @version 1, 12.10.2022
 */

public class Vorstellung {
    public static void main(String[] args) {
        String[] namen = {"Denis", "Moritz", "Filip", "Jannik"};
        System.out.println(namen[0]);
        System.out.println(namen[1]);
        System.out.println(namen[2]);
        System.out.println(namen[3]);
        System.out.println(invert(namen[0]));
        System.out.println(invert(namen[1]));
        System.out.println(invert(namen[2]));
        System.out.println(invert(namen[3]));
    }

    public static String invert(String n) {
        return n.length() <= 1 ? n : invert(n.substring(1)) + n.charAt(0);
    }
}
