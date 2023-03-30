package second;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 30/03/2023
 */
public class Clones extends Frame implements ActionListener {
    private final Color[] COLORS = {Color.black, Color.blue, Color.cyan, Color.gray, Color.green, Color.magenta, Color.orange, Color.pink, Color.red, Color.white, Color.yellow};
    private int index;

    public Clones(int index) {
        setLayout(new FlowLayout());

        Button cycleButton = new Button("Next Color");
        add(cycleButton);
        cycleButton.addActionListener(this);
        cycleButton.setActionCommand("C");

        Button newWindowButton = new Button("New Window");
        add(newWindowButton);
        newWindowButton.addActionListener(this);
        newWindowButton.setActionCommand("W");

        this.index = index;
        setBackground(COLORS[index]);
    }

    public static void main(String[] args) {
        WindowQuitter wQuit = new WindowQuitter();
        Clones frm = new Clones(0);
        frm.addWindowListener(wQuit);
        frm.setSize(500, 500);
        frm.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("W")) {
            WindowQuitter wQuit = new WindowQuitter();
            Clones frm = new Clones(index);
            frm.addWindowListener(wQuit);
            frm.setSize(500, 500);
            frm.setVisible(true);
        } else {
            if (index != 10) {
                index++;
                setBackground(COLORS[index]);
            } else {
                index = 0;
                setBackground(COLORS[index]);
            }
        }
    }
}
