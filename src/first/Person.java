package first;

/**
 * @version 1, 06.12.2022
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 */

import java.util.Calendar;
import java.util.GregorianCalendar;

interface SimpleTreeNode {
    void addChild(SimpleTreeNode child);

    int getChildCnt();

    SimpleTreeNode getChild(int pos);
}

public class Person implements Comparable<Person>, Cloneable, SimpleTreeNode {

    protected String vorname, name, beruf, geburtsort;
    protected int geburtsJahr;
    protected float koerpergroesse;
    protected SimpleTreeNode treeNode = new DefaultTreeNode("");

    public static void main(String[] args) {
        Person a = new Person("Denis", "Schaffer", "Arbeitslos", 2003, 1.80f, "Backnang");
        Person b = new Person("Filip", "Schepers", "Arbeitslos", 2004, 1.75f, "Dorsten");
        Person c[] = {
                new Person("Denis", "Schaffer", "Arbeitslos", 2003, 1.80f, "Backnang"),
                new Person("Filip", "Schepers", "Arbeitslos", 2004, 1.75f, "Dorsten"),
                new Person("Daniel", "Faigle", "Arbeitslos", 2002, 1.70f, "München"),
                new Person("Moritz", "Binneweiß", "Arbeitslos", 2004, 1.85f, "Obersulm"),
                new Person("Vanessa", "Schoger", "Arbeitslos", 2003, 1.60f, "Burgstall"),
        };
        a.addChild(b);
        System.out.println("Anzahl an Kindern: " + a.getChildCnt());
        a.clone();
        a.addChild(a);
        System.out.println("Kind 1: " + a.getChild(0).toString());
        System.out.println("Kind 2: " + a.getChild(1).toString());
        System.out.println("Anzahl an Kindern: " + a.getChildCnt());
        System.out.println("Vergleich zwischen Person a und b: ");
        System.out.println(a.compareTo(b));
        System.out.println("Vergleich zwischen Kind 0 und 1 von Person a: ");
        System.out.println(((Person) a.getChild(1)).compareTo((Person)a.getChild(0)));
        System.out.println("Unsortierte Liste: ");
        for (Person x : c) {
            System.out.println(x.toString());
        }
        sort(c);
        System.out.println("Sortierte Liste: ");
        for (Person x : c) {
            System.out.println(x.toString());
        }
        System.out.println();

    }

    public Person() {
        this.vorname = MyIO.promptAndRead("Vorname eingeben: ");
        this.name = MyIO.promptAndRead("Name eingeben: ");
        this.beruf = MyIO.promptAndRead("Beruf eingeben: ");
        this.geburtsJahr = MyIO.readInt("Geburtsjahr eingeben: ");
        this.koerpergroesse = MyIO.readFloat("Groesse eingeben:");
        this.geburtsort = MyIO.promptAndRead("Geburtsort eingeben");
    }

    public Person(String vName, String nName, String bBeruf, int gBJahr, float kGroesse, String gOrt) {
        this.vorname = vName;
        this.name = nName;
        this.beruf = bBeruf;
        this.geburtsJahr = gBJahr;
        this.koerpergroesse = kGroesse;
        this.geburtsort = gOrt;
    }

    public String toString() {
        int jahr = new GregorianCalendar().get(Calendar.YEAR);
        int alter = jahr - this.geburtsJahr;

        String gBewertung;
        if (this.koerpergroesse < 1.49) {
            gBewertung = "klein";
        } else if (this.koerpergroesse > 1.48 && this.koerpergroesse < 1.83) {
            gBewertung = "mittel";
        } else {
            gBewertung = "gross";
        }
        //System.out.println(this.vorname + " " + this.name + " " + this.beruf + " " + this.geburtsJahr + " " + this.koerpergroesse + " " + this.geburtsort + " " + gBewertung + " " + alter);
        return this.vorname + " " + this.name + " " + this.beruf + " " + this.geburtsJahr + " " + this.koerpergroesse + " " + this.geburtsort + " " + gBewertung + " " + alter;
    }

    public void compareWith(Person b) {
        if (this.geburtsJahr > b.geburtsJahr) {
            System.out.println("Person " + b.vorname + " ist älter");
        } else if (this.geburtsJahr < b.geburtsJahr) {
            System.out.println("Person " + this.vorname + " ist älter");
        } else {
            System.out.println("Beide Personen sind im selben Jahr geboren");
        }

        if (this.koerpergroesse < b.koerpergroesse) {
            System.out.println("Person " + b.vorname + " ist größer");
        } else if (this.koerpergroesse > b.koerpergroesse) {
            System.out.println("Person " + this.vorname + " ist größer");
        } else {
            System.out.println("Beide Personen sind gleich groß");
        }
    }


    @Override
    public void addChild(SimpleTreeNode child) {
        treeNode.addChild(child);
    }

    @Override
    public int getChildCnt() {
        return treeNode.getChildCnt();
    }

    @Override
    public SimpleTreeNode getChild(int pos) {
        return treeNode.getChild(pos);
    }

    @Override
    public int compareTo(Person o) {
        int y = name.compareTo(o.name);
        return y != 0 ? y : vorname.compareTo(o.vorname);
    }

    public Object clone() {
        Person x = new Person(vorname, name, beruf, geburtsJahr, koerpergroesse, geburtsort);
        x.treeNode = new DefaultTreeNode("");
        for (int i = 0; i < getChildCnt(); i++) {
            x.treeNode.addChild((SimpleTreeNode) ((Person) getChild(i)).clone());
        }
        return x;
    }

    public static Person[] sort(Person[] a)
    {
        Person speicher;
        int zähler = 0;
        for(int x = 0; x< 100; x++) {
            for (int i = 0; i < a.length - 1; i++) {
                if (a[zähler].compareTo(a[zähler + 1]) > 0) {
                    speicher = a[zähler];
                    a[zähler] = a[zähler + 1];
                    a[zähler + 1] = speicher;
                }
                zähler++;
            }
            zähler = 0;
        }
        return a;
    }

}
