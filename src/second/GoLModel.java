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
    private final Set<Point> aliveCells = new HashSet<>();
    private int currentWindowNumber;
    private Color aliveCellColor = Color.BLACK;
    private Color deadCellColor = Color.WHITE;
    private Color invertedColor = Color.BLACK;
    private BufferedImage canvas = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
    private Mode lastMode = Mode.PAINTING;
    private Mode activeMode = Mode.PAINTING;

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
     * Gibt aktuelle Fenster Nummer zurück.
     *
     * @return currentWindowNumber
     */
    public int getCurrentWindowNumber() {
        return currentWindowNumber;
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
     * Gibt den Letzten verwendeten Modus
     *
     * @return lastMode
     */
    public Mode getLastMode() {
        return lastMode;
    }

    /**
     * Setzt den Letzten verwendeten Modus
     *
     * @param lastMode
     */
    public void setLastMode(Mode lastMode) {
        this.lastMode = lastMode;
    }

    /**
     * Gibt den aktiven Modus
     *
     * @return activeMode
     */
    public Mode getActiveMode() {
        return activeMode;
    }

    /**
     * Setzt den aktiven Modus
     *
     * @param activeMode
     */
    public void setActiveMode(Mode activeMode) {
        this.activeMode = activeMode;
    }

    /**
     * Gibt zurück, ob der aktive Modus mit dem übergebenen Modus übereinstimmt
     *
     * @param mode
     * @return true, wenn der aktive Modus mit dem übergebenen Modus übereinstimmt
     */
    public boolean isActiveMode(Mode mode) {
        return activeMode == mode;
    }

    /**
     * Verschiedene Modi
     */
    public enum Mode {
        RUNNING, PAINTING, SET, LINE
    }
}
