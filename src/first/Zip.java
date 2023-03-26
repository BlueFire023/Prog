package first;

/**
 * @author Filip Schepers, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Denis Schaffer
 * @version 2, 08.11.2022
 */

public class Zip {
    public static void main(String[] args) {
        String a = "Heute is ein guter Tag";
        String b = "Java ist eine tolle Sprache";
        System.out.println(a);
        System.out.println(b);
        System.out.println(zip(a, b));
    }

    public static String zip(String a, String b) {
        return a.length() * b.length() != 0 ? a.charAt(0) + b.charAt(0) + zip(a.substring(1), b.substring(1)) : a + b;
    }
}
