package second;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 13/04/2023
 */
public class MyPaint extends JFrame implements MouseMotionListener, MouseListener, ActionListener {
    private final int WIDTH = 128;
    private final int HEIGHT = 128;
    private final JPanel pickedColor = new JPanel();
    private final JButton[] buttons = {
            new JButton("Black"),
            new JButton("Blue"),
            new JButton("Cyan"),
            new JButton("Gray"),
            new JButton("Green"),
            new JButton("Magenta"),
            new JButton("Orange"),
            new JButton("Pink"),
            new JButton("Red"),
            new JButton("White"),
            new JButton("Yellow")
    };
    private final JPanel[][] pixels;
    private final Color[] COLORS = {Color.black, Color.blue, Color.cyan, Color.gray, Color.green, Color.magenta, Color.orange, Color.pink, Color.red, Color.white, Color.yellow};
    private JButton prevButton = buttons[0];
    private int prevX, prevY;
    private boolean fillMode = false;
    private int currentColorIndex = 0;

    public MyPaint() {
        setTitle("MyPaint");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new FlowLayout());
        JPanel canvas = new JPanel();
        canvas.setLayout(new GridLayout(WIDTH, HEIGHT, 0, 0));
        canvas.setPreferredSize(new Dimension(WIDTH * 5, HEIGHT * 5));
        setSize(WIDTH * 5, HEIGHT * 5 + 150);
        setLocation(500, 50);
        setResizable(false);
        pixels = new JPanel[WIDTH][HEIGHT];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                pixels[i][j] = new JPanel();
                pixels[i][j].setBackground(Color.WHITE);
                canvas.add(pixels[i][j]);
            }
        }
        getContentPane().add(canvas);

        int i = 0;
        JPanel toolBar = new JPanel();
        for (JButton button : buttons) {
            add(button);
            toolBar.add(button);
            button.setActionCommand("" + i++);
            button.setFont(new Font("Courier", Font.BOLD, 10));
            button.setBackground(Color.WHITE);
            button.addActionListener(this);
        }
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(this);
        clearButton.setFont(new Font("Courier", Font.BOLD, 10));
        clearButton.setBackground(Color.WHITE);
        toolBar.add(clearButton);

        JButton fillTool = new JButton("Fill");
        fillTool.setFont(new Font("Courier", Font.BOLD, 10));
        fillTool.setBackground(Color.WHITE);
        fillTool.addActionListener(this);
        toolBar.add(fillTool);
        toolBar.setLayout(new GridLayout(3, 5));

        getContentPane().add(toolBar);
        addMouseMotionListener(this);
        addMouseListener(this);

        buttons[0].setBackground(Color.darkGray);
        buttons[0].setForeground(Color.WHITE);

        pickedColor.setBackground(Color.BLACK);
        pickedColor.setPreferredSize(new Dimension(50, 50));
        getContentPane().add(pickedColor);

        getContentPane().validate();
        setVisible(true);
    }

    public static void main(String[] args) {
        new MyPaint();
    }

    // MouseMotionListener methoden
    // Zeichnet auf dem Canvas
    @Override
    public void mouseDragged(MouseEvent e) {
        int y = e.getX() / 5;
        int x = e.getY() / 5 - 7;
        // nutzt den Bresenham Algorithmus für glatte linien bei schnellen Maus Bewegungen
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

    // Entweder einen ersten Punkt beim drücken zeichnen oder die methode fill() ausführen
    @Override
    public void mousePressed(MouseEvent e) {
        prevY = e.getX() / 5;
        prevX = e.getY() / 5 - 7;
        if (fillMode && prevX > 0 && prevX < 128 && prevY > 0 && prevY < 128) {
            fill(prevX, prevY);
        } else if (prevX > 0 && prevX < 128 && prevY > 0 && prevY < 128) {
            pixels[prevX][prevY].setBackground(COLORS[currentColorIndex]);
        }
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

    // ist für das auswählen der Knöpfe zuständig
    @Override
    public void actionPerformed(ActionEvent e) {
        if (((JButton) e.getSource()).getText().equals("Clear")) {
            for (int i = 0; i < WIDTH; i++) {
                for (int j = 0; j < HEIGHT; j++) {
                    pixels[i][j].setBackground(Color.WHITE);
                }
            }
        } else if (((JButton) e.getSource()).getText().equals("Fill")) {
            fillMode = !fillMode;
            if (fillMode) {
                ((JButton) e.getSource()).setBackground(Color.darkGray);
                ((JButton) e.getSource()).setForeground(Color.white);
            } else {
                ((JButton) e.getSource()).setBackground(Color.WHITE);
                ((JButton) e.getSource()).setForeground(Color.BLACK);
            }
        } else {
            currentColorIndex = Integer.parseInt(e.getActionCommand());
            pickedColor.setBackground(COLORS[currentColorIndex]);
            prevButton.setBackground(Color.WHITE);
            prevButton.setForeground(Color.BLACK);
            ((JButton) e.getSource()).setBackground(Color.darkGray);
            ((JButton) e.getSource()).setForeground(Color.white);
            prevButton = (JButton) e.getSource();
        }
    }

    // füllt einen Bereich mit ausgewählter Farbe aus
    private void fill(int x, int y) {
        Color targetColor = pixels[x][y].getBackground();
        if (COLORS[currentColorIndex].equals(targetColor)) {
            return;
        }

        Stack<Point> stack = new Stack<>();
        stack.push(new Point(x, y));

        while (!stack.empty()) {
            Point p = stack.pop();
            int px = p.x;
            int py = p.y;

            if (px >= 0 && py >= 0 && px < WIDTH && py < HEIGHT && pixels[px][py].getBackground().equals(targetColor)) {
                pixels[px][py].setBackground(COLORS[currentColorIndex]);
                stack.push(new Point(px + 1, py));
                stack.push(new Point(px - 1, py));
                stack.push(new Point(px, py + 1));
                stack.push(new Point(px, py - 1));
            }
        }
    }

    // Bresenham Algorithmus
    private void drawLineBresenham(int x0, int y0, int x1, int y1) {
        int dx = Math.abs(x1 - x0), dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1, sy = y0 < y1 ? 1 : -1;
        int err = dx - dy, e2;
        while (true) {
            pixels[x0][y0].setBackground(COLORS[currentColorIndex]);
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