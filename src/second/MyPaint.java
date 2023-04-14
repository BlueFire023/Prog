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
    private boolean fillMode = false;
    private JPanel canvas = new JPanel();
    private JPanel toolBar = new JPanel();
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
    JButton prevButton = buttons[0];
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


        int i = 0;
        for (JButton a : buttons) {
            add(a);
            toolBar.add(a);
            a.setActionCommand("" + i++);
            a.setFont(new Font("Courier", Font.BOLD, 10));
            a.setBackground(Color.WHITE);
            a.addActionListener(this);
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
        getContentPane().validate();
        addMouseMotionListener(this);
        addMouseListener(this);
        buttons[0].setBackground(Color.darkGray);
        buttons[0].setForeground(Color.WHITE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MyPaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int y = e.getX() / (640 / WIDTH);
        int x = e.getY() / (640 / HEIGHT) - 7;
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
        prevX = e.getY() / (640 / HEIGHT) - 7;
        if (fillMode && prevX > 0 && prevX < 128 && prevY > 0 && prevY < 128) {
            fillTool(prevX, prevY);
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
            prevButton.setBackground(Color.WHITE);
            prevButton.setForeground(Color.BLACK);
            ((JButton) e.getSource()).setBackground(Color.darkGray);
            ((JButton) e.getSource()).setForeground(Color.white);
            prevButton = (JButton) e.getSource();
        }
    }

    public void fillTool(int x, int y) {
        Color targetColor = pixels[x][y].getBackground();
        Color replacementColor = COLORS[currentColorIndex];
        if (!targetColor.equals(replacementColor)) {
            Stack<Point> stack = new Stack<>();
            stack.push(new Point(x, y));

            while (!stack.isEmpty()) {
                Point p = stack.pop();
                int i = p.x;
                int j = p.y;
                if (i >= 0 && i < WIDTH && j >= 0 && j < HEIGHT && pixels[i][j].getBackground().equals(targetColor)) {
                    pixels[i][j].setBackground(replacementColor);
                    stack.push(new Point(i + 1, j));
                    stack.push(new Point(i - 1, j));
                    stack.push(new Point(i, j + 1));
                    stack.push(new Point(i, j - 1));
                }
            }
        }
    }

}
