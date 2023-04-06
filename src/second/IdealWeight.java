package second;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 06/04/2023
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class IdealWeight extends JFrame implements ActionListener
{
    JRadioButton genderM, genderF, genderD;
    ButtonGroup genderGroup;
    JPanel genderPanel;

    JRadioButton heightA, heightB, heightC, heightD, heightE;
    ButtonGroup heightGroup;
    JPanel heightPanel;

    JTextField resultText;
    JLabel resultLabel;
    JPanel resultPanel;

    public IdealWeight()
    {
        setTitle("'Idealgewicht' Berechner");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Geschlechts-Gruppe
        genderM = new JRadioButton("Male", true );
        genderF = new JRadioButton("Female", false );
        genderD = new JRadioButton("Diverse", false);
        genderGroup = new ButtonGroup();
        genderGroup.add( genderM );
        genderGroup.add( genderF );
        genderGroup.add( genderD );
        genderPanel = new JPanel();
        genderPanel.setLayout(new BoxLayout( genderPanel, BoxLayout.Y_AXIS ) );
        genderPanel.add( new JLabel(" Your Gender (all Genders are welcome!)") );
        genderPanel.add( genderM );
        genderPanel.add( genderF );
        genderPanel.add( genderD );
        genderM.addActionListener(this);
        genderF.addActionListener(this);
        genderD.addActionListener(this);

        // Hoehen-Gruppe
        heightA = new JRadioButton("60 to 64 inches", true );
        heightB = new JRadioButton("64 to 68 inches", false );
        heightC = new JRadioButton("68 to 72 inches", false );
        heightD = new JRadioButton("72 to 76 inches", false );
        heightE = new JRadioButton("76 to 80 inches", false );
        heightGroup = new ButtonGroup();
        heightGroup.add( heightA );
        heightA.addActionListener(this);
        heightGroup.add( heightB );
        heightB.addActionListener(this);
        heightGroup.add( heightC );
        heightC.addActionListener(this);
        heightGroup.add( heightD );
        heightD.addActionListener(this);
        heightGroup.add( heightE );
        heightE.addActionListener(this);

        heightPanel = new JPanel();
        heightPanel.setLayout(new BoxLayout( heightPanel, BoxLayout.Y_AXIS ) );
        heightPanel.add( new JLabel("Your Height") );
        heightPanel.add( heightA );
        heightPanel.add( heightB );
        heightPanel.add( heightC );
        heightPanel.add( heightD );
        heightPanel.add( heightE );

        // Ergebnis-Panel
        resultText = new JTextField(7);
        resultText.setEditable( false );
        resultLabel = new JLabel("'Ideal Weight' (as long as you stay healthy, your weight can just be your weight)");
        resultPanel = new JPanel();
        resultPanel.add( resultLabel );
        resultPanel.add( resultText );

        // Gesamt-Fenster
        getContentPane().setLayout( new BorderLayout() );
        getContentPane().add( genderPanel, BorderLayout.WEST );
        getContentPane().add( heightPanel, BorderLayout.EAST );
        getContentPane().add( resultPanel, BorderLayout.SOUTH );
    }

    public static void main(String[] args)
    {
        IdealWeight weightApp = new IdealWeight() ;
        weightApp.setSize( 600, 500 );
        weightApp.setVisible( true );
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        int genderConstant = genderM.isSelected()? 30 : genderF.isSelected()? 28 : 29;
        int heightConstant = heightA.isSelected()? 60 : heightB.isSelected()? 64 : heightC.isSelected()? 68 : heightC.isSelected()? 72 : 78;
        resultText.setText(((heightConstant*heightConstant)/genderConstant) + "pounds");
    }
}
