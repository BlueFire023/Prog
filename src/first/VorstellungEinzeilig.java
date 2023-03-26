package first;

/**
 * @author Denis Schaffer
 * @version 1, 12.10.2022
 */

public class VorstellungEinzeilig {
    public static void main(String[] args) {
        String[] namen = {"Denis", "Moritz", "Filip", "Jannik"};
        System.out.println(connect(namen));
    }

    public static String connect(String... a) {
        String connected = "";
        for (String i : a) {
            connected += i;
        }
        return connected;
    }
}
