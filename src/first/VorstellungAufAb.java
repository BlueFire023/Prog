package first;

/**
 * @author Denis Schaffer
 * @version 1, 12.10.2022
 */

public class VorstellungAufAb {
    public static void main(String[] args) {
        String[] namen = {"denis", "moritz", "filip", "jannik"};
        System.out.println(upnDown(namen[0]));
        System.out.println(upnDown(namen[1]));
        System.out.println(upnDown(namen[2]));
        System.out.println(upnDown(namen[3]));
    }

    public static String upnDown(String n) {
        return n.length() >= 2 ? n.substring(0, 1).toLowerCase() + n.substring(1, 2).toUpperCase() + upnDown(n.substring(2)) : n;
    }
}
