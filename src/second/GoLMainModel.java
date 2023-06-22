package second;

import java.util.ArrayList;

public class GoLMainModel {
    private final GoLFigures preMadeFigures = new GoLFigures();
    private final ArrayList<GoLPrefab> recentFigures = new ArrayList<>();

    public GoLPrefab getCurrentFigure() {
        return recentFigures.get(recentFigures.size() - 1);
    }
    public void setCurrentFigure(GoLPrefab figure) {
        recentFigures.add(figure);
    }
    public GoLPrefab getPreMadeFigures(int position) {
        return preMadeFigures.getFigure(position);
    }
    public GoLFigures getPreMadeFigures() {
        return preMadeFigures;
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
        //recentFigures.removeIf(prefab -> prefab.name().equals(figure.name()));
        recentFigures.add(figure);
    }


}

