package second;

import java.util.*;

public class Umfülltest {
    static int[] capacity = new int[5];
    static Set<List<Integer>> visited = new HashSet<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Einlesen der Kapazitäten
        for (int i = 0; i < 5; i++) {
            capacity[i] = scanner.nextInt();
        }

        // Startzustand
        List<Integer> start = new ArrayList<>();
        start.add(capacity[0]); // Behälter 1 ist voll
        for (int i = 1; i < 5; i++) {
            start.add(0); // die anderen sind leer
        }

        // Breitensuche
        Queue<List<Integer>> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            List<Integer> state = queue.poll();
            System.out.println(state);

            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (i == j) {
                        continue;
                    }

                    List<Integer> newState = transfer(state, i, j);
                    if (newState != null && !visited.contains(newState)) {
                        queue.add(newState);
                        visited.add(newState);
                    }
                }
            }
        }
    }

    static List<Integer> transfer(List<Integer> state, int from, int to) {
        // Transfer von Behälter "from" zu Behälter "to"
        if (state.get(from) == 0 || state.get(to) == capacity[to]) {
            return null;
        }

        List<Integer> newState = new ArrayList<>(state);
        int amount = Math.min(state.get(from), capacity[to] - state.get(to));
        newState.set(from, state.get(from) - amount);
        newState.set(to, state.get(to) + amount);

        // Anwendung der Umfüllregeln
        switch (from) {
            case 0:
                switch (to) {
                    case 1:
                        break;
                    case 3:
                        break;
                    default:
                        return null;
                }
                break;
            case 1:
                switch (to) {
                    case 2:
                        break;
                    case 4:
                        break;
                    default:
                        return null;
                }
                break;
            case 2:
                switch (to) {
                    case 3:
                        break;
                    default:
                        return null;
                }
                break;
            case 3:
                switch (to) {
                    case 4:
                        break;
                    case 5:
                        break;
                    default:
                        return null;
                }
                break;
            case 4:
                switch (to) {
                    case 2:
                        break;
                    default:
                        return null;
                }
                break;
            case 5:
                switch (to) {
                    case 4:
                        break;
                    default:
                        return null;
                }
                break;
        }

        return newState;
    }

}

