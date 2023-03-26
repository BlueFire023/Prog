package second;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 16/03/2023
 */
public class SevenColumnPrimes extends Thread {
    private int p;
    private String spacer = "";
    private SevenColumnPrimes next;
    private int buffer = -1;

    SevenColumnPrimes(int prime, int spaceL) {
        super("Column-" + spaceL);
        p = prime;
        for (int i = 0; i < spaceL; i++) {
            this.spacer += "    ";
        }
        this.start();
    }

    public static void main(String[] args) {
        final int COLUMNS = 7;
        final int LASTNUMBER = 7351;
        SevenColumnPrimes[] first = new SevenColumnPrimes[COLUMNS];
        for (int i = 0; i < COLUMNS; i++) {
            first[i] = new SevenColumnPrimes(2, i);
        }
        for (int k = 3; k <= LASTNUMBER; k++) {
            for (SevenColumnPrimes f : first) {
                f.send(k);
            }
        }
        for (SevenColumnPrimes f : first) {
            f.send(0);
        }
    }

    public void run() {
        System.out.println(spacer + p);
        while (true) {
            int n = recieve();
            if (n == 0) {
                if (next != null) {
                    next.send(n);
                }
                break;
            }
            if (n % p != 0) {
                if (next != null) {
                    next.send(n);
                } else {
                    next = new SevenColumnPrimes(n, this.spacer.length() / 4);
                }
            }
        }
    }

    synchronized void send(int i) {
        try {
            while (buffer >= 0) {
                wait();
            }
            buffer = i;
            notify();
        } catch (Exception ignored) {
        }
    }

    synchronized int recieve() {
        int result = 0;
        try {
            while ((result = buffer) < 0) {
                wait();
            }
            buffer = -1;
            notify();
        } catch (Exception ignored) {
        }
        return result;
    }
}