package first;

/**
 * @author Denis Schaffer, Filip Schepers, Daniel Faigle, Moritz Binneweiß
 * @version 1, 17,10,2022
 **/

import java.io.*;

public class PalindromTest {
    public static void main(String[] args) throws Exception {
        InputStreamReader isr;
        BufferedReader keyboard;
        String line;
        String invertLine;
        isr = new InputStreamReader(System.in);
        keyboard = new BufferedReader(isr);

        System.out.println("Gib Wort für Palindrom Test: ");
        line = keyboard.readLine().toLowerCase();
        System.out.println(line);
        invertLine = invert(line);
        System.out.println(invertLine);
        if (invertLine.equals(line)) {
            System.out.println("es ist ein Palindrom");
        } else {
            System.out.println("es ist kein Palindrom");
        }
    }

    public static String invert(String n) {
        return n.length() <= 1 ? n : invert(n.substring(1)) + n.charAt(0);
    }
}
