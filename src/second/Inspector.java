package second;
import java.lang.reflect.*;
import java.util.Scanner;
public class Inspector {
    public static void main(String[] args) throws Exception{
        System.out.println("Geben sie den Namen ihrer Klasse ein:");
        Scanner scanner = new Scanner(System.in);
        String className = scanner.nextLine();

        try {
            if (className.toLowerCase().startsWith("end")){
                System.exit(1);
            }

            Class<?> classObject = Class.forName(className);

            Class<?> superclassObject = classObject.getSuperclass();
            if(superclassObject != null) {
                System.out.println("Superklasse:" + superclassObject.getName());
            }

            Class<?>[] interfaceObject = classObject.getInterfaces();
                 if(interfaceObject.length > 0){
                     System.out.println("Interfaces: ");
                     for(Class<?> inface : interfaceObject){
                         System.out.println(inface.getName());
                     }
                 }

           int modifierObject = classObject.getModifiers();
            System.out.println("Modifiers: " + Modifier.toString(modifierObject));

           Method[] methodObjects = classObject.getMethods();
            if (methodObjects.length > 0){
                System.out.println("Methoden: ");
                for(Method method : methodObjects){

                    System.out.println(" ");
                    System.out.println("-Methode:");
                    System.out.println(" Modifier: " + Modifier.toString(method.getModifiers()));
                    System.out.println(" Returnwert: " + method.getReturnType().getName());
                    System.out.println(" Methodenname: " + method.getName());

                    Parameter[] parameterObjects = method.getParameters();
                    System.out.print(" Parameter: (");
                    for (int ind = 0; ind < parameterObjects.length; ind++){
                        Parameter para = parameterObjects[ind];
                        System.out.print(para.getType().getName());
                        if (ind < parameterObjects.length - 1){
                            System.out.print(", ");
                        }
                    }
                    System.out.print(")");
                    System.out.println(" ");
                }
            }
        }

        catch (ClassNotFoundException e){
            System.out.println(" Klasse (" + className + ") konnte nicht gefunden werden");
        }
    }
}
