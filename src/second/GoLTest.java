package second;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 31/05/2023
 */
public class GoLTest extends JPanel {
    BufferedImage test = new BufferedImage(10, 10, 1);
    Color aliveCellColor = Color.BLACK;
    Color deadCellColor = Color.WHITE;
    JMenuBar menuBar = new JMenuBar();
    JMenu menu = new JMenu("Menü");
    JMenu subMenu = new JMenu("SubMenü");
    JMenuItem item1 = new JMenuItem("Item1");
    JMenuItem item2 = new JMenuItem("Item2");
    JMenuItem item3 = new JMenuItem("Item3");
    JMenuItem item4 = new JMenuItem("Item4");

    public GoLTest() {
        JFrame frame = new JFrame();
        for (int i = 0; i < test.getWidth(); i++) {
            for (int j = 0; j < test.getHeight(); j++) {
                setCell(i, j, false);
            }
        }
        setCell(0, 0, true);
        menu.add(item1);
        menu.add(item2);

        subMenu.add(item3);
        subMenu.add(item4);

        menu.add(subMenu);
        menuBar.add(menu);
        menuBar.setBackground(Color.LIGHT_GRAY);
        frame.setJMenuBar(menuBar);
        frame.setSize(new Dimension(1000, 1000));
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setContentPane(this);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new GoLTest();
    }

    public void setAliveCellColor(Color color) {
        this.aliveCellColor = color;
    }

    public void setDeadCellColor(Color color) {
        this.deadCellColor = color;
    }

    public void setCell(int x, int y, boolean isAlive) {
        test.setRGB(x, y, isAlive ? aliveCellColor.getRGB() : deadCellColor.getRGB());
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(test, 0, 0, getWidth(), getHeight(), null);
        repaint();
    }
}
