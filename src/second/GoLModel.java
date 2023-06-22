package second;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 22/06/2023
 */

public class GoLModel {
    private int currentWindowNumber;
    private Color aliveCellColor = Color.BLACK;
    private Color deadCellColor = Color.WHITE;
    private Color invertedColor = Color.BLACK;
    private BufferedImage canvas = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
    private final Set<Point> aliveCells = new HashSet<>();

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
     * Überprüft, ob eine Zelle lebendig ist
     *
     * @param p Point
     * @return true wenn Zelle lebendig
     */
    public boolean isCellAlive(Point p) {
        return aliveCells.contains(p);
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
     * Leert die Menge der lebendigen Zellen
     */
    public void clearAliveCells() {
        aliveCells.clear();
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
     * Setzt die Farbe der lebendigen Zellen
     *
     * @param aliveCellColor
     */
    public void setAliveCellColor(Color aliveCellColor) {
        this.aliveCellColor = aliveCellColor;
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
     * Setzt die Farbe der toten Zellen
     *
     * @param deadCellColor
     */
    public void setDeadCellColor(Color deadCellColor) {
        this.deadCellColor = deadCellColor;
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
     * Setzt die invertierte Farbe
     *
     * @param invertedColor
     */
    public void setInvertedColor(Color invertedColor) {
        this.invertedColor = invertedColor;
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
     * Setzt aktuelle Fenster Nummer.
     *
     * @param currentWindowNumber
     */
    public void setCurrentWindowNumber(int currentWindowNumber) {
        this.currentWindowNumber = currentWindowNumber;
    }

    /**
     * Gibt aktuelle Fenster Nummer zurück.
     *
     * @return
     */
    public int getCurrentWindowNumber() {
        return currentWindowNumber;
    }
}
