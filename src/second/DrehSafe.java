package second;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 06/04/2023
 */

public class DrehSafe extends JFrame implements ActionListener, Runnable
{
    /*Zusätzlich soll sich die Beschriftung (und damit die Wirkung) der Knöpfe im Sekundentakt um eine Position drehen, zunächst nach rechts.
    Bei jedem falschen Knopfdruck ändert sich allerdings die Drehrichtung.*/

    private final String PASSWORD = "8224725301";
    private String tester = "";

    private final JButton[] numPad = {
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

    public DrehSafe()
    {
        setTitle(" DrehSafe ");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        getContentPane().setLayout(new GridLayout(4, 3));

        for (JButton knopf : numPad) {
            knopf.setFont(new Font("Courier", Font.BOLD, 34));
            knopf.addActionListener(this);
        }

        getContentPane().add(numPad[0]);
        getContentPane().add(numPad[1]);
        getContentPane().add(numPad[2]);
        getContentPane().add(numPad[9]);
        getContentPane().add(new JPanel());
        getContentPane().add(numPad[3]);
        getContentPane().add(numPad[8]);
        getContentPane().add(new JPanel());
        getContentPane().add(numPad[4]);
        getContentPane().add(numPad[7]);
        getContentPane().add(numPad[6]);
        getContentPane().add(numPad[5]);
        new Thread(this).start();
    }

    public static void main(String[] args)
    {
        DrehSafe safeSafe = new DrehSafe();
        safeSafe.setSize(300, 400);
        safeSafe.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {

    }

    @Override
    public void run()
    {

    }
}
