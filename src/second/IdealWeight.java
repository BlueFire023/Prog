package second;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 06/04/2023
 */
public class IdealWeight extends JFrame implements ActionListener {
    private JRadioButton genderM, genderF, genderD;
    private ButtonGroup genderGroup;
    private JPanel genderPanel;
    private JRadioButton heightA, heightB, heightC, heightD, heightE;
    private ButtonGroup heightGroup;
    private JPanel heightPanel;
    private JTextField resultText;
    private JLabel resultLabel;
    private JPanel resultPanel;
    private boolean male = true;
    private float height = 62f;

    public IdealWeight() {
        setTitle("Your 'Ideal Weight'");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Erstellt die beiden Radio-Buttons für Geschlecht (Male und Female) und fügt sie einer ButtonGroup hinzu
        genderM = new JRadioButton("Male", true);
        genderM.setActionCommand("M");
        genderM.addActionListener(this);
        genderF = new JRadioButton("Female", false);
        genderF.setActionCommand("F");
        genderF.addActionListener(this);
        genderD = new JRadioButton("Diverse", false);
        genderD.setActionCommand("Di");
        genderD.addActionListener(this);
        genderGroup = new ButtonGroup();
        genderGroup.add(genderM);
        genderGroup.add(genderF);
        genderGroup.add(genderD);

        // Erstellt ein JPanel für die Geschlechtsauswahl, fügt eine Beschriftung hinzu und die beiden Radio-Buttons
        genderPanel = new JPanel();
        genderPanel.setLayout(new BoxLayout(genderPanel, BoxLayout.Y_AXIS));
        genderPanel.add(new JLabel(" Your Gender"));
        genderPanel.add(genderM);
        genderPanel.add(genderF);
        genderPanel.add(genderD);

        // Erstellt die Radio-Buttons für die Größen-Auswahl und fügt sie einer ButtonGroup hinzu
        heightA = new JRadioButton("60 to 64 inches", true);
        heightA.setActionCommand("A");
        heightA.addActionListener(this);
        heightB = new JRadioButton("64 to 68 inches", false);
        heightB.setActionCommand("B");
        heightB.addActionListener(this);
        heightC = new JRadioButton("68 to 72 inches", false);
        heightC.setActionCommand("C");
        heightC.addActionListener(this);
        heightD = new JRadioButton("72 to 76 inches", false);
        heightD.setActionCommand("D");
        heightD.addActionListener(this);
        heightE = new JRadioButton("76 to 80 inches", false);
        heightE.setActionCommand("E");
        heightE.addActionListener(this);
        heightGroup = new ButtonGroup();
        heightGroup.add(heightA);
        heightGroup.add(heightB);
        heightGroup.add(heightC);
        heightGroup.add(heightD);
        heightGroup.add(heightE);

        // Erstellt ein JPanel für die Größen-Auswahl, fügt eine Beschriftung hinzu und die Radio-Buttons
        heightPanel = new JPanel();
        heightPanel.setLayout(new BoxLayout(heightPanel, BoxLayout.Y_AXIS));
        heightPanel.add(new JLabel("Your Height"));
        heightPanel.add(heightA);
        heightPanel.add(heightB);
        heightPanel.add(heightC);
        heightPanel.add(heightD);
        heightPanel.add(heightE);

        // Erstellt ein JTextField für die Ergebnis-Anzeige und setzt es auf nicht editierbar
        resultText = new JTextField(7);
        resultText.setText(String.format("%.02f", calculateIdealWeight(male, height)));
        resultText.setEditable(false);

        // Erstellt eine JLabel für das Ergebnis und ein JPanel für die Anzeige des Ergebnisses
        resultLabel = new JLabel("'Ideal Weight'");
        resultPanel = new JPanel();
        resultPanel.add(resultLabel);
        resultPanel.add(resultText);

        // Setzt das Layout des Haupt-Panels auf BorderLayout und fügt die anderen Panels hinzu
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(genderPanel, BorderLayout.WEST);
        getContentPane().add(heightPanel, BorderLayout.EAST);
        getContentPane().add(resultPanel, BorderLayout.SOUTH);
    }

    // Die main-Methode erzeugt eine Instanz der Klasse IdealWeight, stellt die Größe des Fensters auf 250 x 225 ein
    // und macht das Fenster sichtbar.
    public static void main(String[] args) {
        IdealWeight weightApp = new IdealWeight();
        weightApp.setSize(250, 225);
        weightApp.setVisible(true);
    }

    // Diese Methode berechnet das ideale Gewicht auf der Grundlage von Geschlecht (männlich/weiblich) und Höhe.
    // Wenn der Benutzer männlich ist, wird die Formel (Höhe * Höhe) / 30 verwendet, andernfalls (Höhe * Höhe) / 28.
    public static float calculateIdealWeight(boolean male, float height) {
        if (male) {
            return (height * height) / 30;
        } else {
            return (height * height) / 28;
        }
    }

    // Diese Methode wird aufgerufen, wenn der Benutzer eine Aktion ausführt (z.B. einen RadioButton auswählt).
    // Schließlich wird das berechnete ideale Gewicht im Textfeld angezeigt.
    @Override
    public void actionPerformed(ActionEvent e) {
        int genderConstant = genderM.isSelected()? 30 : genderF.isSelected()? 28 : 29;
        int heightConstant = heightA.isSelected()? 60 : heightB.isSelected()? 64 : heightC.isSelected()? 68 : heightC.isSelected()? 72 : 78;
        resultText.setText(((heightConstant*heightConstant)/genderConstant) + " pounds");
    }
}
