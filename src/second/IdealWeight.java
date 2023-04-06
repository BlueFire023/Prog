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
    private JRadioButton genderM, genderF;
    private ButtonGroup genderGroup;
    private JPanel genderPanel;
    private JRadioButton heightA, heightB, heightC, heightD, heightE;
    private ButtonGroup heightGroup;
    private JPanel heightPanel;
    private JTextField resultText;
    private JLabel resultLabel;
    private JPanel resultPanel;
    private boolean male = true;
    private float height;

    public IdealWeight() {
        setTitle("Your Ideal Weight");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        genderM = new JRadioButton("Male", true);
        genderM.setActionCommand("M");
        genderM.addActionListener(this);
        genderF = new JRadioButton("Female", false);
        genderF.setActionCommand("F");
        genderF.addActionListener(this);

        genderGroup = new ButtonGroup();
        genderGroup.add(genderM);
        genderGroup.add(genderF);

        genderPanel = new JPanel();
        genderPanel.setLayout(new BoxLayout(genderPanel, BoxLayout.Y_AXIS));
        genderPanel.add(new JLabel("Your Gender"));
        genderPanel.add(genderM);
        genderPanel.add(genderF);

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

        heightPanel = new JPanel();
        heightPanel.setLayout(new BoxLayout(heightPanel, BoxLayout.Y_AXIS));
        heightPanel.add(new JLabel("Your Height"));
        heightPanel.add(heightA);
        heightPanel.add(heightB);
        heightPanel.add(heightC);
        heightPanel.add(heightD);
        heightPanel.add(heightE);

        resultText = new JTextField(7);
        resultText.setEditable(false);

        resultLabel = new JLabel("Ideal Weight");

        resultPanel = new JPanel();
        resultPanel.add(resultLabel);
        resultPanel.add(resultText);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(genderPanel, BorderLayout.WEST);
        getContentPane().add(heightPanel, BorderLayout.EAST);
        getContentPane().add(resultPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        IdealWeight weightApp = new IdealWeight();
        weightApp.setSize(250, 225);
        weightApp.setVisible(true);
    }

    public static float calculateIdealWeight(boolean male, float height) {
        if (male) {
            return (height * height) / 30;
        } else {
            return (height * height) / 28;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "M" -> male = true;
            case "F" -> male = false;
            case "A" -> height = 62f;
            case "B" -> height = 66f;
            case "C" -> height = 70f;
            case "D" -> height = 74f;
            case "E" -> height = 78f;
        }
        resultText.setText("" + calculateIdealWeight(male, height));
    }
}
