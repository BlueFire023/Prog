package second;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 15/06/2023
 */
public class GoLView extends JPanel {
    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu optionsMenu = new JMenu("Menü");
    private final JMenu figuresMenu = new JMenu("Figuren");
    private final JMenu sliderMenu = new JMenu("Geschwindigkeit");
    private final JMenu staticMenu = new JMenu("Statische");
    private final JMenu oscMenu = new JMenu("Oszillierende");
    private final JMenu shipsMenu = new JMenu("Raum Schiffe");
    private final JMenu methMenu = new JMenu("Methuselahs");
    private final JMenu gunsMenu = new JMenu("Gleiter Kanonen");
    private final JMenu otherMenu = new JMenu("Andere");
    private final ArrayList<JMenuItem> figures = new ArrayList<>();

    private final JSlider speedSlider = new JSlider();
    private final JMenu recentFiguresMenu = new JMenu("Zuletzt benutzt");
    private final JMenuItem save = new JMenuItem("Speichern");
    private final JMenuItem load = new JMenuItem("Laden");
    private final JMenuItem clearButton = new JMenuItem("Löschen");
    private final JMenuItem setSizeButton = new JMenuItem("Auflösung");
    private final JMenuItem setColorButton = new JMenuItem("Farben");
    private final JMenuItem runButton = new JMenuItem("Laufen");
    private final JMenuItem paintButton = new JMenuItem("Malen");
    private final JMenuItem setButton = new JMenuItem("Setzen");
    private final JMenuItem lineButton = new JMenuItem("Linien");
    private final JMenuItem frameButton = new JMenuItem("Rahmen");
    private final JMenuItem crossButton = new JMenuItem("Kreuz");
    private final JMenuItem plusButton = new JMenuItem("Plus");
    private final JFrame setSizeFrame = new JFrame();
    private final JTextField widthTextArea;
    private final JTextField heightTextArea;
    private final JButton applySizeButton = new JButton("Apply");
    private final JButton aliveCellColorDisplay = new JButton();
    private final JButton deadCellColorDisplay = new JButton();
    private final JFrame frame = new JFrame();
    private final JMenuItem newWindow = new JMenuItem("Neues Fenster");
    private BufferedImage canvas;
    private final int stillLifesCount = 8, oscillatorsCount = 9, spaceshipsCount = 4, methuselahsCount = 3, ggCount = 2, otherCount = 4;

