package second;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 11/05/2023
 */

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Scanner;

public class Inspector {
    public static void main(String[] args) {

        //Benutzereingabe der gewünschten Klasse
        System.out.println("Geben sie hier den Namen der gewünschten Klasse ein: ");
        Scanner scanLeser = new Scanner(System.in);
        String klassenName = scanLeser.nextLine();
        try {

            //Laufzeit über Benutzereingabe beenden
            if (klassenName.toLowerCase().startsWith("end")) {
                System.exit(1);
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

            //Modifier ausgeben
            int modifierObjekte = klassenObjekt.getModifiers();
            System.out.println("Modifier: "+ Modifier.toString(modifierObjekte));

            //Methoden ausgeben
            Method[] methodenObjekte = klassenObjekt.getMethods();
            if (methodenObjekte.length > 0){
                System.out.println("Methoden: ");
                for (Method meth: methodenObjekte){

                    //Modifier, Return Werte, Name ausgeben
                    System.out.print("Modifier:" + Modifier.toString(meth.getModifiers()) + " ");
                    System.out.print("Returnwert:" + meth.getReturnType().getName() + " ");
                    System.out.print("Methodenname:" + meth.getName() + " Parameter:(");

                    //Parameter/Argumente ausgeben
                    Parameter[] parameterObjekte = meth.getParameters();
                    for(int ind = 0; ind < parameterObjekte.length; ind++){
                        Parameter para = parameterObjekte[ind];
                        System.out.print(para.getType().getName());
                    }
                    System.out.print(")");
                    System.out.println("");
                }
            }
        }
        //Benutzereingabe konnte nicht gefunden werden
        catch (ClassNotFoundException e) {
            System.out.println("Klasse("+klassenName+") wurde nicht gefunden.");
        }
    }
}


