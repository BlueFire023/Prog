package first;

/**
 * @author Filip Schepers, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Denis Schaffer
 * @version 1, 14.11.2022
 */

enum Monate {
    Januar(1),
    Februar(2),
    März(3),
    April(4),
    Mai(5),
    Juni(6),
    Juli(7),
    August(8),
    September(9),
    Oktober(10),
    November(11),
    Dezember(12);

    private final int N;

    Monate(final int N) {
        this.N = N;
    }

    public int getNumber() {
        return this.N;
    }
}

public class Monat {
    public static void main(String[] args) {
        for (Monate m : Monate.values()) {
            if (m.toString().toLowerCase().contains("m") && m.getNumber() % 2 != 0) {
                System.out.println(m.getNumber() + ". " + m);
            }
        }
        System.out.println("");
        for (Monate m : Monate.values()) {
            if (m.toString().toLowerCase().contains("b") && m.getNumber() % 2 == 0) {
                System.out.println(m.getNumber() + ". " + m);
            }
        }
    }
}



