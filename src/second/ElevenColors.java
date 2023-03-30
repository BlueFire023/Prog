package second;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 30/03/2023
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ElevenColors extends Frame implements ActionListener {
    private final Color[] COLORS = {Color.black, Color.blue, Color.cyan, Color.gray, Color.green, Color.magenta, Color.orange, Color.pink, Color.red, Color.white, Color.yellow};

    private int i = 0;

    ElevenColors() {
        setLayout(new FlowLayout());
        Button[] buttons = {
                new Button("Black"),
                new Button("Blue"),
                new Button("Cyan"),
                new Button("Gray"),
                new Button("Green"),
                new Button("Magenta"),
                new Button("Orange"),
                new Button("Pink"),
                new Button("Red"),
                new Button("White"),
                new Button("Yellow")
        };
        for (Button a : buttons) {
            add(a);
            a.setActionCommand("" + i++);
            a.addActionListener(this);
        }
    }

    public static void main(String[] args) {
        WindowQuitter wQuit = new WindowQuitter();
        ElevenColors frm = new ElevenColors();
        frm.addWindowListener(wQuit);
        frm.setSize(500, 500);
        frm.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setBackground(COLORS[Integer.parseInt(e.getActionCommand())]);
    }
}
