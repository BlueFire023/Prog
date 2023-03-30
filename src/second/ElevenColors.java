package second;
/**
 * @author  Filip Schepers, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Denis Schaffer
 * @version 1, 30/03/2023
 */
import java.awt.*;
import java.awt.event.*;

public class ElevenColors extends Frame implements ActionListener{

    Button blkButton = new Button("Black");
    Button bluButton = new Button("Blue");
    Button cyaButton = new Button("Cyan");
    Button gryButton = new Button("Gray");
    Button greButton = new Button("Green");
    Button magButton = new Button("Magenta");
    Button oraButton = new Button("Orange");
    Button pikButton = new Button("Pink");
    Button redButton = new Button("Red");
    Button whtButton = new Button("White");
    Button ylwButton = new Button("Yellow");


    public ElevenColors()
    {
        setLayout(new FlowLayout());
        blkButton.addActionListener(this);
        bluButton.addActionListener(this);
        cyaButton.addActionListener(this);
        gryButton.addActionListener(this);
        greButton.addActionListener(this);
        magButton.addActionListener(this);
        oraButton.addActionListener(this);
        pikButton.addActionListener(this);
        redButton.addActionListener(this);
        whtButton.addActionListener(this);
        ylwButton.addActionListener(this);

        blkButton.setActionCommand("black");
        bluButton.setActionCommand("blue");
        cyaButton.setActionCommand("cyan");
        gryButton.setActionCommand("grey");
        greButton.setActionCommand("green");
        magButton.setActionCommand("magenta");
        oraButton.setActionCommand("orange");
        pikButton.setActionCommand("pink");
        redButton.setActionCommand("red");
        whtButton.setActionCommand("white");
        ylwButton.setActionCommand("yellow");

        add(blkButton);
        add(bluButton);
        add(cyaButton);
        add(gryButton);
        add(greButton);
        add(magButton);
        add(oraButton);
        add(pikButton);
        add(redButton);
        add(whtButton);
        add(ylwButton);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "black":
                setBackground(Color.black);
                break;
            case "blue":
                setBackground(Color.blue);
                break;
            case "cyan":
                setBackground(Color.cyan);
                break;
            case "grey":
                setBackground(Color.gray);
                break;
            case "green":
                setBackground(Color.green);
                break;
            case "magenta":
                setBackground(Color.magenta);
                break;
            case "orange":
                setBackground(Color.orange);
                break;
            case "pink":
                setBackground(Color.pink);
                break;
            case "red":
                setBackground(Color.red);
                break;
            case "white":
                setBackground(Color.white);
                break;
            case "yellow":
                setBackground(Color.yellow);
                break;
            default: break;
        }


    }
    public static void main(String[] args)
    {
        ElevenColors eC = new ElevenColors();
        WindowQuitter wquit = new WindowQuitter();
        eC.addWindowListener(wquit);
        eC.setSize(1000,750);
        eC.setVisible(true);
    }
}
