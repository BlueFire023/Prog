package second;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class TrackEvent extends JFrame {
    HashMap<String,JTextField> h = new HashMap<String,JTextField>();
    String[] event = {			// Array mit Strings fuer Event-Namen
            "focusGained", "focusLost", "keyPressed",
            "keyReleased", "keyTyped", "mouseClicked",
            "mouseEntered", "mouseExited","mousePressed",
            "mouseReleased", "mouseDragged", "mouseMoved"
    };

    MyButton
            b1 = new MyButton(Color.yellow, "Knopf A"),
            b2 = new MyButton(Color.green, "Knopf B");
    class MyButton extends JButton {			// innere Klasse von TrackEvent
        void report(String field, AWTEvent e ) {
            h.get(field).setText(((MyButton)e.getSource()).getText()+": "+e.paramString());	// h ordnet felder JTextFields zu
        }
        FocusListener fl = new FocusListener() {
            public void focusGained(FocusEvent e) {
                report("focusGained", e);	// schreibe ins fG-Field den p-String
            }
            public void focusLost(FocusEvent e) {
                report("focusLost", e);	// ... mit FocusLost
            }
        };
        KeyListener kl = new KeyListener() {
            public void keyPressed(KeyEvent e) {
                report("keyPressed", e);	// Tastendruck
            }
            public void keyReleased(KeyEvent e) {
                report("keyReleased", e);	// Tastelosgelassen
            }
            public void keyTyped(KeyEvent e) {
                report("keyTyped", e);	// TasteGedrückt
            }
        };
        MouseListener ml = new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                report("mouseClicked", e);// mausgecklickt
            }
            public void mouseEntered(MouseEvent e) {
                report("mouseEntered", e);// maus ins feld
            }
            public void mouseExited(MouseEvent e) {
                report("mouseExited", e);	// maus raus
            }
            public void mousePressed(MouseEvent e) {
                report("mousePressed", e);// Mausgedrückt
            }
            public void mouseReleased(MouseEvent e) {
                report("mouseReleased", e);// mauslosgelassen
            }
        };
        MouseMotionListener mml =
                new MouseMotionListener() {
                    public void mouseDragged(MouseEvent e) {
                        report("mouseDragged", e);// mausziehen
                    }
                    public void mouseMoved(MouseEvent e) {
                        report("mouseMoved", e);	// mausbewegt
                    }
                };
        public MyButton(Color color, String label) {// Konstruktor
            super(label);								// label
            setBackground(color);						// farbe
            addFocusListener(fl);						// verschiedene Listener
            addKeyListener(kl);						// Key
            addMouseListener(ml);						// Mouse
            addMouseMotionListener(mml);				// MouseMotion
        }
    }
    public TrackEvent() {
        try {
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
        } catch (Exception o) {
            o.printStackTrace();
        }
        // init
        Container c = getContentPane();				// container
        c.setLayout(new GridLayout(event.length+1,2));	// Layout Manager
        for(int i = 0; i < event.length; i++) {		// fuer jeden Event-Typ
            JTextField t = new JTextField();			// ein Textfeld
            t.setPreferredSize(new Dimension(600,500));
            t.setEditable(false);						// nicht editierbar
            JLabel ll = new JLabel(event[i], JLabel.CENTER);
            c.add(ll);								// Label, rechts ausgerichtet
            c.add(t);									// und Textfeld hinzufuegern
            h.put(event[i], t);						// Event-String dem Feld zuordnen
        }
        c.add(b1);									// b1 und
        c.add(b2);
        b1.setOpaque(true);
        b2.setOpaque(true);// b2 ...
        setTitle("TrackEvent ... TrackEvent ... TrackEvent ... ");
        setSize(1700, 700);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        setUIFont (new javax.swing.plaf.FontUIResource("Courier", Font.BOLD, 18));
        new MyPaint();
    }
    // zum Setzen der Schriftart ...
    public static void setUIFont (javax.swing.plaf.FontUIResource f){
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get (key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put (key, f);
        }
    }
}
