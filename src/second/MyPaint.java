package second;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 13.04.2023
 */

public class MyPaint extends JFrame implements ActionListener {
    //Als Ergebnis sollte ein Programm MyPaint entstehen und abgegeben werden, das es erlaubt,
    // einfache Malereien auf einen 2-dimensionalen Gitter zu erzeugen (Phantasie erlaubt / gefragt)

    public MyPaint() {
        setTitle(" MyPaint ");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        MyPaint MyPaint = new MyPaint();
        MyPaint.setSize(800, 600);// Setze die Größe des Fensters auf 800 x 600 Pixel (Formatierung)
        MyPaint.setResizable(false);
        MyPaint.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
