package second;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 06/04/2023
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class ToggleSafe extends JFrame implements ActionListener, Runnable {
    // Das korrekte Passwort, um den Safe zu öffnen
    private final String PASSWORD = "8529630741";
    // Das vom Benutzer eingegebene Passwort
    private String pTest = "";
    // Die Drehrichtung des Tastenfeldes
    private boolean clockwise = true;
    private final double waitTime;
    private static int openWindows;
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

    private ToggleSafe(double waitTime, int[] gridArrangement) {
        // Konfiguriere das Fenster
        setTitle("ToggleSafe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridLayout(4, 3));
        setSize(500, 500);
        setLocation(100, 100);
        setVisible(true);
        setResizable(false);
        openWindows++;

        this.waitTime = waitTime;
        // Konfigurieren der Tasten des Tastenfeldes
        for (JButton button : buttons) {
            button.setBackground(Color.red);
            button.setFont(new Font("Courier", Font.BOLD, 34));
            button.addActionListener(this);
        }

        // Konfigurieren der Anordnung der Tasten im Fenster
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
        // Erstelle ein neues DrehSafe-Objekt
        new ToggleSafe(1000, new int[]{0, 1, 2, 9, -1, 3, 8, -1, 4, 7, 6, 5});
    }

    private void newWindow() {
        // Erstelle ein neues DrehSafe-Objekt mit 33% schnellerer Geschwindigkeit
        new ToggleSafe(waitTime - (waitTime * 0.33), getGridArrangement());
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
                this.dispose();
                openWindows--;
                if (openWindows == 0) {
                    System.exit(0);
                }
            }
            //clockwise = true;
        } else if (e.getActionCommand().equals(PASSWORD.substring(0, 1))) {
            // Der Benutzer hat die erste Ziffer des Passworts eingegeben
            pTest = PASSWORD.substring(0, 1);
        } else {
            // Die Benutzereingabe stimmt nicht mit dem Passwort überein
            pTest = "";
            newWindow();
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

    private void rotate() throws InterruptedException {
        // Warte eine Sekunde, bevor die Zahlen rotiert werden
        Thread.sleep((long) waitTime);
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
        if (((JButton) getContentPane().getComponent(0)).getText().equals("0")) {
            clockwise = !clockwise;
        }
    }

    private int[] getGridArrangement() {
        int[] gridArrangement = new int[12];
        for (int i = 0; i < gridArrangement.length; i++) {
            if (getContentPane().getComponent(i) instanceof JButton) {
                gridArrangement[i] = Integer.parseInt(((JButton) getContentPane().getComponent(i)).getText());
            } else {
                gridArrangement[i] = -1;
            }
        }
        return gridArrangement;
    }
}
