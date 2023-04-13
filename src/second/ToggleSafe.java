package second;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 13.04.2023
 */

public class ToggleSafe extends JFrame implements ActionListener {
    // Ändern Sie das DrehSafe-Programm um in ToggleSafe, so dass mehrere aktive Fenster (mit sich drehenden Knöpfen) gleichzeitig offen sein können.
    // Die Kombination 8-5-2-9-6-3-0-7-4-1 öffnet das Schloss.
    // Die Drehrichtung ändert sich nach jeweils 9 Schritten.
    // Bei jedem falschen Knopfdruck öffnet sich ein weiteres Fenster derselben Art, in dem sich die Knöpfe um 33% schneller drehen als im vorherigen Fenster.
    // Und das Schoss geht erst dann auf, wenn alle Fenster geschlossen wurden

    public ToggleSafe() {
        setTitle(" ToggleSafe ");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        ToggleSafe ToggleSafe = new ToggleSafe();
        ToggleSafe.setSize(800, 600);// Setze die Größe des Fensters auf 800 x 600 Pixel (Formatierung)
        ToggleSafe.setResizable(false);
        ToggleSafe.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
