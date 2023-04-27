package second;
/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 13/04/2023
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MyPaint extends JFrame {

    MyButton buttons[]; //Array von Buttons, die als Pixel auf dem Bild dienen
    private boolean test; //Testvariable, die zeigt, ob die Mausgedrückt wird
    class MyButton extends JButton {			// innere Klasse von TrackEvent
        MouseListener ml = new MouseListener() {
            public void mouseClicked(MouseEvent e) {
            }
            public void mouseEntered(MouseEvent e) { // Zeichnet Rot, wenn Pixel(Button) berührt wird und die Maus gedrückt ist
                if(test) {
                    ((JButton)e.getSource()).setBackground(Color.ORANGE);
                }
            }
            public void mouseExited(MouseEvent e) {
            }
            public void mousePressed(MouseEvent e) { // Wenn die Maus gedrückt ist, wird die Testvariable auf wahr gesetzt
                test = true;
            }
            public void mouseReleased(MouseEvent e) { //Wenn die Maus nicht gedrückt ist, wird die Testvariable auf falsch gesetzt
                test = false;
            }
        };

        public MyButton(Color color, String label) {// Konstruktor
            super(label);								// label
            setBackground(color);						// farbe
            addMouseListener(ml);						// Mouse
        }
    }
    public MyPaint() {
        // Dieser Part wird für die Sichtbarkeit der Farben auf MacOS benötigt
        try {
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
        } catch (Exception o) {
            o.printStackTrace();
        }


        buttons = new MyButton[4096]; // "Pixel-Array" wird definiert
        setSize(1000,1000); // Fenstergröße wird gesetzt
        setLocation(230,222); //Fensterlocation wird gesetzt
        setTitle("MyPaint"); //Fenstertitel wird gesetzt
        Container cp = getContentPane(); //Container für die Buttons wird erstellt
        cp.setLayout(new GridLayout(64,64)); //Die Grid-Größe wird gesetzt
        for(int i= 0; i<4096;i++) // Grid füllt sich mit den Buttons aus dem Array
        {
            buttons[i] = new MyButton(Color.BLUE,"");
            cp.add(buttons[i]);
            buttons[i].setFont(new Font("",Font.BOLD, 1));
            buttons[i].setBorderPainted(false);
        }
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        MyPaint p = new MyPaint();
    }
}
