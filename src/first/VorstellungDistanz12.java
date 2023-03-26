package first;

/**
 * @author Denis Schaffer
 * @version 1, 12.10.2022
 */

public class VorstellungDistanz12 {
    public static void main(String[] args) {
        String[] namen = {"denis", "moritz", "filip", "jannik"};
        System.out.println(osSpace(namen[0]));
        System.out.println(osSpace(namen[1]));
        System.out.println(osSpace(namen[2]));
        System.out.println(osSpace(namen[3]));
    }

    public static String osSpace(String n) {
        return n.length() >= 2 ? n.charAt(0) + " " + n.charAt(1) + "  " + osSpace(n.substring(2)) : n;
    }
}