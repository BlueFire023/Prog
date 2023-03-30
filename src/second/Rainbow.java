package second;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 30/03/2023
 */
public class Rainbow extends Frame implements ActionListener, Runnable {
    private final Color[] COLORS = {Color.black, Color.blue, Color.cyan, Color.gray, Color.green, Color.magenta, Color.orange, Color.pink, Color.red, Color.white, Color.yellow};
    public static int index = 0;

    public Rainbow() {
        setLayout(new FlowLayout());
        Button nWind = new Button("New Rainbow");
        add(nWind);
        nWind.addActionListener(this);
        new Thread(this).start();
    }

    public static void main(String[] args) {
        WindowQuitter wQuit = new WindowQuitter();
        Rainbow frm = new Rainbow();
        frm.addWindowListener(wQuit);
        frm.setSize(500, 500);
        frm.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        WindowQuitter wQuit = new WindowQuitter();
        Rainbow frm = new Rainbow();
        frm.addWindowListener(wQuit);
        frm.setSize(500, 500);
        frm.setVisible(true);
    }

    public void changeColor() throws InterruptedException {
        Thread.sleep(1000);
        if (index < 10) {
            setBackground(COLORS[index]);
            index++;
        } else {
            setBackground(COLORS[index]);
            index = 0;
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                changeColor();
            } catch (Exception e) {
            }
        }
    }
}
