package second;
/**
 * @author  Filip Schepers, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Denis Schaffer
 * @version 1, 30/03/2023
 */
import java.awt.*;
import java.awt.event.*;


public class Safe extends Frame implements ActionListener{

    Button zeroButton = new Button("0");
    Button oneButton = new Button("1");
    Button twoButton = new Button("2");
    Button threeButton = new Button("3");
    Button fourButton = new Button("4");
    Button fiveButton = new Button("5");
    Button sixButton = new Button("6");
    Button sevenButton = new Button("7");
    Button eightButton = new Button("8");
    Button nineButton = new Button("9");

    int[] rIndex = new int[]{3,1,0,3,2,0,2,3};
    int index = 1;



    public Safe()
    {
        setLayout(new FlowLayout());
        zeroButton.addActionListener(this);
        oneButton.addActionListener(this);
        twoButton.addActionListener(this);
        threeButton.addActionListener(this);
        fourButton.addActionListener(this);
        fiveButton.addActionListener(this);
        sixButton.addActionListener(this);
        sevenButton.addActionListener(this);
        eightButton.addActionListener(this);
        nineButton.addActionListener(this);

        zeroButton.setActionCommand("0");
        oneButton.setActionCommand("1");
        twoButton.setActionCommand("2");
        threeButton.setActionCommand("3");
        fourButton.setActionCommand("4");
        fiveButton.setActionCommand("5");
        sixButton.setActionCommand("6");
        sevenButton.setActionCommand("7");
        eightButton.setActionCommand("8");
        nineButton.setActionCommand("9");


        add(zeroButton);
        add(oneButton);
        add(twoButton);
        add(threeButton);
        add(fourButton);
        add(fiveButton);
        add(sixButton);
        add(sevenButton);
        add(eightButton);
        add(nineButton);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "0":
                if(index == 3 || index == 6)
                {
                    index++;
                    setBackground(Color.green);
                }else {
                    index = 1;
                    setBackground(Color.red);
                }
                break;
            case "1":
                if(index == 2)
                {
                    index++;
                    setBackground(Color.green);
                }else {
                    index = 1;
                    setBackground(Color.red);
                }
                break;
            case "2":
                if(index == 5 || index == 7)
                {
                    index++;
                    setBackground(Color.green);
                }else {
                    index = 1;
                    setBackground(Color.red);
                }
                break;
            case "3":
                if(index == 1 || index == 4 || index == 8)
                {
                    if(index == 8){
                        System.exit(0);
                    }
                    index++;
                    setBackground(Color.green);
                }else {
                    index = 2;
                    setBackground(Color.red);
                }
                break;
            case "4", "5", "6", "7", "8", "9":
                index = 1;
                setBackground(Color.red);
                break;

            default: break;
        }


    }
    public static void main(String[] args)
    {
        Safe sehrSichererSafe = new Safe();
        WindowQuitter wquit = new WindowQuitter();
        sehrSichererSafe.addWindowListener(wquit);
        sehrSichererSafe.setSize(1000,750);
        sehrSichererSafe.setVisible(true);
    }
}