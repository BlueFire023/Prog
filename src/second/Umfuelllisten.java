package second;
/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 5/05/2023
 */

import java.util.*;

public class Umfuelllisten {
    static int[] kapazität = new int[5]; //Größen der Eimer

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Einlesen der Kapazitäten
        for (int i = 0; i < 5; i++) {
            kapazität[i] = scanner.nextInt();
        }
        int[] initial = {kapazität[0], 0, 0, 0, 0}; //Startzustand
        List<int[]> states = getAllStates(kapazität, initial);
        for (int[] state : states) {
            System.out.println(Arrays.toString(state));
        }
        System.out.println(states.size() + " states");
    }

    //Errechnen aller Zustände
    public static List<int[]> getAllStates(int[] capacities, int[] initial) {
        Set<String> visited = new HashSet<>(); //Set mit bereits genutzten Zuständen
        List<int[]> result = new ArrayList<>();
        umfüllen(capacities, initial, visited, result);
        //Sortieren der Liste
        Collections.sort(result, (a, b) -> {
            for (int i = 0; i < a.length; i++) {
                if (a[i] != b[i]) {
                    return a[i] - b[i];
                }
            }
            return 0;
        });
        return result;
    }

    //Umfüllen der Eimer mithilfe der Regeln
    private static void umfüllen(int[] capacities, int[] current, Set<String> visited, List<int[]> result) {
        String state = Arrays.toString(current);
        if (visited.contains(state)) {
            return;
        }
        visited.add(state);
        result.add(current.clone());
        for (int i = 0; i < capacities.length; i++) {
            for (int j = 0; j < capacities.length; j++) {
                if (i != j) {
                    // Bedingungen für die Behälterumfüllung
                    if ((i == 0 && j == 1) || (i == 0 && j == 3) || (i == 1 && j == 2) ||
                            (i == 2 && j == 4) || (i == 3 && j == 1) || (i == 4 && j == 3)) {
                        int amount = Math.min(current[i], capacities[j] - current[j]);
                        if (amount > 0) {
                            current[i] -= amount;
                            current[j] += amount;
                            umfüllen(capacities, current, visited, result);
                            current[i] += amount;
                            current[j] -= amount;
                        }
                    }
                }
            }
        }
    }
}
