package second;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 30/03/2023
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ElevenColors extends Frame implements ActionListener {

    ElevenColors() {
        setLayout(new FlowLayout());

        Button green = new Button("Green");
        add(green);
        green.addActionListener(this);
        green.setActionCommand("green");

        Button blue = new Button("Blue");
        add(blue);
        blue.addActionListener(this);
        blue.setActionCommand("blue");

        Button magenta = new Button("Magenta");
        add(magenta);
        magenta.addActionListener(this);
        magenta.setActionCommand("magenta");

        Button cyan = new Button("Cyan");
        add(cyan);
        cyan.addActionListener(this);
        cyan.setActionCommand("cyan");

        Button yellow = new Button("Yellow");
        add(yellow);
        yellow.addActionListener(this);
        yellow.setActionCommand("yellow");

        Button black = new Button("Black");
        add(black);
        black.addActionListener(this);
        black.setActionCommand("black");

        Button grey = new Button("Grey");
        add(grey);
        grey.addActionListener(this);
        grey.setActionCommand("gray");

        Button orange = new Button("Orange");
        add(orange);
        orange.addActionListener(this);
        orange.setActionCommand("orange");

        Button pink = new Button("Pink");
        add(pink);
        pink.addActionListener(this);
        pink.setActionCommand("pink");

        Button white = new Button("White");
        add(white);
        white.addActionListener(this);
        white.setActionCommand("white");

        Button red = new Button("Red");
        add(red);
        red.addActionListener(this);
        red.setActionCommand("red");
    }

    public static void main(String[] args) {
        WindowQuitter wQuit = new WindowQuitter();
        ElevenColors frm = new ElevenColors();
        frm.addWindowListener(wQuit);
        frm.setSize(150, 100);
        frm.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "green" -> setBackground(Color.green);
            case "blue" -> setBackground(Color.blue);
            case "magenta" -> setBackground(Color.magenta);
            case "cyan" -> setBackground(Color.cyan);
            case "yellow" -> setBackground(Color.yellow);
            case "black" -> setBackground(Color.black);
            case "gray" -> setBackground(Color.gray);
            case "orange" -> setBackground(Color.orange);
            case "pink" -> setBackground(Color.pink);
            case "white" -> setBackground(Color.white);
            case "red" -> setBackground(Color.red);
        }
    }
}
