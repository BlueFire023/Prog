package second;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayDeque;
import java.util.Stack;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 13/04/2023
 */
public class MyPaint extends JFrame implements MouseMotionListener, MouseListener, ActionListener {
    private final int WIDTH = 160, HEIGHT = 160, PIXELSIZE = 5, XOFFSET = 1, YOFFSET = 26;
    private final JPanel[][] pixels;

    private final JButton[] buttons = {
            new JButton("Color"),
            new JButton("Fill"),
            new JButton("Clear"),
            new JButton("Undo"),
    };
    private final JButton[] recentColorButtons = {
            new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton()
    };
    private final Stack<Color[][]> previousStates = new Stack<>();
    private final int MAX_SIZE = 10;
    private ArrayDeque<Color> recentColors = new ArrayDeque<>(MAX_SIZE);
    private boolean fillMode = false;
    Border pressedBorder;
    private Color currentColor = Color.BLACK;
    private int prevX, prevY;

    public MyPaint() {
        setTitle("MyPaint");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        JPanel canvas = new JPanel();
        canvas.setLayout(new GridLayout(WIDTH, HEIGHT, 0, 0));
        canvas.setPreferredSize(new Dimension(WIDTH * PIXELSIZE, HEIGHT * PIXELSIZE));
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

        JPanel toolBar = new JPanel();
        toolBar.setLayout(new GridLayout(1, 5));
        toolBar.setPreferredSize(new Dimension(50, 50));

        for (JButton button : buttons) {
            add(button);
            toolBar.add(button);
            button.setActionCommand(button.getText());
            button.setFont(new Font("Courier", Font.BOLD, 10));
            button.setBackground(Color.WHITE);
            button.addActionListener(this);
        }

        pressedBorder = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK,2),
                BorderFactory.createLineBorder(Color.WHITE,2));

        JPanel recentColorsBar = new JPanel();
        recentColorsBar.setLayout(new GridLayout(1, 10, 1,0));
        for (JButton button : recentColorButtons) {
            add(button);
            recentColorsBar.add(button);
            button.setBackground(Color.WHITE);
            button.setPreferredSize(new Dimension(50, 50));
            button.addActionListener(this);
        }

        recentColors.add(currentColor);
        recentColors.add(Color.WHITE);
        recentColors.add(Color.RED);
        recentColors.add(Color.BLUE);
        recentColors.add(Color.GREEN);
        recentColors.add(Color.YELLOW);
        recentColors.add(Color.MAGENTA);
        recentColors.add(Color.GRAY);
        recentColors.add(Color.ORANGE);
        recentColors.add(Color.CYAN);
        int i = 0;
        for (Color c : recentColors) {
            recentColorButtons[i].setBorder(pressedBorder);
            recentColorButtons[i].setBackground(c);
            recentColorButtons[i].setBorderPainted(false);
            i++;
        }
        recentColorButtons[0].setBorderPainted(true);
        recentColorButtons[0].setBackground(currentColor);
        buttons[3].setText("Undo: " + previousStates.size());

        getContentPane().add(toolBar, BorderLayout.NORTH);
        getContentPane().add(recentColorsBar, BorderLayout.CENTER);
        getContentPane().add(canvas, BorderLayout.SOUTH);
        addMouseMotionListener(this);
        addMouseListener(this);

        getContentPane().validate();
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new MyPaint();
    }

    // MouseMotionListener methoden
    // Zeichnet auf dem Canvas
    @Override
    public void mouseDragged(MouseEvent e) {
        int y = e.getX() / PIXELSIZE - XOFFSET;
        int x = e.getY() / PIXELSIZE - YOFFSET;
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
        prevY = e.getX() / PIXELSIZE - XOFFSET;
        prevX = e.getY() / PIXELSIZE - YOFFSET;

        if (fillMode && prevX > -1 && prevX < WIDTH && prevY > -1 && prevY < HEIGHT) {
            fill(prevX, prevY);
        } else if (prevX > -1 && prevX < WIDTH && prevY > -1 && prevY < HEIGHT) {
            previousStates.push(getPixelColor());
            buttons[3].setText("Undo: " + previousStates.size());
            pixels[prevX][prevY].setBackground(currentColor);
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
        switch (e.getActionCommand()) {
            case "Clear" -> clear();
            case "Fill" -> {
                fillMode = !fillMode;
                if (fillMode) {
                    ((JButton) e.getSource()).setBackground(Color.darkGray);
                    ((JButton) e.getSource()).setForeground(Color.white);
                } else {
                    ((JButton) e.getSource()).setBackground(Color.WHITE);
                    ((JButton) e.getSource()).setForeground(Color.BLACK);
                }
            }
            case "Undo" -> undo();
            case "Color" -> {
                currentColor = JColorChooser.showDialog(this, "Select a color", currentColor);
                if (currentColor == null || currentColor.getRGB() == -1118482) {
                    currentColor = Color.WHITE;
                }
                if (recentColors.size() == MAX_SIZE) {
                    recentColors.removeLast();
                }
                recentColors.addFirst(currentColor);
                int i = 0;
                for (Color c : recentColors) {
                    recentColorButtons[i].setBorderPainted(false);
                    recentColorButtons[i].setBackground(c);
                    i++;
                }
                recentColorButtons[0].setBorderPainted(true);
            }
            default -> {
                for (JButton button : recentColorButtons) {
                    button.setBorderPainted(false);
                }
                ((JButton) e.getSource()).setBorderPainted(true);
                currentColor = ((JButton) e.getSource()).getBackground();
            }
        }
    }

    // füllt einen Bereich mit ausgewählter Farbe aus
    private void fill(int x, int y) {
        Color targetColor = pixels[x][y].getBackground();
        previousStates.push(getPixelColor());
        buttons[3].setText("Undo: " + previousStates.size());
        if (!currentColor.equals(targetColor)) {
            Stack<Point> stack = new Stack<>();
            stack.push(new Point(x, y));

            while (!stack.empty()) {
                Point p = stack.pop();
                int px = p.x;
                int py = p.y;

                if (px >= 0 && py >= 0 && px < WIDTH && py < HEIGHT && pixels[px][py].getBackground().equals(targetColor)) {
                    pixels[px][py].setBackground(currentColor);
                    stack.push(new Point(px + 1, py));
                    stack.push(new Point(px - 1, py));
                    stack.push(new Point(px, py + 1));
                    stack.push(new Point(px, py - 1));
                }
            }
        }
    }

    // Bresenham Algorithmus
    private void drawLineBresenham(int x0, int y0, int x1, int y1) {
        int dx = Math.abs(x1 - x0), dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1, sy = y0 < y1 ? 1 : -1;
        int err = dx - dy, e2;
        while (true) {
            if (x0 > -1 && x0 < WIDTH && y0 > -1 && y0 < HEIGHT && x1 > -1 && x1 < WIDTH && y1 > -1 && y1 < HEIGHT) {
                pixels[x0][y0].setBackground(currentColor);
            }
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

    private void undo() {
        if (!previousStates.empty()) {
            Color[][] currLastSteps = previousStates.pop();
            for (int i = 0; i < 160; i++) {
                for (int j = 0; j < 160; j++) {
                    pixels[i][j].setBackground(currLastSteps[i][j]);
                }
            }
            buttons[3].setText("Undo: " + previousStates.size());
        }
    }

    private void clear() {
        previousStates.push(getPixelColor());
        buttons[3].setText("Undo: " + previousStates.size());
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (pixels[i][j].getBackground() != Color.WHITE) {
                    pixels[i][j].setBackground(Color.WHITE);
                }
            }
        }
    }

    private Color[][] getPixelColor() {
        Color[][] c = new Color[WIDTH][HEIGHT];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                c[i][j] = pixels[i][j].getBackground();
            }
        }
        return c;
    }
}