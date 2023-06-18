package second;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 15/06/2023
 */
public class GoLModel {
    private BufferedImage canvas = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
    private Set<Point> aliveCells = new HashSet<>();
    private Color aliveCellColor = Color.BLACK;
    private Color deadCellColor = Color.WHITE;
    private Color invertedColor = Color.BLACK;
    private final GoLFigures preMadeFigures = new GoLFigures();
    private GoLPrefab currentFigure;
    private Point center;
    private int speed = 10;
    private boolean laufen, malen, setzen;

    public void setCell(Point nextCellPosition, boolean isAlive) {
        if (isAlive) {
            canvas.setRGB(nextCellPosition.x, nextCellPosition.y, aliveCellColor.getRGB());
            aliveCells.add(nextCellPosition);
        } else {
            canvas.setRGB(nextCellPosition.x, nextCellPosition.y, deadCellColor.getRGB());
            aliveCells.remove(nextCellPosition);
        }
    }

    public void setCanvasRGB(Point pos, Color c) {
        canvas.setRGB(pos.x, pos.y, c.getRGB());
    }

    public BufferedImage getCanvas() {
        return canvas;
    }

    public void setCanvas(BufferedImage canvas) {
        this.canvas = canvas;
    }

    public Set<Point> getAliveCells() {
        return aliveCells;
    }

    public void clearAliveCells() {
        aliveCells.clear();
    }

    public Color getAliveCellColor() {
        return aliveCellColor;
    }

    public void setAliveCellColor(Color aliveCellColor) {
        this.aliveCellColor = aliveCellColor;
    }

    public Color getDeadCellColor() {
        return deadCellColor;
    }

    public void setDeadCellColor(Color deadCellColor) {
        this.deadCellColor = deadCellColor;
    }

    public int getCanvasWidth() {
        return canvas.getWidth();
    }

    public int getCanvasHeight() {
        return canvas.getHeight();
    }

    public boolean isCellAlive(Point p) {
        return aliveCells.contains(p);
    }

    public Color getInvertedColor() {
        return invertedColor;
    }

    public void setInvertedColor(Color invertedColor) {
        this.invertedColor = invertedColor;
    }

    public GoLPrefab getCurrentFigure() {
        return currentFigure;
    }

    public void setCurrentFigure(GoLPrefab figure) {
        this.currentFigure = figure;
    }

    public GoLPrefab getPreMadeFigures(int position) {
        return preMadeFigures.getFigure(position);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setLaufen(boolean laufen) {
        this.laufen = laufen;
    }

    public void setMalen(boolean malen) {
        this.malen = malen;
    }

    public void setSetzen(boolean setzen) {
        this.setzen = setzen;
    }

    public boolean isLaufen() {
        return laufen;
    }

    public boolean isMalen() {
        return malen;
    }

    public boolean isSetzen() {
        return setzen;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }
}
