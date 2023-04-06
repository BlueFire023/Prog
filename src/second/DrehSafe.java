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
    private String tryout = "";
    private boolean clockwise = true;
    private int tauschwert = 0;

    private final JButton[] knoepfe = {
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

        for (JButton knopf : knoepfe)
        {
            knopf.setFont(new Font("Courier", Font.BOLD, 34));
            knopf.addActionListener(this);
        }

        getContentPane().add(knoepfe[0]);
        getContentPane().add(knoepfe[1]);
        getContentPane().add(knoepfe[2]);
        getContentPane().add(knoepfe[9]);
        getContentPane().add(new JPanel());
        getContentPane().add(knoepfe[3]);
        getContentPane().add(knoepfe[8]);
        getContentPane().add(new JPanel());
        getContentPane().add(knoepfe[4]);
        getContentPane().add(knoepfe[7]);
        getContentPane().add(knoepfe[6]);
        getContentPane().add(knoepfe[5]);
        new Thread(this).start();
    }

    public static void main(String[] args)
    {
        DrehSafe safeSafe = new DrehSafe();
        safeSafe.setSize(300, 400);
        safeSafe.setVisible(true);
    }

    private void drehen() throws InterruptedException
    {
        Thread.sleep(1000);

        if(clockwise)
        {
            for(JButton button : knoepfe)
            {
                if (tauschwert++ > 9 )
                {

                }
            }
        }

        else
        {
            for(JButton button : knoepfe)
            {
                if (tauschwert++ > 9 )
                {

                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        tryout += e.getActionCommand();
        if (PASSWORD.startsWith(tryout))
        {
            for (JButton a : knoepfe)
            {
                a.setBackground(Color.green);
            }

            if (tryout.equals(PASSWORD))
            {
                System.exit(0);
            }
            clockwise = true;
        }

        else if (e.getActionCommand().equals(PASSWORD.substring(0, 1)))
        {
            tryout = PASSWORD.substring(0, 1);
            clockwise = true;
        }

        else
        {
            tryout = "";
            clockwise = false;
            for (JButton a : knoepfe)
            {
                a.setBackground(Color.red);
            }
        }
    }

    @Override
    public void run()
    {
        while (true)
            try
            {
                drehen();
            }
        catch (InterruptedException ignored)
        {

        }
    }
}
