package first;

/**
 * @version 2, 17.11.2022
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 */

public class Schnecke {
    public static void main(String[] args) {
        final int MAX_N = 45, MIN_N = 0;
        int n = Math.max(MIN_N, Math.min(MAX_N, MyIO.readInt("Gib Größe (cappedMin: " + MIN_N + " cappedMax: " + MAX_N + "): ")));
        String name = MyIO.promptAndRead("Gib deinen Namen: ");
        String[][] snail = createSnailMatrix(name, n);
        printMatrix(snail);
        /*for (int i = MIN_N; i <= MAX_N; i++) {
         *String[][] snail = createSnailMatrix(name, i);
         * printMatrix(snail);
        }*/
    }

    public static String[][] createSnailMatrix(String name, int size) {
        String[][] matrix = new String[size][size];                        // matrix[zeile][Spalte] == matrix[m][n]

        int m = size - 1, n = size - 1, offset = 0, i = 0, charsPlaced = 0, z = 1, snailLength = size;
        boolean topRightC, botLeftC, topLeftC, botRightC;
        boolean moveUp = true, moveDown = false, moveRight = false, moveLeft = false;

        //Code für die Abbruchbedingung
        do {
            for (int j = 0; j < 1; j++) {
                snailLength += ((size) - z) * 2;
                //System.out.println("cha: " + charLength + " z: "+ z);
            }
            z += 2;
        } while (z < size);
        if (size % 2 == 0) {
            snailLength -= 1;
        }
        size -= 1;

        //Code um die Matrix zu füllen
        while (snailLength > charsPlaced) {
            //printSnail(matrix);
            if (i == name.length()) {
                matrix[m][n] = "_";
                i = 0;
            } else if (getNextChar(name, i).equals(" ")) {
                matrix[m][n] = "_";
                i++;
            } else {
                matrix[m][n] = getNextChar(name, i);
                i++;
            }

            topRightC = (n == size - offset && m == offset);

            topLeftC = (n == offset && m == offset);

            botRightC = (n == size - offset && m == size - offset + 2);

            botLeftC = (n == offset && m == size - offset);

            if (topRightC) {
                moveLeft = true;
                moveUp = false;
                //System.out.println("trc");
            }
            if (topLeftC) {
                moveDown = true;
                moveLeft = false;
                //System.out.println("tlc");
            }
            if (botRightC) {
                moveUp = true;
                moveRight = false;
                //System.out.println("brc");
            }
            if (botLeftC) {
                moveRight = true;
                moveDown = false;
                offset += 2;
                //System.out.println("blc");
            }

            n = moveLeft ? --n : n;

            m = moveDown ? ++m : m;

            m = moveUp ? --m : m;

            n = moveRight ? ++n : n;

            charsPlaced++;
            //System.out.println("m: " + m + " n: " + n + " charsPlaced: " + charsPlaced);
        }
        return matrix;
    }

    public static String getNextChar(String n, int i) {
        return n.substring(i, i + 1);
    }

    public static void printMatrix(String[][] matrix) {
        int m = matrix.length;
        if (m == 0) {
            System.out.println("Size: " + m);
            System.out.println("[]");
            return;
        }
        System.out.println();
        System.out.println("Size: " + m);
        for (int i = 0; i < m; i++) {
            int n = matrix[i].length;
            System.out.print("[ ");
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == null) {
                    matrix[i][j] = " ";
                }
                if (j == matrix.length - 1) {
                    System.out.print(matrix[i][j]);
                } else {
                    System.out.print(matrix[i][j] + " ");
                }
            }
            System.out.println(" ]");
        }
    }
}
