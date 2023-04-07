package second;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 06/04/2023
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DrehSafe extends JFrame implements ActionListener, Runnable {
    // Das korrekte Passwort, um den Safe zu öffnen
    private final String PASSWORD = "8224725301";
    // Das vom Benutzer eingegebene Passwort
    private String pTest = "";
    // Die Drehrichtung des Tastenfeldes
    private boolean clockwise = true;
    // Die 10 Ziffern-Tasten des Tastenfeldes
    private final JButton[] buttons = {
            new JButton("0"),
            new JButton("1"),
            new JButton("2"),
            new JButton("3"),
            new JButton("4"),
            new JButton("5"),
            new JButton("6"),
            new JButton("7"),
            new JButton("8"),
            new JButton("9")
    };

    public DrehSafe() {
        // Konfiguriere das Fenster
        setTitle("DrehSafe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridLayout(4, 3));

        // Konfigurieren der Tasten des Tastenfeldes
        for (JButton button : buttons) {
            button.setBackground(Color.green);
            button.setFont(new Font("Courier", Font.BOLD, 34));
            button.addActionListener(this);
        }

        // Konfigurieren der Anordnung der Tasten im Fenster
        int[] gridArrangement = {0, 1, 2, 9, -1, 3, 8, -1, 4, 7, 6, 5};
        for (int k : gridArrangement) {
            if (k == -1) {
                getContentPane().add(new JPanel());
            } else {
                getContentPane().add(buttons[k]);
            }
        }

        // Starte den Timer im Hintergrund
        new Thread(this).start();
    }

    public static void main(String[] args) {
        // Erstelle ein neues DrehSafe-Objekt und zeige es an
        DrehSafe drehSafe = new DrehSafe();
        drehSafe.setSize(500, 500);
        drehSafe.setVisible(true);
        drehSafe.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Füge die Benutzereingabe an die bisher eingegebene Kombination an
        pTest += ((JButton) e.getSource()).getText();
        if (PASSWORD.startsWith(pTest)) {
            // Der Benutzer hat das korrekte Passwort teilweise eingegeben
            // Setze die Farbe aller Tasten auf Grün
            for (JButton button : buttons) {
                button.setBackground(Color.green);
            }
            if (pTest.equals(PASSWORD)) {
                // Der Benutzer hat das korrekte Passwort vollständig eingegeben
                // Beende das Programm
                System.exit(0);
            }
            clockwise = true;
        } else if (e.getActionCommand().equals(PASSWORD.substring(0, 1))) {
            // Der Benutzer hat die erste Ziffer des Passworts eingegeben
            pTest = PASSWORD.substring(0, 1);
            clockwise = true;
        } else {
            // Die Benutzereingabe stimmt nicht mit dem Passwort überein
            pTest = "";
            clockwise = false;
            // Setze die Farbe aller Tasten auf Rot
            for (JButton button : buttons) {
                button.setBackground(Color.red);
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                rotate();
            } catch (InterruptedException ignored) {
            }
        }
    }

    public void rotate() throws InterruptedException {
        // Warte eine Sekunde, bevor die Zahlen rotiert werden
        Thread.sleep(1000);
        // Wenn clockwise true ist, rotiere die Zahlen im Uhrzeigersinn
        // Der aktuelle Zählerstand des Drehknopfs
        int counter;
        if (clockwise) {
            for (JButton button : buttons) {
                counter = Integer.parseInt(button.getText());
                // Reduziere den Zähler um 1, aber wenn der Zähler 0 ist, setze ihn auf 9
                counter = counter <= 0 ? 9 : counter - 1;
                button.setText("" + counter);
            }
        } else {
            // Wenn clockwise false ist, rotiere die Zahlen gegen den Uhrzeigersinn
            for (JButton button : buttons) {
                counter = Integer.parseInt(button.getText());
                // Erhöhe den Zähler um 1, aber wenn der Zähler 9 ist, setze ihn auf 0
                counter = counter >= 9 ? 0 : counter + 1;
                button.setText("" + counter);
            }
        }
    }
}