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
    private final String PASSWORD = "8224725301";
    private String tester = "";
    private int i = 0;
    private int z = 0;
    private boolean right = true;
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

    public DrehSafe() {
        setTitle("DrehSafe");
        getContentPane().setLayout(new GridLayout(4, 3));
        for (JButton a : numPad) {
            a.setBackground(Color.green);
            a.setFont(new Font("Courier", Font.BOLD, 34));
            a.setActionCommand("" + i++);
            a.addActionListener(this);
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

    public static void main(String[] args) {
        WindowQuitter wQuit = new WindowQuitter();
        DrehSafe frm = new DrehSafe();
        frm.addWindowListener(wQuit);
        frm.setSize(500, 500);
        frm.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        tester += e.getActionCommand();
        if (PASSWORD.startsWith(tester)) {
            for (JButton a : numPad) {
                a.setBackground(Color.green);
            }
            if (tester.equals(PASSWORD)) {
                System.exit(0);
            }
            right = true;
        } else if (e.getActionCommand().equals(PASSWORD.substring(0, 1))) {
            tester = PASSWORD.substring(0, 1);
            right = true;
        } else {
            tester = "";
            right = false;
            for (JButton a : numPad) {
                a.setBackground(Color.red);
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                rotate();
            } catch (InterruptedException e) {
            }
        }
    }

    public void rotate() throws InterruptedException {
        Thread.sleep(1000);
        if (right) {
            for (JButton a : numPad) {
                if (++z > 9) {
                    z = 0;
                }
                a.setText("" + z);
                a.setActionCommand("" + z);
            }
            z--;
        } else {
            for (JButton a : numPad) {
                a.setText("" + z);
                a.setActionCommand("" + z);
                if (++z > 9) {
                    z = 0;
                }
            }
            if (++z > 9) {
                z = 0;
            }
        }
    }
}
