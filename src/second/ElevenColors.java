package second;

/**
 * @version 1.1, 30.03.2023
 * @author Filip Schepers, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Denis Schaffer
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

public class ElevenColors extends Frame implements ActionListener
{
    Button btnbla = new Button("schwarzer Hintergrund!");
    Button btnblu = new Button("blauer Hintergrund!");
    Button btncya = new Button("cyan Hintergrund!");
    Button btngre = new Button("grauer Hintergrund!");
    Button btngrü = new Button("grüner Hintergrund!");
    Button btnmag = new Button("magenta Hintergrund!");
    Button btnora = new Button("orangener Hintergrund!");
    Button btnpin = new Button("pinker Hintergrund!");
    Button btnred = new Button("roter Hintergrund!");
    Button btnwhi = new Button("weißer Hintergrund!");
    Button btnyel = new Button("gelber Hintergrund!");

    public ElevenColors()
    {
        setLayout(new FlowLayout());

        btnbla.addActionListener(this);
        btnbla.setActionCommand("black");
        add(btnbla);

        btnblu.addActionListener(this);
        btnble.setActionCommand("blue");
        add(btnblu);

        btncya.addActionListener(this);
        btncya.setActionCommand("cyan");
        add(btncya);

        btngre.addActionListener(this);
        btngre.setActionCommand("grey");
        add(btngre);

        btngrü.addActionListener(this);
        btngrü.setActionCommand("greed");
        add(btngrü);

        btnmag.addActionListener(this);
        btnmag.setActionCommand("magenta");
        add(btnmag);

        btnora.addActionListener(this);
        btnora.setActionCommand("orange");
        add(btnora);

        btnpin.addActionListener(this);
        btnpin.setActionCommand("pink");
        add(btnpin);

        btnred.addActionListener(this);
        btnred.setActionCommand("red");
        add(btnred);

        btnwhi.addActionListener(this);
        btnwhi.setActionCommand("white");
        add(btnwhi);

        btnyel.addActionListener(this);
        btnyel.setActionCommand("yellow");
        add(btnyel);
    }
    Color[] colors = {Color.black, Color.blue, Color.cyan, Color.gray, Color.green, Color.magenta, Color.orange, Color.pink, Color.red, Color.white, Color.yellow};
    int color = 0;

    @Override
    public void actionPerformed(ActionEvent e)
    {
        switch()
    }

    public static void main(String[] args)
    {
        Frame frme = new Frame();
        frme.setSize(300,400);
        frme.setVisible(true);

        WindowQuitter wquit = new WindowQuitter();
        wquit.addWindowListener(wquit);
    }
}
