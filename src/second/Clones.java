package second;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 30/03/2023
 */
public class Clones extends Frame implements ActionListener {
    Button cyclon = new Button("Next Color");
    Button nWind = new Button("New Window");
    Color[] cyclic = {Color.black, Color.blue, Color.cyan, Color.gray, Color.green, Color.magenta, Color.orange, Color.pink, Color.red, Color.white, Color.yellow};
    int index = 0;
    public Clones(){
        setLayout(new FlowLayout());
        add(cyclon);
        cyclon.addActionListener(this);
        cyclon.setActionCommand("C");
        add(nWind);
        nWind.addActionListener(this);
        nWind.setActionCommand("W");
    }

    public static void main(String[] args) {
        WindowQuitter wQuit = new WindowQuitter();
        Clones frm = new Clones();
        frm.addWindowListener(wQuit);
        frm.setSize(500,500);
        frm.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("W")){
            WindowQuitter wQuit = new WindowQuitter();
            Clones frm = new Clones();
            frm.addWindowListener(wQuit);
            frm.setSize(500,500);
            frm.setVisible(true);
        }
        else{
            if(index != 10){
                setBackground(cyclic[index]);
                index++;
            }else{
                index = 0;
            }
        }
    }
}
