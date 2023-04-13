package second;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 13.04.2023
 */

public class MyPaint extends JFrame implements MouseListener {
    JPanel[] panels;
    Container cp = getContentPane();


    public MyPaint() {
        setTitle(" MyPaint ");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        cp.addMouseListener(this);

        panels = new JPanel[1024];
        for (int i = 0; i < 1024; i++)
        {
                panels[i] = new JPanel(new GridLayout(1,1));
                cp.add(panels[i]);
        }
    }

    public static void main(String[] args) {
        MyPaint MyPaint = new MyPaint();
        MyPaint.setSize(800, 600);// Setze die Größe des Fensters auf 800 x 600 Pixel (Formatierung)
        MyPaint.setResizable(true);
        MyPaint.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        ((JPanel) e.getSource()).setForeground(Color.BLACK);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
