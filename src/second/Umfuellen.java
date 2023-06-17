package second;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Umfuellen {
    public static int[] kapazität = {3,2,3,2,3};
    public static ArrayList<Integer> eimer = new ArrayList<>();
    public static ArrayList<Integer> speicher = new ArrayList<>();
    public static Set<ArrayList<Integer>> log = new HashSet<>();
    public static boolean test = true;

    public static void main(String[] args)
    {
        eimer.add(kapazität[0]);
        eimer.add(0);
        eimer.add(0);
        eimer.add(0);
        eimer.add(0);

        speicher = eimer;
        log.add(speicher);

        /**
        while (test) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (i == j) {
                        continue;
                    }

                    ArrayList<Integer> newState = umkippen(eimer, i, j);
                    if (newState != null) {
                        eimer = newState;
                        log.add(eimer);
                    }
                }
            }
            test = false;
        }
         */
        eimer = umkippen(eimer,1,2);
        log.add(eimer);

        eimer = umkippen(eimer, 1,4);
        log.add(eimer);


        System.out.println(log.toString());

    }

    public static ArrayList<Integer> umkippen(ArrayList<Integer> start, int from, int to)
    {
        ArrayList<Integer> newState = new ArrayList<>(start);

        switch (from) {
            case 1:
                switch (to) {
                    case 2:
                        for(int i = 0; i< start.get(0); i++)
                        {
                            if(start.get(0)==0 || newState.get(1)==kapazität[1])
                            {
                                break;
                            }else {
                                newState.set(0, newState.get(0)-1);
                                start.set(0, start.get(0)-1);
                                newState.set(1, newState.get(1)+1);
                            }
                        }
                        break;
                    case 4:
                        for(int i = 0; i< start.get(0); i++)
                        {
                            if(start.get(0)==0 || newState.get(3)==kapazität[3])
                            {
                                break;
                            }else {
                                newState.set(0, newState.get(0)-1);
                                start.set(0, start.get(0)-1);
                                newState.set(3, newState.get(3)+1);
                            }
                        }
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
