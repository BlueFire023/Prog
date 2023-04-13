package second;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 13.04.2023
 */

public class MyPaint extends JFrame implements MouseListener {
    //Als Ergebnis sollte ein Programm MyPaint entstehen und abgegeben werden, das es erlaubt,
    // einfache Malereien auf einen 2-dimensionalen Gitter zu erzeugen (Fantasie erlaubt / gefragt)

    public MyPaint() {
        setTitle(" MyPaint ");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container cp = getContentPane();
        JPanel gridPanel = new JPanel(new GridLayout(30, 30));

        // Weise jedem Zell-Panel eine Hintergrundfarbe zu
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                JPanel cellPanel = new JPanel();
                cellPanel.setBackground(Color.GRAY);
                gridPanel.add(cellPanel);
            }
        }
        cp.add(gridPanel);
    }

    void report(String field, AWTEvent e ) {
        h.get(field).setText(((TrackEvent.MyButton)e.getSource()).getText()+": "+e.paramString());	// h ordnet felder JTextFields zu
    }
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


    public static void main(String[] args) {
        MyPaint MyPaint = new MyPaint();
        MyPaint.setSize(800, 600);// Setze die Größe des Fensters auf 800 x 600 Pixel (Formatierung)
        MyPaint.setResizable(true);
        MyPaint.setVisible(true);
    }
