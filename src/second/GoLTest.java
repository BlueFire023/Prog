package second;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 31/05/2023
 */
public class GoLTest extends JPanel implements KeyListener, MouseListener, MouseMotionListener, ActionListener, Runnable {
    BufferedImage canvas = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
    Set<Point> aliveCells = new HashSet<>();
    Color aliveCellColor = Color.BLACK;
    Color deadCellColor = Color.WHITE;
    JMenuBar menuBar = new JMenuBar();
    JMenu menu = new JMenu("Menü");
    JMenuItem clearButton = new JMenuItem("Clear");
    JMenuItem setSizeButton = new JMenuItem("Set Size");
    JFrame frame = new JFrame();
    Point lastCell = new Point(0, 0);
    int prevX, prevY;

    public GoLTest() {
        updateCanvasColors();
        clearButton.addActionListener(this);
        setSizeButton.addActionListener(this);
        menu.add(clearButton);
        menu.add(setSizeButton);
        menuBar.add(menu);
        menuBar.setBackground(Color.LIGHT_GRAY);
        frame.setTitle("Game of Life");
        frame.addKeyListener(this);
        frame.addMouseMotionListener(this);
        frame.addMouseListener(this);
        frame.setJMenuBar(menuBar);
        frame.setSize(new Dimension(1014, 1060));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(this);
        frame.setVisible(true);
        new Thread(this).start();
    }

    private void updateCanvasColors() {
        for (int i = 0; i < canvas.getWidth(); i++) {
            for (int j = 0; j < canvas.getHeight(); j++) {
                setCell(new Point(i, j), false);
            }
        }
        for (Point p : aliveCells) {
            setCell(p, true);
        }
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

    public void clearCanvas() {
        aliveCells.clear();
        updateCanvasColors();
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
        g.drawImage(canvas, 0, 0, frame.getWidth(), frame.getHeight(), null);
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
        int x = (e.getX() - (frame.getWidth() - getWidth())) * canvas.getWidth() / getWidth();
        int y = (e.getY() - (frame.getHeight() - getHeight())) * canvas.getHeight() / getHeight();
        drawLineBresenham(prevX, prevY, x, y);
        prevX = x;
        prevY = y;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = (e.getX() - (frame.getWidth() - getWidth())) * canvas.getWidth() / getWidth();
        int y = (e.getY() - (frame.getHeight() - getHeight())) * canvas.getHeight() / getHeight();
        try {
            if (!isCellAlive(new Point(x, y))) {
                canvas.setRGB(lastCell.x, lastCell.y, isCellAlive(lastCell) ? aliveCellColor.getRGB() : deadCellColor.getRGB());
                canvas.setRGB(x, y, Color.GRAY.getRGB());
                lastCell = new Point(x, y);
            } else {
                canvas.setRGB(x, y, isCellAlive(lastCell) ? aliveCellColor.getRGB() : deadCellColor.getRGB());
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        prevX = (e.getX() - (frame.getWidth() - getWidth())) * canvas.getWidth() / getWidth();
        prevY = (e.getY() - (frame.getHeight() - getHeight())) * canvas.getHeight() / getHeight();
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

    public void updateCanvasSize() {
        JFrame setSizeFrame = new JFrame();
        setSizeFrame.setLayout(new FlowLayout());
        JTextField widthTextArea = new JTextField(String.valueOf(canvas.getTileWidth()));
        JTextField heightTextArea = new JTextField(String.valueOf(canvas.getTileHeight()));
        setSizeFrame.add(widthTextArea);
        setSizeFrame.add(heightTextArea);
        setSizeFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSizeFrame.setSize(100, 200);
        setSizeFrame.setVisible(true);
        JButton applyButton = new JButton("Apply");
        setSizeFrame.add(applyButton);
        applyButton.addActionListener(e -> {
            clearCanvas();
            canvas = new BufferedImage(Integer.parseInt(widthTextArea.getText()), Integer.parseInt(heightTextArea.getText()), 1);
            setSizeFrame.dispose();
            updateCanvasColors();
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Clear" -> clearCanvas();
            case "Set Size" -> updateCanvasSize();
        }
    }

    @Override
    public void run() {

    }
}
