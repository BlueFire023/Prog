package first;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 19.12.22
 */
public class Player implements Serializable {
    private Fraction score; //Punktestand
    private String symbol; //Abkürzendes Symbol
    private Color color; //Farbe des Spielers
    private final String name; //Ausgeschriebener Name des Spielers
    private final ArrayList<String> moveSet; //Liste der Bewegungsrichtungen, die getätigt werden dürfen
    private final GameBoard board; //Zugehöriges Gameboard auf dem sich der Spieler später bewegt
    private int winCounter; //Zähler der Siege
    private int xPosition; //XPosition auf dem Feld
    private int yPosition; //YPosition auf dem Feld


    public Player(String name, String symbol, String[] moveSet, GameBoard board) {
        //Setzen der Instanzvariabeln (ohne Farbe)
        this.name = name;
        if (symbol.length() < 8) {
            this.symbol = symbol;
        } else {
            for (int i = 0; i < MaXx.p.length; i++) {
                if (MaXx.p[i] == null) {
                    this.symbol = "P" + (i + 1);
                    break;
                }
            }
            System.out.println("\nSymbol darf nicht länger als 7 Zeichen sein!");
        }
        this.moveSet = new ArrayList<>(Arrays.asList(moveSet));
        this.board = board;

    }
    public Player(String name, String symbol, String[] moveSet, GameBoard board, Color color) {
        //Setzen der Instanzvariabeln (mit Farbe)
        this.name = name;
        if (symbol.length() < 8) {
            this.symbol = symbol;
        } else {
            for (int i = 0; i < MaXx.p.length; i++) {
                if (MaXx.p[i] == null) {
                    this.symbol = "P" + (i + 1);
                    break;
                }
            }
            System.out.println("\nSymbol darf nicht länger als 7 Zeichen sein!");
        }
        this.color = color;
        this.moveSet = new ArrayList<>(Arrays.asList(moveSet));
        this.board = board;
    }
    public String getSymbol(){
        return symbol;
    } //Rückgabe des Symbols
    public Color getColor(){
        return color;
    } //Rückgabe der Farbe
    public void increaseWin() {
        this.winCounter++;
    } //Erhöhung der Siege

    public void setPosition(int xOffset, int yOffset) { // Erhöhung der Positionswerte
        this.xPosition += xOffset;
        this.yPosition += yOffset;
        board.setValue(this.xPosition, this.yPosition, this.symbol);
    }

    public void initPosValue(int xValue, int yValue) { //Setzen neuer Positionen
        this.xPosition = xValue;
        this.yPosition = yValue;
    }

    public void setScore(Fraction score) {
        this.score = score;
    } //Setzen des Scores

    public void initPosition() {
        board.setValue(this.xPosition, this.yPosition, this.symbol);
    }//Setzen des Symbols auf bestimmter Position

    public int getXPosition() {
        return xPosition;
    } //Rückgabe der XPosition

    public int getYPosition() {
        return yPosition;
    } //Rückgabe der YPosition

    public Fraction getScore() {
        return score;
    } //Rückgabe des Scores

    public int getWinCounter() {
        return winCounter;
    } //Rückgabe des Sieges Counter

    public String getName() {
        return name;
    } //Rückgabe des Namens

    public boolean isInMoveSet(String direction) {
        return this.moveSet.contains(direction);
    } //Abfrage, ob Bewegung erlaubt ist

    public void move(String direction) throws Exception {
        //Bewegung des Spielers mit Erhöhung des Scores und Änderung der Position
        if (!isInMoveSet(direction)) {
            throw new Exception("Nicht im Moveset enthalten!");
        } else {
            switch (direction) {
                case "N" -> {
                    this.score = this.score.add(board.getValue(this.xPosition, this.yPosition - 1));
                    setPosition(0, -1);
                }
                case "O" -> {
                    this.score = this.score.add(board.getValue(this.xPosition + 1, this.yPosition));
                    setPosition(1, 0);
                }
                case "S" -> {
                    this.score = this.score.add(board.getValue(this.xPosition, this.yPosition + 1));
                    setPosition(0, 1);
                }
                case "W" -> {
                    this.score = this.score.add(board.getValue(this.xPosition - 1, this.yPosition));
                    setPosition(-1, 0);
                }
                case "NO" -> {
                    this.score = this.score.add(board.getValue(this.xPosition + 1, this.yPosition - 1));
                    setPosition(1, -1);
                }
                case "NW" -> {
                    this.score = this.score.add(board.getValue(this.xPosition - 1, this.yPosition - 1));
                    setPosition(-1, -1);
                }
                case "SO" -> {
                    this.score = this.score.add(board.getValue(this.xPosition + 1, this.yPosition + 1));
                    setPosition(1, 1);
                }
                case "SW" -> {
                    this.score = this.score.add(board.getValue(this.xPosition - 1, this.yPosition + 1));
                    setPosition(-1, 1);
                }
            }
        }
    }
}
