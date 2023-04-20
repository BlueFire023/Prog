package first;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 19.12.22
 */
public class Player {
    private Fraction score;
    private String symbol;
    private final String name;
    private final ArrayList<String> moveSet;
    private final GameBoard board;
    private int winCounter;
    private int xPosition;
    private int yPosition;

    public Player(String name, String symbol, String[] moveSet, GameBoard board) {
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
    public String getSymbol(){
        return symbol;
    }
    public void increaseWin() {
        this.winCounter++;
    }

    public void setPosition(int xOffset, int yOffset) {
        this.xPosition += xOffset;
        this.yPosition += yOffset;
        board.setValue(this.xPosition, this.yPosition, this.symbol);
    }

    public void initPosValue(int xValue, int yValue) {
        this.xPosition = xValue;
        this.yPosition = yValue;
    }

    public void setScore(Fraction score) {
        this.score = score;
    }

    public void initPosition() {
        board.setValue(this.xPosition, this.yPosition, this.symbol);
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public Fraction getScore() {
        return score;
    }

    public int getWinCounter() {
        return winCounter;
    }

    public String getName() {
        return name;
    }

    public boolean isInMoveSet(String direction) {
        return this.moveSet.contains(direction);
    }

    public void move(String direction) throws Exception {
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
