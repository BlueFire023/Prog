package second;

import first.MyMath;

import java.util.*;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 04/05/2023
 */
public class Umfuelllisten {
    private static final int[] capacity = {3, 2, 3, 2, 3};
    private static final List<Integer>[] containers = new ArrayList[5];
    private static final Set<String> setToSort = new HashSet<>();
    private static List<String> listToSort;
    private static int listLength = 0;

    public static void main(String[] args) {
        for (int i = 0; i < containers.length; i++) {
            containers[i] = new ArrayList<>();
        }
        setStartingValue();
        transferWater();
        translateList();
        printSortedList();
    }

    private static void translateList() {
        for (int i = 0; i < listLength; i++) {
            String intAsString = "";
            for (List<Integer> l : containers) {
                intAsString = intAsString.concat(String.valueOf(l.get(i)));
            }
            setToSort.add(intAsString);
        }
        listToSort = new ArrayList<>(setToSort);
        Collections.sort(listToSort);
    }

    private static void transferWater() {
        boolean run = true;
        int i =0;
        int current, next;
        while (run) {
            run = false;
            current = 0;
            next = 1;
            run = transferIfPossible(run, current, next);
            next = 3;
            run = transferIfPossible(run, current, next);
            current = 1;
            next = 2;
            run = transferIfPossible(run, current, next);
            current = 2;
            next = 4;
            run = transferIfPossible(run, current, next);
            current = 3;
            next = 1;
            run = transferIfPossible(run, current, next);
            if (run) {
                listLength++;
            }
            i++;
        }
    }

    private static boolean transferIfPossible(boolean run, int current, int next) {
        if (containers[current].get(listLength - 1) > 0 && containers[next].get(listLength - 1) < capacity[next]) {
            run = true;
            containers[next].add(Math.min(containers[current].get(listLength - 1), capacity[next] - containers[next].get(listLength - 1)));
            containers[current].add(MyMath.clampMin(containers[current].get(listLength - 1) - (capacity[next] - containers[next].get(listLength - 1)), 0));
            fillNotChanged(current, next);
        }
        return run;
    }

    private static void fillNotChanged(int aChanged, int bChanged) {
        for (int i = 0; i < containers.length; i++) {
            if (i != aChanged && i != bChanged) {
                containers[i].add(containers[i].get(listLength - 1));
            }
        }
    }

    private static void setStartingValue() {
        containers[0].add(capacity[0]);
        for (int i = 1; i < containers.length; i++) {
            containers[i].add(0);
        }
        listLength++;
    }

    private static void printSortedList() {
        int i;
        for (i = 0; i < listToSort.toArray().length; i++) {
            System.out.println(listToSort.get(i));
        }
        System.out.println(i);
    }
}
