package second;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 11/05/2023
 */

import java.util.Scanner;

public class Inspector {
    public static void main(String[] args) {
        //Benutzereingabe der gewünschten Klasse
        System.out.println("Geben sie hier den Namen der gewünschten Klasse ein: ");
        Scanner scanLeser = new Scanner(System.in);
        String klassenName = scanLeser.nextLine();

        while (true) {
            try {
                //Laufzeit über Benutzereingabe beenden
                if (klassenName.toLowerCase().startsWith("end")) {
                    break;
                }
                //Klassenobjekt anhand des Klassennamens erstellen
                Class<?> klassenObjekt = Class.forName(klassenName);

                //Superklasse ausgeben
                Class<?> superklassenObjekt = klassenObjekt.getSuperclass();
                if (superklassenObjekt != null){
                    System.out.println("Superklasse: " + superklassenObjekt.getName());
                }

                //Interfaces ausgeben
                Class<?>[] interfaceObjekte = klassenObjekt.getInterfaces();
                if (interfaceObjekte.length > 0){
                    System.out.println("Interfaces: ");
                    for (Class<?> inface: interfaceObjekte){
                        System.out.println(inface.getName());
                    }
                }

            }
            //Benutzereingabe konnte nicht gefunden werden
            catch (ClassNotFoundException e) {
                System.out.println("Klasse("+klassenName+") wurde nicht gefunden.");
            }
        }
    }
}


