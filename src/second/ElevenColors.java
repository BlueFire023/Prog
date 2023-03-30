package second;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 30/03/2023
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ElevenColors extends Frame implements ActionListener {
    Button green = new Button("Green");
    Button blue = new Button("Blue");
    Button red = new Button("Red");
    Button magenta = new Button("Magenta");
    Button yellow = new Button("Yellow");
    Button cyan = new Button("Cyan");
    Button black = new Button("Black");
    Button grey = new Button("Grey");
    Button orange = new Button("Orange");
    Button pink = new Button("Pink");
    Button white = new Button("White");
    ElevenColors(){
        setLayout(new FlowLayout());
        add(green);
        green.addActionListener(this);
        green.setActionCommand("green");
        add(blue);
        blue.addActionListener(this);
        blue.setActionCommand("blue");
        add(magenta);
        magenta.addActionListener(this);
        magenta.setActionCommand("magenta");
        add(cyan);
        cyan.addActionListener(this);
        cyan.setActionCommand("cyan");
        add(yellow);
        yellow.addActionListener(this);
        yellow.setActionCommand("yellow");
        add(black);
        black.addActionListener(this);
        black.setActionCommand("black");
        add(grey);
        grey.addActionListener(this);
        grey.setActionCommand("gray");
        add(orange);
        orange.addActionListener(this);
        orange.setActionCommand("orange");
        add(pink);
        pink.addActionListener(this);
        pink.setActionCommand("pink");
        add(white);
        white.addActionListener(this);
        white.setActionCommand("white");
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
        System.out.println(e.getSource().toString());
        switch (e.getActionCommand()){
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

