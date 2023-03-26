package first;

/**
 * @author Denis Schaffer, Filip Schepers, Daniel Faigle, Moritz Binneweiß
 * @version 1, 17,10,2022
 **/

import java.io.*;

public class FileMirror {
    public static void main(String[] args) throws Exception {
        // src/first/Datei1
        // src/first/Datei2
        InputStreamReader isr;
        BufferedReader keyboard;
        String path;
        isr = new InputStreamReader(System.in);
        keyboard = new BufferedReader(isr);
        System.out.println("Gib Pfad der ersten Datei: ");
        path = keyboard.readLine();
        File f1 = new File(path);
        System.out.println("Gib Pfad der zweiten Datei: ");
        path = keyboard.readLine();
        File f2 = new File(path);
        copyFileAndInvert(f1, f2);
    }

    public static void copyFileAndInvert(File f1, File f2) throws Exception {
        String line;
        PrintStream target = new PrintStream(new FileOutputStream(f2));
        FileInputStream fs = new FileInputStream(f1);
        BufferedReader fileInput;
        InputStreamReader isr;
        isr = new InputStreamReader(fs);
        fileInput = new BufferedReader(isr);
        line = fileInput.readLine();
        while (line != null) {
            target.println(invert(line));
            line = fileInput.readLine();
        }
    }

    public static String invert(String n) {
        return n.length() <= 1 ? n : invert(n.substring(1)) + n.charAt(0);
    }
}
