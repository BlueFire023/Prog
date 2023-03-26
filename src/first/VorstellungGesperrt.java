package first;

/**
 * @author Denis Schaffer
 * @version 1, 12.10.2022
 */

public class VorstellungGesperrt {
    public static void main(String[] args) {
        String[] namen = {"denis", "moritz", "filip", "jannik"};
        System.out.println(spaceinsert(namen[0]));
        System.out.println(spaceinsert(namen[1]));
        System.out.println(spaceinsert(namen[2]));
        System.out.println(spaceinsert(namen[3]));
    }

    public static String spaceinsert(String n) {
        return n.length() <= 1 ? n : n.charAt(0) + " " + spaceinsert(n.substring(1));
    }
}
