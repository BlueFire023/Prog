package second;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayDeque;
import java.util.Stack;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 13/04/2023
 */
public class MyPaint extends JFrame implements MouseMotionListener, MouseListener, ActionListener, ChangeListener {
    private final int WIDTH = 160, HEIGHT = 160, PIXEL_SIZE = 5, X_OFFSET = 1, Y_OFFSET = 26;
    private final JPanel[][] pixels;

    private final JButton[] buttonTools = {
            new JButton("Color"),
            new JButton("Fill"),
            new JButton("Clear"),
            new JButton("Undo")
    };
    private final JButton[] buttonRecentColors = new JButton[10];
    private final Stack<Color[][]> previousStates = new Stack<>();
    private final int MAX_SIZE = 10;
    private final ArrayDeque<Color> queueRecentColors = new ArrayDeque<>(MAX_SIZE);
    private boolean fillMode = false;
    private Color currentColor = Color.BLACK;
    private int prevX, prevY, brushSize = 1;

    public MyPaint() {
        setTitle("MyPaint");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelCanvas = new JPanel();
        panelCanvas.setLayout(new GridLayout(WIDTH, HEIGHT, 0, 0));
        panelCanvas.setPreferredSize(new Dimension(WIDTH * PIXEL_SIZE, HEIGHT * PIXEL_SIZE));

        setResizable(false);
        pixels = new JPanel[WIDTH][HEIGHT];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                pixels[i][j] = new JPanel();
                pixels[i][j].setBackground(Color.WHITE);
                panelCanvas.add(pixels[i][j]);
            }
        }

        JPanel panelToolBar = new JPanel();
        panelToolBar.setLayout(new GridLayout(1, 5));
        panelToolBar.setPreferredSize(new Dimension(WIDTH, 50));

        for (JButton b : buttonTools) {
            add(b);
            panelToolBar.add(b);
            b.setActionCommand(b.getText());
            b.setFont(new Font("Courier", Font.BOLD, 10));
            b.setBackground(Color.WHITE);
            b.addActionListener(this);
        }
        buttonTools[3].setText("Undo: " + previousStates.size());

        JPanel panelRecentColorsBar = new JPanel();
        panelRecentColorsBar.setLayout(new GridLayout(1, 10, 1, 0));
        panelRecentColorsBar.setPreferredSize(new Dimension(WIDTH, 50));

        for (int i = 0; i < buttonRecentColors.length; i++) {
            buttonRecentColors[i] = new JButton();
            add(buttonRecentColors[i]);
            panelRecentColorsBar.add(buttonRecentColors[i]);
            buttonRecentColors[i].setBackground(Color.WHITE);
            buttonRecentColors[i].addActionListener(this);
        }

        queueRecentColors.add(currentColor);
        queueRecentColors.add(Color.WHITE);
        queueRecentColors.add(Color.RED);
        queueRecentColors.add(Color.BLUE);
        queueRecentColors.add(Color.GREEN);
        queueRecentColors.add(Color.YELLOW);
        queueRecentColors.add(Color.MAGENTA);
        queueRecentColors.add(Color.GRAY);
        queueRecentColors.add(Color.ORANGE);
        queueRecentColors.add(Color.CYAN);

        Border borderFocusedColor = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createLineBorder(Color.WHITE, 2));

        int i = 0;
        for (Color c : queueRecentColors) {
            buttonRecentColors[i].setBorder(borderFocusedColor);
            buttonRecentColors[i].setBackground(c);
            buttonRecentColors[i].setBorderPainted(false);
            i++;
        }
        buttonRecentColors[0].setBorderPainted(true);

        JSlider sliderBrushSize = new JSlider(SwingConstants.HORIZONTAL, 1, 5, 1);
        sliderBrushSize.addChangeListener(this);
        sliderBrushSize.setPaintTicks(true);
        sliderBrushSize.setPaintLabels(true);
        sliderBrushSize.setMajorTickSpacing(1);
        sliderBrushSize.setToolTipText("Brushsize");
        sliderBrushSize.setMaximum(5);

        panelToolBar.add(sliderBrushSize);
        add(panelToolBar, BorderLayout.NORTH);
        add(panelRecentColorsBar, BorderLayout.CENTER);
        add(panelCanvas, BorderLayout.SOUTH);
        addMouseMotionListener(this);
        addMouseListener(this);

        validate();
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
        int y = e.getX() / PIXEL_SIZE - X_OFFSET;
        int x = e.getY() / PIXEL_SIZE - Y_OFFSET;
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
        prevY = e.getX() / PIXEL_SIZE - X_OFFSET;
        prevX = e.getY() / PIXEL_SIZE - Y_OFFSET;
        if (fillMode) {
            fill(prevX, prevY);
        } else if (prevX >= brushSize && prevX < WIDTH && prevY >= brushSize && prevY < HEIGHT) {
            savePreviousState();
            paintPixel(prevX, prevY);
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
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        brushSize = source.getValue();
    }

    // ist für das auswählen der Knöpfe zuständig
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        switch (e.getActionCommand()) {
            case "Clear" -> clear();
            case "Fill" -> {
                fillMode = !fillMode;
                source.setBackground(fillMode ? Color.darkGray : Color.WHITE);
                source.setForeground(fillMode ? Color.WHITE : Color.BLACK);
            }
            case "Undo" -> undo();
            case "Color" -> {
                currentColor = JColorChooser.showDialog(this, "Select a color", currentColor);
                if (currentColor == null || currentColor.getRGB() == -1118482) {
                    currentColor = Color.WHITE;
                }
                if (queueRecentColors.size() == MAX_SIZE) {
                    queueRecentColors.removeLast();
                }
                queueRecentColors.addFirst(currentColor);
                int i = 0;
                for (Color c : queueRecentColors) {
                    buttonRecentColors[i].setBorderPainted(false);
                    buttonRecentColors[i].setBackground(c);
                    i++;
                }
                buttonRecentColors[0].setBorderPainted(true);
            }
            default -> {
                for (JButton button : buttonRecentColors) {
                    button.setBorderPainted(false);
                }
                source.setBorderPainted(true);
                currentColor = source.getBackground();
            }
        }
    }

    // füllt einen Bereich mit ausgewählter Farbe aus
    private void fill(int x, int y) {
        Color targetColor = pixels[x][y].getBackground();
        savePreviousState();
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
            paintPixel(x0, y0);
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
        if (!previousStates.isEmpty()) {
            Color[][] currLastSteps = previousStates.pop();
            for (int i = 0; i < WIDTH; i++) {
                for (int j = 0; j < HEIGHT; j++) {
                    pixels[i][j].setBackground(currLastSteps[i][j]);
                }
            }
        }
        buttonTools[3].setText("Undo: " + previousStates.size());
    }

    private void clear() {
        savePreviousState();
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (pixels[i][j].getBackground() != Color.WHITE) {
                    pixels[i][j].setBackground(Color.WHITE);
                }
            }
        }
    }

    private void paintPixel(int x, int y) {
        for (int i = 0; i < brushSize; i++) {
            for (int j = 0; j < brushSize; j++) {
                if (((int) (brushSize / 2.5d)) + x - i >= 0 && ((int) (brushSize / 2.5d)) + y - j >= 0 && ((int) (brushSize / 2.5d)) + x - i < WIDTH && ((int) (brushSize / 2.5d)) + y - j < HEIGHT) {
                    pixels[(int) (brushSize / 2.5d) + x - i][(int) (brushSize / 2.5d) + y - j].setBackground(currentColor);
                }
            }
        }
    }

    private void savePreviousState() {
        Color[][] c = new Color[WIDTH][HEIGHT];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                c[i][j] = pixels[i][j].getBackground();
            }
        }
        previousStates.push(c);
        buttonTools[3].setText("Undo: " + previousStates.size());
    }
}