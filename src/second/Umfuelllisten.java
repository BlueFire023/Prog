package second;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 04/05/2023
 */
public class Umfuelllisten {
    private static int[] capacity = {3, 2, 3, 2, 3};
    private static List<Integer>[] containers = new ArrayList[5];
    private static List<Long> listToSort = new ArrayList<>();
    private static int listLength = 50;

    public static void main(String[] args) {
        for (int i = 0; i < containers.length; i++) {
            containers[i] = new ArrayList<>();
        }
        for (int r = 0; r < 5; r++) {
            for (int i = 0; i < containers.length; i++) {
                for (int j = 0; j < 25; j++) {
                    containers[i].add(i * j);
                }
            }
        }
        translateList();
        printSortedList();
    }

    private static void translateList() {
        for (int i = 0; i < listLength; i++) {
            String intAsString = "";
            for (List<Integer> l : containers) {
                intAsString = intAsString.concat(String.valueOf(l.get(i)));
            }
            listToSort.add(Long.valueOf(intAsString));
        }
        Collections.sort(listToSort);
    }

    private static void printSortedList() {
        for (int i = 0; i < listLength; i++) {
            System.out.println(listToSort.get(i));
        }
    }
}
