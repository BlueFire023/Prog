package second;

import java.awt.*;
import java.util.ArrayList;

public class GoLMainModel {
    private final GoLFigures preMadeFigures = new GoLFigures();
    private final ArrayList<GoLPrefab> recentFigures = new ArrayList<>();
    private Point center;
    private int brushSize = 1;

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
     *
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
     * Gibt die vordefinierten Figuren zurück
     *
     * @return
     */
    public GoLFigures getPreMadeFigures() {
        return preMadeFigures;
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

    /**
     * Gibt die Liste der aktuellen Figuren zurück
     *
     * @return
     */
    public ArrayList<GoLPrefab> getRecentFigures() {return recentFigures;}

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
}

