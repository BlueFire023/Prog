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

    public void setCell(Point nextCellPosition, boolean isAlive) {
        if (isAlive) {
            canvas.setRGB(nextCellPosition.x, nextCellPosition.y, aliveCellColor.getRGB());
            aliveCells.add(nextCellPosition);
        } else {
            canvas.setRGB(nextCellPosition.x, nextCellPosition.y, deadCellColor.getRGB());
            aliveCells.remove(nextCellPosition);
        }
    }

    public void setCanvasRGB(int x, int y, Color c) {
        canvas.setRGB(x, y, c.getRGB());
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

    public void setAliveCells(Set<Point> aliveCells) {
        this.aliveCells = aliveCells;
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
}
