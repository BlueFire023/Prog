package second;

import first.*;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 13.04.2023
 */
public class MaXx {
    private static GameBoard b;
    public static Player[] p;
    private static final int PLAYERCOUNT = 2;
    private static final int BOARDSIZE = 8;
    private static final double SCORESTOWIN = 53d;
    private static int currentPlayerIndex;
    private static int longestNameLength = 0;

    public static void main(String[] args) {
        System.out.println("""
                \nWillkommen zu MaXx, bewegen sie sich auf dem Spielfeld um Brüche zu addieren und am Ende als Sieger hervorzugehen!
                Der erste Spieler, der mehr als 53 addiert hat, ist der Gewinner.
                Spieler 1 ist jetzt der weiße Spieler(W)
                Spieler 2 ist jetzt der schwarze Spieler(B)
                Das Spiel kann mit "Stop", "End" oder "Ende" beendet werden.""");

        b = new GameBoard(BOARDSIZE);
        p = new Player[PLAYERCOUNT];
        p[0] = new Player("Weiß", "W", new String[]{"N", "O", "S", "W", "NO"}, b);
        p[1] = new Player("Schwarz", "B", new String[]{"N", "O", "S", "W", "SW"}, b);
        for (Player player : p) {
            if (longestNameLength < player.getName().length()) {
                longestNameLength = player.getName().length();
            }
        }
        do {
            initNewGame();
            b.printBoard();
            System.out.println(p[currentPlayerIndex].getName() + " darf anfangen!\n");
            do {
                while (true) {
                    try {
                        String instruction = MyIO.promptAndRead("Bitte gib eine Himmelsrichtung an(N,O,S,W,NO(Weiß),SW(Schwarz)): ").trim().toUpperCase();
                        b.setValue(p[currentPlayerIndex].getXPosition(), p[currentPlayerIndex].getYPosition(), "0");
                        switch (instruction) {
                            case "N", "O", "S", "W", "NW", "NO", "SW", "SO" -> p[currentPlayerIndex].move(instruction);
                            case "STOP", "ENDE", "END" -> System.exit(0);
                            case "R", "RESTART", "RELOAD" -> initNewGame();
                            default -> throw new Exception("Keine mögliche Eingabe!");
                        }
                        b.printBoard();
                        for (Player player : p) {
                            System.out.print(player.getName() + ":");
                            for (int i = 0; i < longestNameLength - player.getName().length(); i++) {
                                System.out.print(" ");
                            }
                            System.out.print(" (" + player.getScore().doubleValue() + ") " + player.getScore() + "\n");
                        }
                        break;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("\nUnmögliche Bewegung!\n");
                    } catch (Exception e) {
                        System.out.println("\n" + e.getMessage() + "\n");
                    }
                }
                if (p[currentPlayerIndex].getScore().doubleValue() > SCORESTOWIN) {
                    break;
                } else {
                    currentPlayerIndex++;
                }
                if (currentPlayerIndex == PLAYERCOUNT) {
                    currentPlayerIndex = 0;
                }
                System.out.println("\n" + p[currentPlayerIndex].getName() + " ist dran!\n");
            } while (true);
            p[currentPlayerIndex].increaseWin();
            System.out.println("\n" + p[currentPlayerIndex].getName() + " hat Gewonnen!\n");
            for (Player player : p) {
                System.out.print(player.getName() + ":");
                for (int i = 0; i < longestNameLength - player.getName().length(); i++) {
                    System.out.print(" ");
                }
                System.out.print(" " + player.getWinCounter() + "\n");
            }
            System.out.println("\nNochmal? y/n");
        } while (MyIO.promptAndReadBoolean(""));
    }

    private static void initNewGame() {
        b.fillBoard();
        boolean repeat;
        do {
            repeat = false;
            for (Player player : p) {
                player.initPosValue((MyMath.rand(0, b.getSize() - 1)), (MyMath.rand(0, b.getSize() - 1)));
            }
            for (int i = 0; i < PLAYERCOUNT - 1; i++) {
                if (p[i].getXPosition() == p[i + 1].getXPosition() && p[i].getYPosition() == p[i + 1].getYPosition()) {
                    repeat = true;
                    break;
                }
            }
        } while (repeat);
        for (Player player : p) {
            player.initPosition();
            player.setScore(new Fraction(0,1));
        }
        currentPlayerIndex = MyMath.rand(0, PLAYERCOUNT - 1);
    }
}