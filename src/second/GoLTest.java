package second;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 31/05/2023
 */
public class GoLTest extends JPanel implements KeyListener, MouseListener, MouseMotionListener {
    BufferedImage canvas = new BufferedImage(100, 100, 1);
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
    JFrame frame = new JFrame();
    int prevX, prevY;

    public GoLTest() {
        for (int i = 0; i < canvas.getWidth(); i++) {
            for (int j = 0; j < canvas.getHeight(); j++) {
                setCell(new Point(i, j), false);
            }
        }
        menu.add(item1);
        menu.add(item2);

        subMenu.add(item3);
        subMenu.add(item4);

        menu.add(subMenu);
        menuBar.add(menu);
        menuBar.setBackground(Color.LIGHT_GRAY);
        frame.addKeyListener(this);
        frame.addMouseMotionListener(this);
        frame.addMouseListener(this);
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
            canvas.setRGB(nextCellPosition.x, nextCellPosition.y, aliveCellColor.getRGB());
            aliveCells.add(nextCellPosition);
        } else {
            canvas.setRGB(nextCellPosition.x, nextCellPosition.y, deadCellColor.getRGB());
            aliveCells.remove(nextCellPosition);
        }
    }

    public boolean isCellAlive(Point p) {
        return aliveCells.contains(p);
    }

    public Point calculateWrap(Point p) {
        int x = Math.floorMod(p.x, canvas.getWidth());
        int y = Math.floorMod(p.y, canvas.getHeight());
        return new Point(x, y);
    }

    public void calculateNextGeneration() {
        Set<Point> cellsToAdd = new HashSet<>();
        Set<Point> cellsToRemove = new HashSet<>();
        Set<Point> deadCellsToCheck = new HashSet<>();
        int newX, newY, aliveCellsCount;
        for (Point p : aliveCells) {
            aliveCellsCount = 0;
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    newX = p.x + i;
                    newY = p.y + j;
                    if (new Point(newX, newY).equals(p)) {
                        continue;
                    }
                    if (isCellAlive(calculateWrap(new Point(newX, newY)))) {
                        aliveCellsCount++;
                    } else {
                        deadCellsToCheck.add(new Point(newX, newY));
                    }
                }
            }
            if (aliveCellsCount < 2) {
                cellsToRemove.add(p);
            }
            if (aliveCellsCount > 3) {
                cellsToRemove.add(p);
            }
        }
        for (Point p : deadCellsToCheck) {
            aliveCellsCount = 0;
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    newX = p.x + i;
                    newY = p.y + j;
                    if (new Point(newX, newY).equals(p)) {
                        continue;
                    }
                    if (isCellAlive(calculateWrap(new Point(newX, newY)))) {
                        aliveCellsCount++;
                    }
                }
            }
            if (aliveCellsCount == 3) {
                cellsToAdd.add(p);
            }
        }
        for (Point p : cellsToRemove) {
            setCell(p, false);
        }
        for (Point p : cellsToAdd) {
            setCell(p, true);
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(canvas, 0, 0, getWidth(), getHeight(), null);
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

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX() / (frame.getWidth() / canvas.getWidth());
        int y = e.getY() / (frame.getWidth() / canvas.getWidth());
        drawLineBresenham(prevX, prevY, x, y);
        prevX = x;
        prevY = y;
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        prevX = e.getX() / (frame.getWidth() / canvas.getWidth());
        prevY = e.getY() / (frame.getWidth() / canvas.getWidth());
        setCell(new Point(prevX, prevY), true);
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

    private void drawLineBresenham(int x0, int y0, int x1, int y1) {
        int dx = Math.abs(x1 - x0), dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1, sy = y0 < y1 ? 1 : -1;
        int err = dx - dy, e2;
        while (true) {
            setCell(new Point(x0, y0), true);
            if (x0 == x1 && y0 == y1) {
                break;
            }
            e2 = err * 2;
            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }
    }
}
