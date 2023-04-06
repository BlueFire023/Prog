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
    private int counter = 9;
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
        int[] gridArrangement = {0, 1, 2, 9, -1, 3, 8, -1, 4, 7, 6, 5};
        for (int k : gridArrangement) {
            if (k == -1) {
                getContentPane().add(new JPanel());
            } else {
                getContentPane().add(buttons[k]);
            }
        }
        new Thread(this).start();
    }

    public static void main(String[] args) {
        DrehSafe frm = new DrehSafe();
        frm.setSize(500, 500);
        frm.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        pTest += ((JButton) e.getSource()).getText();
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
                counter = Integer.parseInt(button.getText());
                counter = counter <= 0 ? 9 : counter - 1;
                button.setText("" + counter);
            }
        } else {
            for (JButton button : buttons) {
                counter = Integer.parseInt(button.getText());
                counter = counter >= 9 ? 0 : counter + 1;
                button.setText("" + counter);
            }
        }
    }
}