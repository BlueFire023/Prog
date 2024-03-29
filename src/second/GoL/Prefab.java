package second.GoL;

import java.awt.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 16/06/2023
 */

public record Prefab(String name, Set<Point> cells) implements Serializable {
}
