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

    /**
     * Setzt die Farbe der Zelle im Bild "canvas" abhängig davon, ob sie lebendig sind oder nicht
     *
     * @param nextCellPosition
     * @param isAlive
     */
    public void setCell(Point nextCellPosition, boolean isAlive) {
        if (isAlive) {
            canvas.setRGB(nextCellPosition.x, nextCellPosition.y, aliveCellColor.getRGB());
            aliveCells.add(nextCellPosition);
        } else {
            canvas.setRGB(nextCellPosition.x, nextCellPosition.y, deadCellColor.getRGB());
            aliveCells.remove(nextCellPosition);
        }
    }

    /**
     * Setzt die Farbe eines bestimmten Punktes im Bild "canvas"
     *
     * @param pos
     * @param c
     */
    public void setCanvasRGB(Point pos, Color c) {
        canvas.setRGB(pos.x, pos.y, c.getRGB());
    }

    /**
     * Gibt das Bild "canvas" zurück
     *
     * @return BufferedImage - canvas
     */
    public BufferedImage getCanvas() {
        return canvas;
    }

    /**
     * Setzt das Bild "canvas"
     *
     * @param canvas
     */
    public void setCanvas(BufferedImage canvas) {
        this.canvas = canvas;
    }

    /**
     * Gibt die Menge der lebendigen Zellen zurück
     *
     * @return Set an lebendigen Zellen
     */
    public Set<Point> getAliveCells() {
        return aliveCells;
    }

    /**
     * Leert die Menge der lebendigen Zellen
     */
    public void clearAliveCells() {
        aliveCells.clear();
    }

    /**
     * Gibt die Farbe der lebendigen Zellen zurück
     *
     * @return Farbe für lebende Zellen
     */
    public Color getAliveCellColor() {
        return aliveCellColor;
    }

    /**
     * Setzt die Farbe der lebendigen Zellen
     *
     * @param aliveCellColor
     */
    public void setAliveCellColor(Color aliveCellColor) {
        this.aliveCellColor = aliveCellColor;
    }

    /**
     * Gibt die Farbe der toten Zellen zurück
     *
     * @return Farbe für tote Zellen
     */
    public Color getDeadCellColor() {
        return deadCellColor;
    }

    /**
     * Setzt die Farbe der toten Zellen
     *
     * @param deadCellColor
     */
    public void setDeadCellColor(Color deadCellColor) {
        this.deadCellColor = deadCellColor;
    }

    /**
     * Gibt die Breite des Bildes "canvas" zurück
     *
     * @return width
     */
    public int getCanvasWidth() {
        return canvas.getWidth();
    }

    /**
     * Gibt die Höhe des Bildes "canvas" zurück
     *
     * @return height
     */
    public int getCanvasHeight() {
        return canvas.getHeight();
    }

    /**
     * Überprüft, ob eine Zelle lebendig ist
     *
     * @param p Point
     * @return true wenn Zelle lebendig
     */
    public boolean isCellAlive(Point p) {
        return aliveCells.contains(p);
    }

    /**
     * Gibt die invertierte Farbe zurück
     *
     * @return invertedColor
     */
    public Color getInvertedColor() {
        return invertedColor;
    }

    /**
     * Setzt die invertierte Farbe
     *
     * @param invertedColor
     */
    public void setInvertedColor(Color invertedColor) {
        this.invertedColor = invertedColor;
    }

    /**
     * Gibt die aktuelle Figur zurück
     *
     * @return currentFigure
     */
    public GoLPrefab getCurrentFigure() {
        return recentFigures.get(recentFigures.size() - 1);
    }

    /**
     * Setzt die aktuelle Figur
     * @param figure GoLPrefab
     */
    public void setCurrentFigure(GoLPrefab figure) {
        recentFigures.add(figure);
    }

    /**
     * Gibt eine vordefinierte Figur an einer bestimmten Position zurück
     *
     * @param position
     * @return
     */
    public GoLPrefab getPreMadeFigures(int position) {
        return preMadeFigures.getFigure(position);
    }

    /**
     * Gibt den Mittelpunkt zurück
     *
     * @return
     */
    public Point getCenter() {
        return center;
    }

    /**
     * Setzt den Mittelpunkt
     *
     * @param center
     */
    public void setCenter(Point center) {
        this.center = center;
    }

    /**
     * Gibt die vordefinierten Figuren zurück
     *
     * @return
     */
    public GoLFigures getPreMadeFigures() {
        return preMadeFigures;
    }

    /**
     * Gibt die Größe des Pinsels zurück
     *
     * @return
     */
    public int getBrushSize() {
        return brushSize;
    }

    /**
     * Setzt die Größe des Pinsels
     *
     * @param brushSize
     */
    public void setBrushSize(int brushSize) {
        this.brushSize = brushSize;
    }

    /**
     * Gibt die Liste der aktuellen Figuren zurück
     *
     * @return
     */
    public ArrayList<GoLPrefab> getRecentFigures() {
        return recentFigures;
    }

    /**
     * Aktualisiert die Liste der aktuellen Figuren mit einer bestimmten Figur basierend auf dem Namen
     *
     * @param name
     */
    public void updateRecentFigures(String name) {
        for (GoLPrefab prefab : recentFigures) {
            if (prefab.name().equals(name)) {
                recentFigures.remove(prefab);
                recentFigures.add(prefab);
                break;
            }
        }
    }

    /**
     * Aktualisiert die Liste der aktuellen Figuren mit einer bestimmten Figur
     *
     * @param figure
     */
    public void updateRecentFigures(GoLPrefab figure) {
        recentFigures.removeIf(prefab -> prefab.name().equals(figure.name()));
        recentFigures.add(figure);
    }
}
