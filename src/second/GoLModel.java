package second;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 15/06/2023
 */
public class GoLModel {
    private final GoLFigures preMadeFigures = new GoLFigures();
    private BufferedImage canvas = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
    private final Set<Point> aliveCells = new HashSet<>();
    private Color aliveCellColor = Color.BLACK;
    private Color deadCellColor = Color.WHITE;
    private Color invertedColor = Color.BLACK;
    private final ArrayList<GoLPrefab> recentFigures = new ArrayList<>();
    private Point center;
    private int brushSize = 1;


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
        return recentFigures.get(recentFigures.size() - 1);
    }

    public void setCurrentFigure(GoLPrefab figure) {
        recentFigures.add(figure);
    }

    public GoLPrefab getPreMadeFigures(int position) {
        return preMadeFigures.getFigure(position);
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public GoLFigures getPreMadeFigures() {
        return preMadeFigures;
    }

    public int getBrushSize() {
        return brushSize;
    }

    public void setBrushSize(int brushSize) {
        this.brushSize = brushSize;
    }

    public ArrayList<GoLPrefab> getRecentFigures() {
        return recentFigures;
    }

    public void updateRecentFigures(String name) {
        for (GoLPrefab prefab : recentFigures) {
            if (prefab.name().equals(name)) {
                recentFigures.remove(prefab);
                recentFigures.add(prefab);
                break;
            }
        }
    }

    public void updateRecentFigures(GoLPrefab figure) {
        recentFigures.removeIf(prefab -> prefab.name().equals(figure.name()));
        recentFigures.add(figure);
    }
}
