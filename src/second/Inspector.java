package second;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 11/05/2023
 */

import java.lang.reflect.*;
import java.util.Scanner;

public class Inspector {
    public static void main(String[] args) {
        System.out.println("Geben sie hier den Namen der gewünschten Klasse ein: ");
        Scanner scanLeser = new Scanner(System.in);
        String klassenName = scanLeser.nextLine();

        while (true) {
            try {
                if (klassenName.toLowerCase().startsWith("end")) {
                    break;
                }
                Class<?> klassenObjekt = Class.forName(klassenName);

            } catch (ClassNotFoundException e) {
                System.out.println("Klasse("+klassenName+") wurde nicht gefunden.");
            }
        }
    }
}


