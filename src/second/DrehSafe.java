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
    private String pTest = "";
    private int z = 0;
    private boolean clockwise = true;
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
        setTitle("DrehSafe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridLayout(4, 3));
        for (JButton button : buttons) {
            button.setBackground(Color.green);
            button.setFont(new Font("Courier", Font.BOLD, 34));
            button.addActionListener(this);
        }
        getContentPane().add(buttons[0]);
        getContentPane().add(buttons[1]);
        getContentPane().add(buttons[2]);
        getContentPane().add(buttons[9]);
        getContentPane().add(new JPanel());
        getContentPane().add(buttons[3]);
        getContentPane().add(buttons[8]);
        getContentPane().add(new JPanel());
        getContentPane().add(buttons[4]);
        getContentPane().add(buttons[7]);
        getContentPane().add(buttons[6]);
        getContentPane().add(buttons[5]);
        new Thread(this).start();
    }

    public static void main(String[] args) {
        DrehSafe frm = new DrehSafe();
        frm.setSize(500, 500);
        frm.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        pTest += e.getActionCommand();
        if (PASSWORD.startsWith(pTest)) {
            for (JButton button : buttons) {
                button.setBackground(Color.green);
            }
            if (pTest.equals(PASSWORD)) {
                System.exit(0);
            }
            clockwise = true;
        } else if (e.getActionCommand().equals(PASSWORD.substring(0, 1))) {
            pTest = PASSWORD.substring(0, 1);
            clockwise = true;
        } else {
            pTest = "";
            clockwise = false;
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
        Thread.sleep(1000);
        if (clockwise) {
            for (JButton button : buttons) {
                if (++z > 9) {
                    z = 0;
                }
                button.setText("" + z);
                button.setActionCommand("" + z);
            }
            z--;
        } else {
            for (JButton button : buttons) {
                if (++z > 9) {
                    z = 0;
                }
                button.setText("" + z);
                button.setActionCommand("" + z);
            }
            if (++z > 9) {
                z = 0;
            }
        }
    }
}
