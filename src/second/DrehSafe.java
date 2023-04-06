package second;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * @author  Filip Schepers, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Denis Schaffer
 * @version 1, 06/04/2023
 */
public class DrehSafe extends JFrame implements ActionListener, Runnable{

    int richtung = 0;
    int index = 1;
    ArrayList<JButton> buttons = new ArrayList<>();
    public DrehSafe()
    {
        setTitle("DrehSafe");
        setLayout(new GridLayout(4,3));
        for(int i = 3; i>=1; i-- ) {
            JButton b = new JButton("" + i);
            b.setFont(new Font("Courier", Font.BOLD, 34));
            b.addActionListener(this);
            add(b);
            buttons.add(b);
        }
        JButton c = new JButton("4");
        c.setFont(new Font("Courier", Font.BOLD, 34));
        c.addActionListener(this);
        add(c);
        buttons.add(c);

        JPanel j = new JPanel();
        add(j);


        JButton d = new JButton("0");
        d.setFont(new Font("Courier", Font.BOLD, 34));
        d.addActionListener(this);
        add(d);
        buttons.add(d);

        JButton e = new JButton("5");
        e.setFont(new Font("Courier", Font.BOLD, 34));
        e.addActionListener(this);
        add(e);
        buttons.add(e);

        JPanel k = new JPanel();
        add(k);

        JButton f = new JButton("9");
        f.setFont(new Font("Courier", Font.BOLD, 34));
        f.addActionListener(this);
        add(f);
        buttons.add(f);

        for(int i = 6; i<=8; i++ ) {
            JButton b = new JButton("" + i);
            b.setFont(new Font("Courier", Font.BOLD, 34));
            b.addActionListener(this);
            add(b);
            buttons.add(b);
        }

        setSize(300,400);
        setLocation(300,300);
        setVisible(true);


        try {
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
        } catch (Exception o) {
            o.printStackTrace();
        }


        for(int i = 0; i<=9;i++) {
            buttons.get(i).setOpaque(true);
            buttons.get(i).setBackground(Color.white);

        }


        new Thread(this).start();


    }

    public static void main(String[] args)
    {
        DrehSafe sichererSafe = new DrehSafe();
        WindowQuitter wquit = new WindowQuitter();
        sichererSafe.addWindowListener(wquit);

    }
    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "0":
                if(index == 9)
                {
                    index++;
                    for(int i = 0; i<=9;i++) {
                        buttons.get(i).setBackground(Color.green);
                    }
                }else {
                    index = 1;
                    for(int i = 0; i<=9;i++) {
                        buttons.get(i).setBackground(Color.red);
                    }
                    if(richtung == 0){
                        richtung = 1;
                    }else {
                        richtung = 0;
                    }
                }
                break;
            case "1":
                if(index == 10)
                {
                    System.exit(0);
                }else {
                    index = 1;
                    for(int i = 0; i<=9;i++) {
                        buttons.get(i).setBackground(Color.red);
                    }
                    if(richtung == 0){
                        richtung = 1;
                    }else {
                        richtung = 0;
                    }
                }

                break;
            case "2":
                if(index == 2 || index == 3 || index ==6)
                {
                    index++;
                    for(int i = 0; i<=9;i++) {
                        buttons.get(i).setBackground(Color.green);
                    }
                }else {
                    index = 1;
                    for(int i = 0; i<=9;i++) {
                        buttons.get(i).setBackground(Color.red);
                    }
                    if(richtung == 0){
                        richtung = 1;
                    }else {
                        richtung = 0;
                    }
                }

                break;
            case "3":
                if(index == 8)
                {
                    index++;
                    for(int i = 0; i<=9;i++) {
                        buttons.get(i).setBackground(Color.green);
                    }
                }else {
                    index = 1;
                    for(int i = 0; i<=9;i++) {
                        buttons.get(i).setBackground(Color.red);
                    }
                    if(richtung == 0){
                        richtung = 1;
                    }else {
                        richtung = 0;
                    }
                }

                break;
            case "4":
                if(index == 4)
                {
                    index++;
                    for(int i = 0; i<=9;i++) {
                        buttons.get(i).setBackground(Color.green);
                    }
                }else {
                    index = 1;
                    for(int i = 0; i<=9;i++) {
                        buttons.get(i).setBackground(Color.red);
                    }
                    if(richtung == 0){
                        richtung = 1;
                    }else {
                        richtung = 0;
                    }
                }

                break;
            case "5":
                if(index == 7)
                {
                    index++;
                    for(int i = 0; i<=9;i++) {
                        buttons.get(i).setBackground(Color.green);
                    }
                }else {
                    index = 1;
                    for(int i = 0; i<=9;i++) {
                        buttons.get(i).setBackground(Color.red);
                    }
                    if(richtung == 0){
                        richtung = 1;
                    }else {
                        richtung = 0;
                    }
                }

                break;
            case "7":
                if(index == 5)
                {
                    index++;
                    for(int i = 0; i<=9;i++) {
                        buttons.get(i).setBackground(Color.green);
                    }
                }else {
                    index = 1;
                    for(int i = 0; i<=9;i++) {
                        buttons.get(i).setBackground(Color.red);
                    }
                    if(richtung == 0){
                        richtung = 1;
                    }else {
                        richtung = 0;
                    }
                }

                break;
            case "8":
                if(index == 1)
                {
                    index++;
                    for(int i = 0; i<=9;i++) {
                        buttons.get(i).setBackground(Color.green);
                    }
                }else {
                    index = 1;
                    for(int i = 0; i<=9;i++) {
                        buttons.get(i).setBackground(Color.red);
                    }
                    if(richtung == 0){
                        richtung = 1;
                    }else {
                        richtung = 0;
                    }
                }

                break;
            case "6", "9":
                index = 1;
                for(int i = 0; i<=9;i++) {
                    buttons.get(i).setBackground(Color.red);
                }
                if(richtung == 0){
                    richtung = 1;
                }else {
                    richtung = 0;
                }
                break;
            default: break;
        }

    }

    @Override
    public void run() {
        while(true) {
            if (richtung == 0) {
                try {
                    rechtsDrehen();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else if (richtung == 1) {
                try {
                    linksDrehen();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void rechtsDrehen() throws InterruptedException {

            for (int i = 0; i <= 9; i++) {
                int z = Integer.parseInt(buttons.get(i).getText());
                if (z == 9) {
                    z = 0;
                } else {
                    z += 1;
                }
                buttons.get(i).setText("" + z);
                buttons.get(i).setActionCommand(String.valueOf(z));

            }
            Thread.sleep(1000);

    }

    public void linksDrehen() throws InterruptedException {

            for (int i = 0; i <= 9; i++) {
                int z = Integer.parseInt(buttons.get(i).getText());
                if (z == 0) {
                    z = 9;
                } else {
                    z -= 1;
                }
                buttons.get(i).setText("" + z);
                buttons.get(i).setActionCommand(String.valueOf(z));


            }
            Thread.sleep(1000);


    }
}
