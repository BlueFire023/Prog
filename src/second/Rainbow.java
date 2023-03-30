package second;
/**
 * @author  Filip Schepers, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Denis Schaffer
 * @version 1, 30/03/2023
 */
import java.awt.*;
import java.awt.event.*;
public class Rainbow extends Frame implements ActionListener, Runnable{

    Button zykButton = new Button("New Window");



    public Rainbow(){
        setLayout(new FlowLayout());
        zykButton.addActionListener(this);

        zykButton.setActionCommand("Cycle");

        add(zykButton);

        new Thread(this).start();


    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Cycle")){

            Rainbow rB = new Rainbow();
            WindowQuitter wquit = new WindowQuitter();
            rB.addWindowListener(wquit);
            rB.setSize(200,150);
            rB.setVisible(true);

        }

    }

    public void run(){
        while(true)
        {
            try {
                changeColor();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void changeColor() throws InterruptedException {


            setBackground(Color.red);
            Thread.sleep(1000);
            setBackground(Color.orange);
            Thread.sleep(1000);
            setBackground(Color.yellow);
            Thread.sleep(1000);
            setBackground(Color.green);
            Thread.sleep(1000);
            setBackground(Color.blue);
            Thread.sleep(1000);
            setBackground(Color.magenta);


    }
    public static void main(String[] args) throws InterruptedException {
        Rainbow rA = new Rainbow();
        WindowQuitter wquit = new WindowQuitter();
        rA.addWindowListener(wquit);
        rA.setSize(200,150);
        rA.setVisible(true);


            rA.changeColor();


    }
}