    public GoLView(BufferedImage canvas, int openWindows) {
        this.canvas = canvas;
        widthTextArea = new JTextField(String.valueOf(canvas.getTileWidth()));
        widthTextArea.setColumns(7);
        heightTextArea = new JTextField(String.valueOf(canvas.getTileHeight()));
        heightTextArea.setColumns(7);

        speedSlider.setMinimum(1);
        speedSlider.setMaximum(100);
        speedSlider.setMajorTickSpacing(10);
        speedSlider.setMinorTickSpacing(5);
        speedSlider.createStandardLabels(5);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.setValue(10);
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        for (int i = 10; i <= 100; i += 10) {
            labelTable.put(i, new JLabel(Integer.toString(i)));
        }
        speedSlider.setLabelTable(labelTable);

        optionsMenu.add(clearButton);
        optionsMenu.add(setSizeButton);
        optionsMenu.add(setColorButton);
        optionsMenu.add(runButton);
        optionsMenu.add(paintButton);
        optionsMenu.add(setButton);
        optionsMenu.add(lineButton);
        optionsMenu.add(newWindow);
        menuBar.add(optionsMenu);

        figuresMenu.add(recentFiguresMenu);
        figuresMenu.add(save);
        figuresMenu.add(load);
        figuresMenu.add(staticMenu);
        figuresMenu.add(oscMenu);
        figuresMenu.add(shipsMenu);
        figuresMenu.add(methMenu);
        figuresMenu.add(gunsMenu);
        figuresMenu.add(otherMenu);
        figuresMenu.add(frameButton);
        figuresMenu.add(crossButton);
        figuresMenu.add(plusButton);

        menuBar.add(figuresMenu);
        sliderMenu.add(speedSlider);
        menuBar.add(sliderMenu);
        menuBar.setBackground(Color.LIGHT_GRAY);

        applySizeButton.setActionCommand("size");
        applySizeButton.setBackground(Color.WHITE);

        aliveCellColorDisplay.setPreferredSize(new Dimension(50, 50));
        aliveCellColorDisplay.setActionCommand("acc");
        aliveCellColorDisplay.setFocusable(false);
        deadCellColorDisplay.setPreferredSize(new Dimension(50, 50));
        deadCellColorDisplay.setActionCommand("dcc");
        deadCellColorDisplay.setFocusable(false);

        crossButton.setName("p");
        frameButton.setName("p");
        plusButton.setName("p");

        setSizeFrame.setResizable(false);
        setSizeFrame.setTitle("Auflösung");
        setSizeFrame.setSize(new Dimension(250, 200));
        setSizeFrame.setLayout(new GridLayout(3, 1));
        JPanel widthPanel = new JPanel(new GridLayout(1, 2));
        JLabel widthLabel = new JLabel("Breite:");
        JPanel widthLabelLayout = new JPanel(new GridBagLayout());
        widthLabelLayout.add(widthLabel);
        JPanel widthPanelLayout = new JPanel(new GridBagLayout());
        widthPanelLayout.add(widthTextArea);
        widthPanel.add(widthLabelLayout);
        widthPanel.add(widthPanelLayout);
        setSizeFrame.add(widthPanel);
        JPanel heightPanel = new JPanel(new GridLayout(1, 2));
        JLabel heightLabel = new JLabel("Höhe:");
        JPanel heightLabelLayout = new JPanel(new GridBagLayout());
        heightLabelLayout.add(heightLabel);
        heightPanel.add(heightLabelLayout);
        JPanel heightPanelLayout = new JPanel(new GridBagLayout());
        heightPanelLayout.add(heightTextArea);
        heightPanel.add(heightPanelLayout);
        setSizeFrame.add(heightPanel);
        JPanel applyPanel = new JPanel(new GridBagLayout());
        applyPanel.add(applySizeButton);
        setSizeFrame.add(applyPanel);
        setSizeFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        frame.setTitle("Game of Life " + openWindows);
        frame.setJMenuBar(menuBar);
        frame.setSize(new Dimension(1014, 1060));
        frame.setDefaultCloseOperation(openWindows == 1 ? WindowConstants.EXIT_ON_CLOSE : WindowConstants.DISPOSE_ON_CLOSE);
        frame.setContentPane(this);
        frame.setVisible(true);
    }

    public void setListeners(ActionListener al, KeyListener kl, MouseListener ml, MouseMotionListener mml, ChangeListener cl, WindowFocusListener wfl, WindowListener wl, MouseWheelListener mwl) {
        frame.addKeyListener(kl);
        addMouseWheelListener(mwl);
        addMouseMotionListener(mml);
        addMouseListener(ml);
        clearButton.addActionListener(al);
        setSizeButton.addActionListener(al);
        applySizeButton.addActionListener(al);
        setColorButton.addActionListener(al);
        runButton.addActionListener(al);
        paintButton.addActionListener(al);
        setButton.addActionListener(al);
        lineButton.addActionListener(al);
        newWindow.addActionListener(al);
        aliveCellColorDisplay.addActionListener(al);
        deadCellColorDisplay.addActionListener(al);
        save.addActionListener(al);
        load.addActionListener(al);
        frameButton.addActionListener(al);
        frameButton.addMouseListener(ml);
        crossButton.addActionListener(al);
        crossButton.addMouseListener(ml);
        plusButton.addActionListener(al);
        plusButton.addMouseListener(ml);
        frame.addWindowFocusListener(wfl);
        speedSlider.addChangeListener(cl);
        frame.addWindowListener(wl);
        int index = 0;
        for (JMenuItem j : figures) {
            j.addActionListener(al);
            j.setActionCommand(String.valueOf(index++));
        }
    }

