package second;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 13/04/2023
 */
public class MyPaint extends JFrame implements MouseMotionListener, MouseListener, ActionListener {
    private final int WIDTH = 128;
    private final int HEIGHT = 128;
    private JPanel canvas = new JPanel();
    private JPanel colorPicker = new JPanel();
    private JPanel[][] pixels;
    private final Color[] COLORS = {Color.black, Color.blue, Color.cyan, Color.gray, Color.green, Color.magenta, Color.orange, Color.pink, Color.red, Color.white, Color.yellow};
    private int currentColorIndex = 0;
    int prevX, prevY;

    public MyPaint() {
        setTitle("MyPaint");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new FlowLayout());
        canvas.setLayout(new GridLayout(WIDTH, HEIGHT, 0, 0));
        canvas.setPreferredSize(new Dimension(640, 640));
        setSize(640, 800);
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

        JButton[] buttons = {
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
        int i = 0;
        for (JButton a : buttons) {
            add(a);
            colorPicker.add(a);
            a.setActionCommand("" + i++);
            a.addActionListener(this);
        }
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(this);
        colorPicker.add(clearButton);
        colorPicker.setLayout(new GridLayout(3, 5));
        getContentPane().add(colorPicker);
        getContentPane().validate();
        addMouseMotionListener(this);
        addMouseListener(this);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MyPaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int y = e.getX() / (640 / WIDTH);
        int x = e.getY() / (640 / HEIGHT) - 6;
        drawLineBresenham(prevX, prevY, x, y);
        prevX = x;
        prevY = y;
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public void drawLineBresenham(int x0, int y0, int x1, int y1) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx - dy;
        int x = x0;
        int y = y0;

        while (x != x1 || y != y1) {
            if ((x > -1 && y > -1) && (x < 128 && y < 128)) {
                pixels[x][y].setBackground(COLORS[currentColorIndex]);
            }
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x += sx;
            }
            if (e2 < dx) {
                err += dx;
                y += sy;
            }
        }
        if ((x > -1 && y > -1) && (x < 128 && y < 128)) {
            pixels[x1][y1].setBackground(COLORS[currentColorIndex]);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        prevY = e.getX() / (640 / WIDTH);
        prevX = e.getY() / (640 / HEIGHT) - 6;
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (((JButton) e.getSource()).getText().equals("Clear")) {
            for (int i = 0; i < WIDTH; i++) {
                for (int j = 0; j < HEIGHT; j++) {
                    pixels[i][j].setBackground(Color.WHITE);
                }
            }
        } else {
            currentColorIndex = Integer.parseInt(e.getActionCommand());
        }
    }
}
