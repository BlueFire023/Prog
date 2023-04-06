package second;

import java.awt.* ;
import java.awt.event.*;
import javax.swing.*;
public class IdealWeight extends JFrame implements ActionListener { // sollte zusätzlich ActionListener sein
    JRadioButton genderM, genderF; // Knöpfe für Geschlecht
    ButtonGroup genderGroup; // ... dazu Knopfgruppe
    JPanel genderPanel; // ... dazu Panel
    JRadioButton heightA, heightB, heightC, heightD, heightE; // Kn. Grösse
    ButtonGroup heightGroup; // ... Gruppe
    JPanel heightPanel; // ... Panel
    JTextField resultText; // Textfeld für Ergebnis
    JLabel resultLabl; // ... dazu Label
    JPanel resultPanel; // ... dazu Panel

    public IdealWeight() { // Konstruktor
        setTitle("Your Ideal Weight"); // Fenstertitel
        setDefaultCloseOperation(EXIT_ON_CLOSE); // zum Fensterschliessen
        // Geschlechts-Gruppe
        genderM = new JRadioButton("Male", true); // Knopf M. selekt.
        genderF = new JRadioButton("Female", false); // Knopf F. nicht s.
        genderGroup = new ButtonGroup(); // Gruppe def.
        genderGroup.add(genderM);
        genderGroup.add(genderF); // Kn. hinzufuegen
        genderPanel = new JPanel(); // G.-Panel
        genderPanel.setLayout( // Layout
                new BoxLayout(genderPanel, BoxLayout.Y_AXIS)); // ... vertikal
        genderPanel.add(new JLabel("Your Gender")); // Label &
        genderPanel.add(genderM);
        genderPanel.add(genderF); // Knoepfe hinzuf.
        // Hoehen-Gruppe
        heightA = new JRadioButton("60 to 64 inches", true); // ... selektiert

        heightB = new JRadioButton("64 to 68 inches", false); // nicht selektiert

        heightC = new JRadioButton("68 to 72 inches", false); // ...

        heightD = new JRadioButton("72 to 76 inches", false); // ...

        heightE = new JRadioButton("76 to 80 inches", false); // ...

        heightGroup = new ButtonGroup(); // Gruppe def.
        heightGroup.add(heightA);
        heightGroup.add(heightB); // Kn.
        heightGroup.add(heightC);
        heightGroup.add(heightD); // ... hinzufuegen
        heightGroup.add(heightE);
        heightPanel = new JPanel(); // H-Panel
        heightPanel.setLayout( // Layout
                new BoxLayout(heightPanel, BoxLayout.Y_AXIS)); // ... vertikal
        heightPanel.add(new JLabel("Your Height")); // Label &
        heightPanel.add(heightA);
        heightPanel.add(heightB); // Kn. hinzufuegen
        heightPanel.add(heightC);
        heightPanel.add(heightD); // ...
        heightPanel.add(heightE); // ...
// Ergebnis-Panel
        resultText = new JTextField(7); // Textfeld
        resultText.setEditable(false); // ... nur Ausgabe
        resultLabl = new JLabel("Ideal Weight"); // Label def.
        resultPanel = new JPanel(); // Panel def.
        resultPanel.add(resultLabl); // Label hinzufuegen
        resultPanel.add(resultText); // Textfeld ...




// Gesamt-Fenster
        getContentPane().setLayout(new BorderLayout()); // Layout: Border
        getContentPane().add(genderPanel, BorderLayout.WEST); // G-Panel hinzuf.
        getContentPane().add(heightPanel, BorderLayout.EAST); // H-Panel ...
        getContentPane().add(resultPanel, BorderLayout.SOUTH); // Ergebnis-Panel ..
        heightA.addActionListener(this);

        heightB.addActionListener(this);

        heightC.addActionListener(this);
        heightD.addActionListener(this);

        heightE.addActionListener(this);






    }

    public static void main(String[] args) { // main ...
        IdealWeight weightApp = new IdealWeight();
        weightApp.setSize(250, 225);
        weightApp.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        int genderConstant = genderM.isSelected()? 30 : 28;
        int heightConstant = heightA.isSelected()? 60 :
                heightB.isSelected()? 64 :
                        heightC.isSelected()? 68 :
                                heightC.isSelected()? 72 : 78;
        resultText.setText(((heightConstant*heightConstant)/genderConstant) + "pounds");

    }
}
