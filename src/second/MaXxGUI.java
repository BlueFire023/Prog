package second;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 13.04.2023
 */

public class MaXxGUI extends JFrame implements ActionListener {
    //Schreiben Sie das Programm MaXxGuI für das „MaXx“-Spiel aus dem 1. Semester (Foliensatz 9, Seite 62).
    // Es soll dabei möglich sein, mehrere Fenster gleichzeitig offen zu haben und in jedem Fenster ein Spiel zu spielen,
    // welches von den anderen unabhängig ist.

    public MaXxGUI() {
        setTitle(" MaXxGUI ");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        MaXxGUI MaXxGUI = new MaXxGUI();
        MaXxGUI.setSize(800, 600);// Setze die Größe des Fensters auf 800 x 600 Pixel (Formatierung)
        MaXxGUI.setResizable(false);
        MaXxGUI.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
