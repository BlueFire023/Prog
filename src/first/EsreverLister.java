package first;

/**
 * @version 1, 07.11.2022
 * @author Filip Schepers, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Denis Schaffer
 */

import java.io.*;
import java.util.ArrayList;

public class EsreverLister {
    public static void main(String[] arg) throws IOException {
        ArrayList<String> a = new ArrayList<>();
        int z = 1;
        while (true) {
            String b = MyIO.promptAndRead("Satz " + z + ": ");
            if (b.equals(null) || b.equals("^z") || b.equals("potS")) {
                break;
            }
            a.add(Vorstellung.invert(b));
            z++;
        }
        arrayUmgekehrtAusgeben(a);
        arrayUmgekehrtAusgeben(codeIntoArray());
    }

    public static void arrayUmgekehrtAusgeben(ArrayList<String> a) {
        for (int i = a.size(); i > 0; i--) {
            System.out.println(a.get(i - 1));
        }
    }

    public static ArrayList<String> codeIntoArray() throws IOException {
        ArrayList<String> a = new ArrayList<>();

        String inputLine;
        File f = new File("src/first/EsreverLister.java");
        FileInputStream fs = new FileInputStream(f);
        InputStreamReader isr;
        BufferedReader fileInput;
        isr = new InputStreamReader(fs);
        fileInput = new BufferedReader(isr);
        inputLine = fileInput.readLine();
        while (inputLine != null) {
            a.add(Vorstellung.invert(inputLine));
            inputLine = fileInput.readLine();
        }
        return a;
    }
}
