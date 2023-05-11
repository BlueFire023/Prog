package first;

import java.io.Serializable;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 19.12.2022
 */
public class GameBoard implements Serializable {
    private String[][] board; //Spielfeld als 2D String Array
    private int size; // Größe des Spielfeldes (immer quadratisch)
    private final int MINVALUE = 10; //Min Wert, damit weder Numerator noch der Denominator darunter fallen
    private final int MAXVALUE = 999; //Max Wert, damit weder Numerator noch der Denominator darüber fallen

    public GameBoard(int size) {
        //Gameboard wird erstellt
        this.size = size;
        board = new String[this.size][this.size];
    }

    public void fillBoard() {
        //Füllen des Boards mit zufälligen Werten
        int r;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Fraction f;
                do {
                    r = MyMath.rand(MINVALUE, MAXVALUE - 1);
                    f = new Fraction(MyMath.rand(r + 1, MAXVALUE), r);
                } while ((f.getNumerator().doubleValue() < 10d) || (f.getDenominator().doubleValue() < 10d) || (f.getNumerator().doubleValue() < f.getDenominator().doubleValue()));
                board[i][j] = f.toString();
            }
        }
    }

    public void printBoard() {
        //Ausgeben des StringArrays(Gameboards auf der Konsole)
        System.out.println();
        for (String[] strings : board) {
            System.out.print("[ ");
            for (String string : strings) {
                switch (string.length()) {
                    case 1 -> System.out.print("   " + string + "    ");
                    case 2 -> System.out.print("  " + string + "    ");
                    case 3 -> System.out.print("  " + string + "   ");
                    case 4 -> System.out.print(" " + string + "   ");
                    case 5 -> System.out.print(" " + string + "  ");
                    case 6 -> System.out.print(string + "  ");
                    case 7 -> System.out.print(string + " ");
                    default -> System.out.print("_______ ");
                }
            }
            System.out.println("\b ]");
        }
        System.out.println();
    }

    public Fraction getValue(int x, int y) throws Exception {
        //Abfrage eines bestimmten Wertes im Feld, Rückgabe als Fraction und nicht als String
        String s = board[y][x].trim();
        if (s.equals("0")) {
            return Fraction.NaN;
        } else if (s.contains("/")) {
            int slashIndex = s.indexOf("/");
            return new Fraction((s.substring(0, slashIndex)), (s.substring(slashIndex + 1)));
        } else {
            throw new Exception("Du kannst dich nicht in einen anderen Spieler bewegen!");
        }
    }

    public int getSize() {
        return size;
    } //Rückgabe der Größe

    public void setValue(int x, int y, String n) {
        board[y][x] = n;
    } //Setzen eines Wertes an einer bestimmten Position im Feld
}
