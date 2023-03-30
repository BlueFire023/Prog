package second;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 30/03/2023
 */
public class Safe extends Frame implements ActionListener {
    private final String PASSWORD = "31032023";
    private String tester = "";
    private int i = 0;

    public Safe() {
        setLayout(new FlowLayout());
        Button[] numPad = {
                new Button("0"),
                new Button("1"),
                new Button("2"),
                new Button("3"),
                new Button("4"),
                new Button("5"),
                new Button("6"),
                new Button("7"),
                new Button("8"),
                new Button("9")
        };
        for (Button a : numPad) {
            add(a);
            a.setActionCommand("" + i++);
            a.addActionListener(this);
        }
    }

    public static void main(String[] args) {
        WindowQuitter wQuit = new WindowQuitter();
        Safe frm = new Safe();
        frm.addWindowListener(wQuit);
        frm.setSize(500, 500);
        frm.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        tester += e.getActionCommand();
        if (PASSWORD.startsWith(tester)) {
            setBackground(Color.green);
            if (tester.equals(PASSWORD)) {
                System.exit(0);
            }
        } else if (e.getActionCommand().equals(PASSWORD.substring(0,1))) {
            tester = PASSWORD.substring(0,1);
        } else {
            tester = "";
            setBackground(Color.red);
        }
    }
}
