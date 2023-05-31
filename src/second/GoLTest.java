package second;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 31/05/2023
 */
public class GoLTest extends JPanel implements KeyListener {
    BufferedImage test = new BufferedImage(10, 10, 1);
    Set<Point> aliveCells = new HashSet<>();
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
                setCell(new Point(i, j), false);
            }
        }
        setCell(new Point(5, 5), true);
        setCell(new Point(4, 4), true);
        menu.add(item1);
        menu.add(item2);

        subMenu.add(item3);
        subMenu.add(item4);

        menu.add(subMenu);
        menuBar.add(menu);
        menuBar.setBackground(Color.LIGHT_GRAY);
        frame.addKeyListener(this);
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

    public void setCell(Point p, boolean isAlive) {
        Point nextCellPosition = calculateWrap(p);
        if (isAlive) {
            test.setRGB(nextCellPosition.x, nextCellPosition.y, aliveCellColor.getRGB());
            aliveCells.add(nextCellPosition);
        } else {
            test.setRGB(nextCellPosition.x, nextCellPosition.y, deadCellColor.getRGB());
            aliveCells.remove(nextCellPosition);
        }
    }

    public boolean isCellAlive(Point p) {
        return aliveCells.contains(p);
    }

    public Point calculateWrap(Point p) {
        int x = Math.floorMod(p.x, test.getWidth());
        int y = Math.floorMod(p.y, test.getHeight());
        return new Point(x, y);
    }

    public void calculateNextGeneration() {
        ArrayList<Point> cellsToAdd = new ArrayList<>();

        for (Point p : aliveCells) {
            Point rightNeighbor = new Point(p.x + 1, p.y);

            if (!isCellAlive(rightNeighbor)) {
                cellsToAdd.add(rightNeighbor);
            }
        }
        for (Point p : cellsToAdd) {
            setCell(p, true);
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(test, 0, 0, getWidth(), getHeight(), null);
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        calculateNextGeneration();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
