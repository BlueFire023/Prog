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

    public void setCell(Point nextCellPosition, boolean isAlive) { // Setzt die Farbe der Zelle im Bild "canvas" abhängig davon, ob sie lebendig sind oder nicht
        if (isAlive) {
            canvas.setRGB(nextCellPosition.x, nextCellPosition.y, aliveCellColor.getRGB());
            aliveCells.add(nextCellPosition);
        } else {
            canvas.setRGB(nextCellPosition.x, nextCellPosition.y, deadCellColor.getRGB());
            aliveCells.remove(nextCellPosition);
        }
    }

    public void setCanvasRGB(Point pos, Color c) { // Setzt die Farbe eines bestimmten Punktes im Bild "canvas"
        canvas.setRGB(pos.x, pos.y, c.getRGB());
    }

    public BufferedImage getCanvas() { // Gibt das Bild "canvas" zurück
        return canvas;
    }

    public void setCanvas(BufferedImage canvas) { // Setzt das Bild "canvas"
        this.canvas = canvas;
    }

    public Set<Point> getAliveCells() { // Gibt die Menge der lebendigen Zellen zurück
        return aliveCells;
    }

    public void clearAliveCells() { // Leert die Menge der lebendigen Zellen
        aliveCells.clear();
    }

    public Color getAliveCellColor() { // Gibt die Farbe der lebendigen Zellen zurück
        return aliveCellColor;
    }

    public void setAliveCellColor(Color aliveCellColor) { // Setzt die Farbe der lebendigen Zellen
        this.aliveCellColor = aliveCellColor;
    }

    public Color getDeadCellColor() { // Gibt die Farbe der toten Zellen zurück
        return deadCellColor;
    }

    public void setDeadCellColor(Color deadCellColor) { // Setzt die Farbe der toten Zellen
        this.deadCellColor = deadCellColor;
    }

    public int getCanvasWidth() { // Gibt die Breite des Bildes "canvas" zurück
        return canvas.getWidth();
    }

    public int getCanvasHeight() { // Gibt die Höhe des Bildes "canvas" zurück
        return canvas.getHeight();
    }

    public boolean isCellAlive(Point p) { // Überprüft, ob eine Zelle lebendig ist
        return aliveCells.contains(p);
    }

    public Color getInvertedColor() { // Gibt die invertierte Farbe zurück
        return invertedColor;
    }

    public void setInvertedColor(Color invertedColor) { // Setzt die invertierte Farbe
        this.invertedColor = invertedColor;
    }

    public GoLPrefab getCurrentFigure() {  // Gibt die aktuelle Figur zurück
        return recentFigures.get(recentFigures.size() - 1);
    }

    public void setCurrentFigure(GoLPrefab figure) { // Setzt die aktuelle Figur
        recentFigures.add(figure);
    }

    public GoLPrefab getPreMadeFigures(int position) {  // Gibt eine vordefinierte Figur an einer bestimmten Position zurück
        return preMadeFigures.getFigure(position);
    }

    public Point getCenter() { // Gibt den Mittelpunkt zurück
        return center;
    }

    public void setCenter(Point center) { // Setzt den Mittelpunkt
        this.center = center;
    }

    public GoLFigures getPreMadeFigures() { // Gibt die vordefinierten Figuren zurück
        return preMadeFigures;
    }

    public int getBrushSize() { // Gibt die Größe des Pinsels zurück
        return brushSize;
    }

    public void setBrushSize(int brushSize) { // Setzt die Größe des Pinsels
        this.brushSize = brushSize;
    }

    public ArrayList<GoLPrefab> getRecentFigures() {  // Gibt die Liste der aktuellen Figuren zurück
        return recentFigures;
    }

    public void updateRecentFigures(String name) { // Aktualisiert die Liste der aktuellen Figuren mit einer bestimmten Figur basierend auf dem Namen
        for (GoLPrefab prefab : recentFigures) {
            if (prefab.name().equals(name)) {
                recentFigures.remove(prefab);
                recentFigures.add(prefab);
                break;
            }
        }
    }

    public void updateRecentFigures(GoLPrefab figure) { // Aktualisiert die Liste der aktuellen Figuren mit einer bestimmten Figur
        recentFigures.removeIf(prefab -> prefab.name().equals(figure.name()));
        recentFigures.add(figure);
    }
}
