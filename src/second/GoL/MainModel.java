package second.GoL;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 22/06/2023
 */

public class MainModel {
    private final Figures preMadeFigures = new Figures();
    private final ArrayList<Prefab> recentFigures = new ArrayList<>();
    private int brushSize = 1;
    private Point center;

    /**
     * Gibt die aktuelle Figur zurück
     *
     * @return currentFigure
     */
    public Prefab getCurrentFigure() {
        return recentFigures.get(recentFigures.size() - 1);
    }

    /**
     * Setzt die aktuelle Figur
     *
     * @param figure GoLPrefab
     */
    public void setCurrentFigure(Prefab figure) {
        recentFigures.add(figure);
    }

    /**
     * Gibt eine vordefinierte Figur an einer bestimmten Position zurück
     *
     * @param position
     * @return prefab
     */
    public Prefab getPreMadeFigures(int position) {
        return preMadeFigures.getFigure(position);
    }

    /**
     * Gibt die vordefinierten Figuren zurück
     *
     * @return figures
     */
    public Figures getPreMadeFigures() {
        return preMadeFigures;
    }

    /**
     * Aktualisiert die Liste der aktuellen Figuren mit einer bestimmten Figur basierend auf dem Namen
     *
     * @param name
     */
    public void updateRecentFigures(String name) {
        for (Prefab prefab : recentFigures) {
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
    public void updateRecentFigures(Prefab figure) {
        recentFigures.removeIf(prefab -> prefab.name().equals(figure.name()));
        recentFigures.add(figure);
    }

    /**
     * Gibt die Liste der aktuellen Figuren zurück
     *
     * @return recentFigures
     */
    public ArrayList<Prefab> getRecentFigures() {
        return recentFigures;
    }

    /**
     * Gibt den Mittelpunkt zurück
     *
     * @return Point
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
     * Gibt die Größe des Pinsels zurück
     *
     * @return BrushSize
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
}

