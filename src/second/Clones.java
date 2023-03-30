package second;
/**
 * @author  Filip Schepers, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Denis Schaffer
 * @version 1, 30/03/2023
 */
import java.awt.*;
import java.awt.event.*;

public class Clones extends Frame implements ActionListener{

    Button zykButton = new Button("Zyklus");
    Button newButton = new Button("Neu");

    int index = 0;




    public Clones()
    {
        setLayout(new FlowLayout());
        zykButton.addActionListener(this);
        newButton.addActionListener(this);


        zykButton.setActionCommand("Cycle");
        newButton.setActionCommand("New");

        add(zykButton);
        add(newButton);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Cycle")){
            switch(index){
                case 0: setBackground(Color.black);
                break;
                case 1: setBackground(Color.blue);
                    break;
                case 2: setBackground(Color.cyan);
                    break;
                case 3: setBackground(Color.gray);
                    break;
                case 4: setBackground(Color.green);
                    break;
                case 5: setBackground(Color.magenta);
                    break;
                case 6: setBackground(Color.orange);
                    break;
                case 7: setBackground(Color.pink);
                    break;
                case 8: setBackground(Color.red);
                    break;
                case 9: setBackground(Color.white);
                    break;
                case 10: setBackground(Color.yellow);
                    break;
                default: break;

            }
            if(index == 10)
            {
                index = 0;
            }else{
                index++;
            }
        }else if(e.getActionCommand().equals("New")) {
            Clones dd = new Clones();
            WindowQuitter wquit = new WindowQuitter();
            dd.addWindowListener(wquit);
            dd.setSize(200, 150);
            dd.setVisible(true);
            switch (index-1) {
                case 0:
                    dd.setBackground(Color.black);
                    break;
                case 1:
                    dd.setBackground(Color.blue);
                    break;
                case 2:
                    dd.setBackground(Color.cyan);
                    break;
                case 3:
                    dd.setBackground(Color.gray);
                    break;
                case 4:
                    dd.setBackground(Color.green);
                    break;
                case 5:
                    dd.setBackground(Color.magenta);
                    break;
                case 6:
                    dd.setBackground(Color.orange);
                    break;
                case 7:
                    dd.setBackground(Color.pink);
                    break;
                case 8:
                    dd.setBackground(Color.red);
                    break;
                case 9:
                    dd.setBackground(Color.white);
                    break;
                case 10:
                    dd.setBackground(Color.yellow);
                    break;
                default:
                    break;

            }


        }
    }
    public static void main(String[] args)
    {
        Clones cc = new Clones();
        WindowQuitter wquit = new WindowQuitter();
        cc.addWindowListener(wquit);
        cc.setSize(200,150);
        cc.setVisible(true);
    }
}

