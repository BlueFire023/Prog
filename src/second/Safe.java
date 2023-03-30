package second;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @version 1.1, 30.03.2023
 * @author Filip Schepers, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Denis Schaffer
 */

public class Safe extends Frame implements ActionListener
{
    Button btn0 = new Button("0");
    Button btn1 = new Button("1");
    Button btn2 = new Button("2");
    Button btn3 = new Button("3");
    Button btn4 = new Button("4");
    Button btn5 = new Button("5");
    Button btn6 = new Button("6");
    Button btn7 = new Button("7");
    Button btn8 = new Button("8");
    Button btn9 = new Button("9");

    public Safe()
    {
        setLayout(new FlowLayout());

        btn0.addActionListener(this);
        btn0.setActionCommand("0");
        add(btn0);

        btn1.addActionListener(this);
        btn1.setActionCommand("1");
        add(btn1);

        btn2.addActionListener(this);
        btn2.setActionCommand("2");
        add(btn2);

        btn3.addActionListener(this);
        btn3.setActionCommand("3");
        add(btn3);

        btn4.addActionListener(this);
        btn4.setActionCommand("4");
        add(btn4);

        btn5.addActionListener(this);
        btn5.setActionCommand("5");
        add(btn5);

        btn6.addActionListener(this);
        btn6.setActionCommand("6");
        add(btn6);

        btn7.addActionListener(this);
        btn7.setActionCommand("7");
        add(btn7);

        btn8.addActionListener(this);
        btn8.setActionCommand("8");
        add(btn8);

        btn9.addActionListener(this);
        btn9.setActionCommand("9");
        add(btn9);
    }

    public static void main(String[] args)
    {
        Safe nfrm = new Safe();
        nfrm.setSize(400,400);
        nfrm.setVisible(true);

        WindowQuitter wquit = new WindowQuitter();
        nfrm.addWindowListener(wquit);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        switch (e.getActionCommand())
        {
            case "0", "2", "1", "3" -> setBackground(Color.green);
            case "4", "5", "9", "7", "8", "6" -> setBackground(Color.red);
        }
    }
}
