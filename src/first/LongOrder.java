package first;

/**
 * @version 2, 09.11.2022
 * @author Filip Schepers, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Denis Schaffer
 */

import java.util.ArrayList;

public class LongOrder {
    public static void main(String[] agrs) {
        ArrayList<Long> Llist = new ArrayList<>();
        createLongArrayList(Llist);
        returnSorted(Llist);
        for (Long n : Llist) {
            System.out.print(n + " ");
        }
    }

    public static void createLongArrayList(ArrayList<Long> l) {
        String longval = MyIO.promptAndRead("Gib Long ein: ");
        while (longval.length() > 0) {
            l.add(Long.parseLong(longval));
            longval = MyIO.promptAndRead("Gib Long ein: ");
        }
    }

    public static void returnSorted(ArrayList<Long> l) {
        int length = l.size(), i;
        long ram;
        boolean isSorting = true;
        while (true) {
            for (i = 0; i < length - 1; i++) {
                if (l.get(i) > l.get(i + 1)) {
                    ram = l.get(i);
                    l.set(i, l.get(i + 1));
                    l.set(i + 1, ram);
                    isSorting = true;
                }
            }
            if (!isSorting) {
                break;
            }
            isSorting = false;
        }
    }
}

