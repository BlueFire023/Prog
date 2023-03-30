package second;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 30/03/2023
 */
public class WindowQuitter extends WindowAdapter {
    public void windowClosing(WindowEvent e){
        System.exit(0);
    }
}
