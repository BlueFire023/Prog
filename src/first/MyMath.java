package first;

/**
 * @author Denis Schaffer
 * @version 1.3, 31,10,2022
 **/

public class MyMath {
    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21};
        long[] b = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21};
        float[] e = {1.1f, 2.2f, 3.3f, 4.4f, 5.5f, 6.6f, 7.7f, 8.8f, 9.9f, 10.1f, 11.11f, 12.12f, 13.13f, 14.14f, 15.15f, 16.16f, 17.17f, 18.18f, 19.19f, 20.2f, 21.21f};
        double[] c = {1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 7.7, 8.8, 9.9, 10.1, 11.11, 12.12, 13.13, 14.14, 15.15, 16.16, 17.17, 18.18, 19.19, 20.2, 21.21};
        String[] d = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21"};
        System.out.println(addAll(a));
        System.out.println(addAll(b));
        System.out.println(addAll(c));
        System.out.println(addAll(d));
        System.out.println(multiplyAll(a));
        System.out.println(multiplyAll(b));
        System.out.println(multiplyAll(c));
        System.out.println(catAll(a));
        System.out.println(catAll(b));
        System.out.println(catAll(c));
        System.out.println(catAll(d));
        System.out.println(max(a));
        System.out.println(max(b));
        System.out.println(max(c));
        System.out.println(max(e));
        System.out.println(min(a));
        System.out.println(min(b));
        System.out.println(min(c));
        System.out.println(min(e));
        System.out.println(clampMin(MyIO.readInt("min: "), MyIO.readInt("i: ")));
    }

    public static long addAll(int... a) { //Addierung aller Werte einer Int-Liste
        int count = 0;
        for (int i : a) {
            count += i;
        }
        return count;
    }

    public static long addAll(long... a) { //Addierung aller Werte einer long-Liste
        long count = 0;
        for (long i : a) {
            count += i;
        }
        return count;
    }

    public static double addAll(double... a) { //Addierung aller Werte einer Double-Liste
        double count = 0;
        for (double i : a) {
            count += i;
        }
        return count;
    }

    public static String addAll(String... a) { //Addierung aller Werte einer String-Liste
        String connected = "";
        for (String i : a) {
            connected += i;
        }
        return connected;
    }

    public static long multiplyAll(int... a) { //Multiplikation aller Werte einer Int-Liste
        long product = 1;
        for (int i : a) {
            product *= i;
        }
        return product;
    }

    public static long multiplyAll(long... a) {//Multiplikation aller Werte einer Long-Liste
        long product = 1;
        for (long i : a) {
            product *= i;
        }
        return product;
    }

    public static double multiplyAll(double... a) { //Multiplikation aller Werte einer Double-Liste
        double product = 1;
        for (double i : a) {
            product *= i;
        }
        return product;
    }

    public static String catAll(String... a) { //Multiplikation aller Werte einer String-Liste
        String connected = "";
        for (String i : a) {
            connected += i + ",";
        }
        return connected.substring(0, connected.length() - 1);
    }

    public static String catAll(int... a) { //Anneinanderreihung von Int-Werten als String
        String connected = "";
        for (int i : a) {
            connected += i + ",";
        }
        return connected.substring(0, connected.length() - 1);
    }

    public static String catAll(long... a) { //Anneinanderreihung von Long-Werten als String
        String connected = "";
        for (long i : a) {
            connected += i + ",";
        }
        return connected.substring(0, connected.length() - 1);
    }

    public static String catAll(double... a) { //Anneinanderreihung von Double-Werten als String
        String connected = "";
        for (double i : a) {
            connected += i + ",";
        }
        return connected.substring(0, connected.length() - 1);
    }

    public static int max(int... a) { //Rückgabe des größten Wertes von einer Int-Liste
        int m = Integer.MIN_VALUE;
        for (int i : a) {
            m = Math.max(m, i);
        }
        return m;
    }

    public static int min(int... a) { //Rückgabe des kleinsten Wertes von einer Int-Liste
        int m = Integer.MAX_VALUE;
        for (int i : a) {
            m = m >= i ? i : m;
        }
        return m;
    }

    public static long max(long... a) { //Rückgabe des größten Wertes von einer Long-Liste
        long m = Long.MIN_VALUE;
        for (long i : a) {
            m = m <= i ? i : m;
        }
        return m;
    }

    public static long min(long... a) { //Rückgabe des kleinsten Wertes von einer Long-Liste
        long m = Long.MAX_VALUE;
        for (long i : a) {
            m = m >= i ? i : m;
        }
        return m;
    }

    public static float max(float... a) { //Rückgabe des größten Wertes von einer Float-Liste
        float m = Float.MIN_VALUE;
        for (float i : a) {
            m = m <= i ? i : m;
        }
        return m;
    }

    public static float min(float... a) { //Rückgabe des kleinsten Wertes von einer Float-Liste
        float m = Float.MAX_VALUE;
        for (float i : a) {
            m = m >= i ? i : m;
        }
        return m;
    }

    public static double max(double... a) { //Rückgabe des größten Wertes von einer Double-Liste
        double m = Double.MIN_VALUE;
        for (double i : a) {
            m = m <= i ? i : m;
        }
        return m;
    }

    public static double min(double... a) { //Rückgabe des kleinsten Wertes von einer Double-Liste
        double m = Double.MAX_VALUE;
        for (double i : a) {
            m = m >= i ? i : m;
        }
        return m;
    }

    public static int abs(int i) {
        return i < 0 ? i * -1 : i;
    } //Rückgabe einer positiven Zahl auf Grundlage einer Int-Zahl

    public static long abs(long i) {
        return i < 0L ? i * -1L : i;
    } //Rückgabe einer positiven Zahl auf Grundlage einer Long-Zahl

    public static float abs(float i) {
        return i < 0f ? i * -1f : i;
    } //Rückgabe einer positiven Zahl auf Grundlage einer Float-Zahl

    public static double abs(double i) {
        return i < 0d ? i * -1d : i;
    } //Rückgabe einer positiven Zahl auf Grundlage einer Double-Zahl

    public static int pow(int a, int b) {

        return b == 0 ? 1 : b != 1 ? a * pow(a, --b) : a;
    }

    public static long pow(long a, long b) {
        return b == 0L ? 1L : b != 1L ? a * pow(a, --b) : a;
    }

    public static int sqr(int a) {
        return a * a;
    } //Quadratwert einer Int-Zahl

    public static long sqr(long a) {
        return a * a;
    } //Quadratwert einer Long-Zahl

    public static float sqr(float a) {
        return a * a;
    } //Quadratwert einer Float-Zahl

    public static double sqr(double a) {
        return a * a;
    } //Quadratwert einer Double-Zahl

    public static String toBinary(int n) {
        String b = "";
        while (n != 0) {
            b += n % 2;
            n /= 2;
        }
        return invert(b);
    } //Umwandlung eines Int-Wertes ind Binary

    public static String toBinary(double n) {
        String b = "";
        int preComma = (int) n;
        double postComma = n - preComma;
        System.out.println(preComma);
        System.out.println(postComma);
        if (preComma == 0) {
            b += 0;
        }
        b += toBinary(preComma) + ".";
        while (postComma != 0) {
            postComma *= 2d;
            if ((int) postComma == 1) {
                b += 1;
                postComma -= 1d;
            } else {
                b += 0;
            }
        }
        return b;
    } //Umwandlung eines Double-Wertes ind Binary

    public static int toDecimal(String n) {
        int r = 0;
        for (int i = 0; i <= n.length() - 1; i++) {
            r += Integer.parseInt(n.substring(i, i + 1)) * pow(2, n.length() - i - 1);
        }
        return r;
    }

    public static double toFPN(String n) {
        int rPreC, pointIndex = 0;
        double rPostC = 0d;
        for (int i = 0; i <= n.length() - 1; i++) {
            if (n.charAt(i) == '.') {
                pointIndex = i + 1;
                break;
            }
        }
        rPreC = toDecimal(n.substring(0, pointIndex - 1));
        for (int i = 1; i <= n.length() - pointIndex; ++i) {
            //System.out.println("i: " + i + " pointIndex: " + pointIndex + " Substring: " + n.substring(pointIndex + i - 1, pointIndex + i));
            rPostC += Double.parseDouble(n.substring(pointIndex + i - 1, pointIndex + i)) * (1d / (Math.pow(2d, ((double) i))));
        }
        return rPreC + rPostC;
    }

    public static int clampMin(int value, int clampedTo) {
        return clampedTo <= value ? value : clampedTo;
    }

    public static int clampMax(int value, int clampedTo) {
        return clampedTo >= value ? value : clampedTo;
    }

    public static int clampMinMax(int lowValue, int highValue, int clampedTo) {
        if (clampedTo <= lowValue) {
            return lowValue;
        } else if (clampedTo >= highValue) {
            return highValue;
        } else {
            return clampedTo;
        }
    }

    public static boolean isPrime(long n) {
        long tester, count = 0;
        if (n == 1) {
            return true;
        }
        for (int i = 1; i <= n; i++) {
            tester = n % i;
            if (tester == 0) {
                count++;
            }
        }
        return count == 2;
    }

    public static long nextPrime(long n) {
        boolean primeFound = false;
        while (!primeFound) {
            n++;
            primeFound = isPrime(n);
        }
        return n;
    }

    public static int rand(int min, int max) {
        return (int) Math.floor(Math.random() * (max - min + 1) + min);
    } //Erstellung eines zufälligen Int-Wertes

    public static double rand(double min, double max) {
        return Math.floor(Math.random() * (max - min + 1) + min);
    } //Erstellung eines zufälligen Double-Wertes

    public static String invert(String n) {
        return n.length() <= 1 ? n : invert(n.substring(1)) + n.charAt(0);
    }
}

