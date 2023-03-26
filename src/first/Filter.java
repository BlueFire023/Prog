package first;

/**
 * @author Denis Schaffer, Filip Schepers, Daniel Faigle, Moritz Binneweiß
 * @version 1, 17,10,2022
 **/

import java.net.URL;
import java.io.*;

public class Filter {
    public static void main(String[] args) throws Exception {
        // https://www.whitehouse.gov/
        InputStreamReader is;
        BufferedReader keyboard;
        String search, text;
        int column = 0;
        is = new InputStreamReader(System.in);
        keyboard = new BufferedReader(is);

        System.out.println("Gib URL: ");
        URL u = new URL(keyboard.readLine().toLowerCase());
        System.out.println("Das Wort das du suchst: ");
        search = keyboard.readLine().toLowerCase();
        FilterInputStream ins = (FilterInputStream) u.openStream();
        InputStreamReader isr = new InputStreamReader(ins);
        BufferedReader reader = new BufferedReader(isr);
        text = reader.readLine();
        while (text != null) {
            if (text.contains(search)) {
                System.out.println("Zeile: " + column + " : " + text);
            }
            column++;
            text = reader.readLine();
        }
    }
}
