package first;

/**
 * @author Filip Schepers, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Denis Schaffer
 * @version 1, 05.12.2022
 **/

public class Cosima {
    public static void main(String[] args) {
        int n;
        do {
            n = MyIO.readInt("Gib n: ");
            if (n > 0) {
                for (int i = 0; i <= n; i++) {
                    if (i % 2 == 0 && i % 3 == 0 && i % 5 == 0) {
                        System.out.print("Cyber ");
                    } else if ((i % 2 == 0 && i % 3 == 0) || (i % 3 == 0 && i % 5 == 0) || (i % 5 == 0 && i % 2 == 0)) {
                        System.out.print("Secans ");
                    } else if (i % 5 == 0) {
                        System.out.print("Math ");
                    } else if (i % 3 == 0) {
                        System.out.print("Sin ");
                    } else if (i % 2 == 0) {
                        System.out.print("Cos ");
                    } else {
                        System.out.print(i + " ");
                    }
                }
                break;
            } else {
                System.out.println("N muss größer Null sein(N > 0)");
            }
        }while(true);
    }
}
