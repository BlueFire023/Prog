package second;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 31/05/2023
 */
public class GoLTest extends JPanel{
    BufferedImage test = new BufferedImage(10,10, 1);
    public GoLTest(){
        JFrame frame = new JFrame();
        test.setRGB(0,0,Color.WHITE.getRGB());

        frame.setSize(new Dimension(1000,1000));
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setContentPane(this);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new GoLTest();
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(test,0,0,getWidth(),getHeight(),null);
        repaint();
    }
}
