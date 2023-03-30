package second;
/**
 * @author  Filip Schepers, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Denis Schaffer
 * @version 1, 30/03/2023
 */
import java.awt.*;
import java.awt.event.*;

class WindowQuitter extends WindowAdapter {
    public void windowClosing( WindowEvent e ) {
        System.exit(0);
} }
