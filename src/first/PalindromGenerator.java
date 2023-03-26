package first;

/**
 * @author Denis Schaffer, Filip Schepers, Daniel Faigle, Moritz Binneweiß
 * @version 1, 17,10,2022
 **/

import java.io.*;

public class PalindromGenerator {
    public static void main(String[] args) throws Exception {
        File f = new File("src/first/palindrom.txt");
        PrintStream target = new PrintStream(new FileOutputStream(f));
        InputStreamReader isr;
        BufferedReader keyboard;
        String line1, line2;
        isr = new InputStreamReader(System.in);
        keyboard = new BufferedReader(isr);

        System.out.println("Gib ersten Satz: ");
        line1 = keyboard.readLine();
        System.out.println("Gib zweiten Satz: ");
        line2 = keyboard.readLine();
        System.out.println(line1 + line2 + invert(line1 + line2));
        target.println(line1 + line2 + invert(line1 + line2));
        System.out.println(line2 + line1 + invert(line2 + line1));
        target.println(line2 + line1 + invert(line2 + line1));
        System.out.println(zip(line1, line2) + invert(zip(line1, line2)));
        target.println(zip(line1, line2) + invert(zip(line1, line2)));
        System.out.println(zip(line2, line1) + invert(zip(line2, line1)));
        target.println(zip(line2, line1) + invert(zip(line2, line1)));
        System.out.println(spaceinsert(line1 + line2) + invert(spaceinsert(line1 + line2)));
        target.println(spaceinsert(line1 + line2) + invert(spaceinsert(line1 + line2)));
    }

    public static String invert(String n) {
        return n.length() <= 1 ? n : invert(n.substring(1)) + n.charAt(0);
    }

    public static String zip(String a, String b) {
        return a.length() * b.length() != 0 ? a.charAt(0) + b.charAt(0) + zip(a.substring(1), b.substring(1)) : a + b;
    }

    public static String spaceinsert(String n) {
        return n.length() <= 1 ? n : n.charAt(0) + " " + spaceinsert(n.substring(1));
    }
}
