package first;

/**
 * @author Denis Schaffer
 * @version 1.0, 31.10.2022
 */

public class Triangle {
    public static void main(String[] args) {
        int n = MyIO.readInt("Gibe Zeilen menge: ");
        buildTriangle(n);
    }

    public static void buildTriangle(int n) {
        int ng = n;
        while (n != 0) {
            for (int i = 0; i < ng - n; i++) {
                System.out.print(" ");
            }
            for (int j = 0; j < n; j++) {
                System.out.print("* ");
            }
            n--;
            //System.out.println();
        }
    }
}
