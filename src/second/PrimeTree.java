package second;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 16/03/2023
 */

public class PrimeTree extends Thread {
    private int p;
    private PrimeTree left;
    private PrimeTree right;
    private int buffer = -1;
    PrimeTree(int prime)
    {
        super("Primer-"+ prime);
        p = prime;
        this.start();
    }

    public static void main(String args[])
    {

        PrimeTree first = new PrimeTree(2);

        for(int i=3; i<=100; i++){
            first.send(i);
        }
        first.send(0);


        System.out.println(currentThread() + "main beendet");
    }

    public void run(){
        System.out.println(currentThread() + "Errechnete Zahl: "+ p);
        while (true){
            int n = recieve();
            if (n == 0){
                if(left != null) left.send(n);
                if(right != null) right.send(n);
                break;
            }
            if (n%p != 0){
                if(left != null) left.send(n);
                else left = new PrimeTree(n);
                if(right != null) right.send((3*n)+1);
                else right = new PrimeTree((3*n)+1);
            }


        }
    }

    synchronized void send(int i){
        try{
            while (buffer >= 0) wait();
            buffer = i;
            notify();
        }catch (InterruptedException e ) {}
    }

    synchronized int recieve(){
        int result = 0;
        try{
            while ((result=buffer)<0) wait();
            buffer = -1;
            notify();
        }catch (InterruptedException e){}
        return result;
    }

}

/**
 * Bei der Ausgabe des Programmes kann man beobachten, dass das Programm in eine Endlos-Schleife gerät und später abgebrochen werden muss bzw. das System das Programm abbricht (Fehler).
 * Das kommt dadurch zustande, dass die Anzahl der Threads exponentiell wächst, da pro Element(stellt einen Thread dar) der Pipe zwei neue Elemente erstellt werden.
 * Auch wenn der Selbstzerstörungsbefehl erteilt wurde, kommt dieser dem raschen Wachstum kaum hinterher bzw. ist nicht in der Lage das Wachstum einzuholen.
 */