    public void updateCanvasSize() {
        setSizeFrame.setVisible(true);
    }

    public void disposeSetSizeFrame() {
        setSizeFrame.dispose();
    }

    public void updateRecentFiguresMenu(Stack<GoLPrefab> recent, ActionListener al) {
        for (int i = 0; i < Math.min(recent.size(), 10); i++) {
            JMenuItem item = new JMenuItem(recent.get( i).name());
            item.addActionListener(al);
            recentFiguresMenu.add(item);
        }
    }

    public void updateCellColor(Color aColor, Color dColor) {
        JFrame setColorFrame = new JFrame();
        setColorFrame.setLayout(new GridLayout(2, 2));
        JLabel aliveCellColorTag = new JLabel(" Lebende Zellen:");
        JLabel deadCellColorTag = new JLabel(" Tote Zellen:");

        aliveCellColorDisplay.setBackground(aColor);
        deadCellColorDisplay.setBackground(dColor);
        setColorFrame.setTitle("Farben");
        JPanel aliveLayoutPanel = new JPanel();
        aliveLayoutPanel.setLayout(new GridBagLayout());
        setColorFrame.add(aliveCellColorTag);
        aliveLayoutPanel.add(aliveCellColorDisplay);
        setColorFrame.add(aliveLayoutPanel);
        setColorFrame.add(deadCellColorTag);
        JPanel deadLayoutPanel = new JPanel();
        deadLayoutPanel.setLayout(new GridBagLayout());
        deadLayoutPanel.add(deadCellColorDisplay);
        setColorFrame.add(deadLayoutPanel);
        setColorFrame.setSize(250, 200);
        setColorFrame.setVisible(true);
        setColorFrame.setResizable(false);
    }

    public void updateCanvasObject(BufferedImage canvas) {
        this.canvas = canvas;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(canvas, 0, 0, getWidth(), getHeight(), null);
        repaint();
    }

    public int getNewWidth() {
        return Integer.parseInt(widthTextArea.getText());
    }

    public int getNewHeight() {
        return Integer.parseInt(heightTextArea.getText());
    }

    public int getSliderstat() {
        return speedSlider.getValue();
    }

    public void initFiguresMenu(GoLFigures preMadeFigures) {
        int count = 0;
        for (int i = 0; i < stillLifesCount; i++) {
            figures.add(new JMenuItem(preMadeFigures.getFigure(i).name()));
            staticMenu.add(figures.get(i));
        }
        count += stillLifesCount;
        for (int i = count; i < oscillatorsCount + count; i++) {
            figures.add(new JMenuItem(preMadeFigures.getFigure(i).name()));
            oscMenu.add(figures.get(i));
        }
        count += oscillatorsCount;
        for (int i = count; i < spaceshipsCount + count; i++) {
            figures.add(new JMenuItem(preMadeFigures.getFigure(i).name()));
            shipsMenu.add(figures.get(i));
        }
        count += spaceshipsCount;
        for (int i = count; i < methuselahsCount + count; i++) {
            figures.add(new JMenuItem(preMadeFigures.getFigure(i).name()));
            methMenu.add(figures.get(i));
        }
        count += methuselahsCount;
        for (int i = count; i < ggCount + count; i++) {
            figures.add(new JMenuItem(preMadeFigures.getFigure(i).name()));
            gunsMenu.add(figures.get(i));
        }
        count += ggCount;
        for (int i = count; i < otherCount + count; i++) {
            figures.add(new JMenuItem(preMadeFigures.getFigure(i).name()));
            otherMenu.add(figures.get(i));
        }
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setNewTitle(int number) {
        frame.setTitle("Game of Life " + number);
    }
}
