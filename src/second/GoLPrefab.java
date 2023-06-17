package second;

import java.awt.*;
import java.util.Set;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 16/06/2023
 */
public class GoLPrefab {
    private String name;
    private Set<Point> cells;

    public GoLPrefab(String name, Set<Point> cells) {
        this.name = name;
        this.cells = cells;
    }

    public String getName() {
        return name;
    }

    public Set<Point> getCells() {
        return cells;
    }
}
