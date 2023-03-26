package first;

/**
 * @author Denis Schaffer, Filip Schepers, Daniel Faigle, Moritz Binneweiß
 * @version 1, 17,10,2022
 **/

import java.io.*;

public class Janus {
    public static void main(String[] args) throws Exception {
        String inputLine;
        File f = new File("src/first/Janus.java");
        FileInputStream fs = new FileInputStream(f);
        InputStreamReader isr;
        BufferedReader fileInput;
        isr = new InputStreamReader(fs);
        fileInput = new BufferedReader(isr);
        inputLine = fileInput.readLine();
        while (inputLine != null) {
            System.out.println(inputLine);
            inputLine = fileInput.readLine();
        }
    }
}
