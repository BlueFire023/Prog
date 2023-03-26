package first;

/**
 * @author Filip Schepers, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Denis Schaffer
 * @version 2, 08.11.2022
 */

public class WordLcm {
    public static void main(String[] arg) {
        String n = MyIO.promptAndRead("Bitte Zahl als Wort eingeben: ").toLowerCase().trim();
        long kgv = 1L;
        while (n.length() > 0) {
            long h = 1;
            switch (n) {
                case "eins" -> h = 1L;
                case "zwei" -> h = 2L;
                case "drei" -> h = 3L;
                case "vier" -> h = 4L;
                case "fünf" -> h = 5L;
                case "sechs" -> h = 6L;
                case "sieben" -> h = 7L;
                case "acht" -> h = 8L;
                case "neun" -> h = 9L;
                case "zehn" -> h = 10L;
                case "elf" -> h = 11L;
                case "zwölf" -> h = 12L;
                case "dreizehn" -> h = 13L;
                case "vierzehn" -> h = 14L;
                case "fünfzehn" -> h = 15L;
                case "sechzehn" -> h = 16L;
                case "siebzehn" -> h = 17L;
                case "achtzehn" -> h = 18L;
                case "neunzehn" -> h = 19L;
                case "zwanzig" -> h = 20L;
                case "einundzwanzig" -> h = 21L;
                case "zweiundzwanzig" -> h = 22L;
                case "dreiundzwanzig" -> h = 23L;
                case "vierundzwanzig" -> h = 24L;
                case "fünfundzwanzig" -> h = 25L;
                case "sechsundzwanzig" -> h = 26L;
                case "siebenundzwanzig" -> h = 27L;
                case "achtundzwanzig" -> h = 28L;
                case "neunundzwanzig" -> h = 29L;
                default -> System.out.println("Ungültige Zahl");
            }
            kgv = (kgv * h) / ggt(kgv, h);
            n = MyIO.promptAndRead("Bitte nächste Zahl als Wort eingeben: ").toLowerCase().trim();
        }
        System.out.println("Das KgV aller Zahlen ist: " + kgv);
    }

    public static long ggt(long x, long y) {
        return y == 0 ? x : ggt(y, x % y);
    }
}
