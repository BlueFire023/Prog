package second;

/**
 * @version 1.1, 30.03.2023
 * @author Filip Schepers, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Denis Schaffer
 */

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowQuitter extends WindowAdapter
{
    public void windowClosing(WindowEvent e)
    {
        System.exit(0);
    }
}
