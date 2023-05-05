package second;

import java.util.*;

public class umfuellen {
    public static void main(String[] args) {
        int[] capacities = {3, 2, 3, 2, 3};
        int[] initial = {capacities[0], 0, 0, 0, 0};
        List<int[]> states = getAllStates(capacities, initial);
        for (int[] state : states) {
            System.out.println(Arrays.toString(state));
        }
        System.out.println(states.size() + " states");
    }

    public static List<int[]> getAllStates(int[] capacities, int[] initial) {
        Set<String> visited = new HashSet<>();
        List<int[]> result = new ArrayList<>();
        dfs(capacities, initial, visited, result);
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

    private static void dfs(int[] capacities, int[] current, Set<String> visited, List<int[]> result) {
        String state = Arrays.toString(current);
        if (visited.contains(state)) {
            return;
        }
        visited.add(state);
        result.add(current.clone());
        for (int i = 0; i < capacities.length; i++) {
            for (int j = 0; j < capacities.length; j++) {
                if (i != j) {
                    int amount = Math.min(current[i], capacities[j] - current[j]);
                    if (amount > 0) {
                        current[i] -= amount;
                        current[j] += amount;
                        dfs(capacities, current, visited, result);
                        current[i] += amount;
                        current[j] -= amount;
                    }
                }
            }
        }
    }

}
