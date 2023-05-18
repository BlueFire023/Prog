package second;
import java.awt.event.*;


public class WindowQuitter extends WindowAdapter {
        public void windowClosing ( WindowEvent e ) {
            System.exit ( 0 );
        }
    }
