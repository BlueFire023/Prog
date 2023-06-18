package second;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Hashtable;

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
    private final JMenuItem save = new JMenuItem("Speichern");
    private final JMenuItem load = new JMenuItem("Laden");
    private final JMenuItem clearButton = new JMenuItem("Löschen");
    private final JMenuItem setSizeButton = new JMenuItem("Auflösung");
    private final JMenuItem setColorButton = new JMenuItem("Farben");
    private final JMenuItem runButton = new JMenuItem("Laufen");
    private final JMenuItem paintButton = new JMenuItem("Malen");
    private final JMenuItem setButton = new JMenuItem("Setzen");
    private final JMenuItem lineButton = new JMenuItem("Linien");
    private final JFrame setSizeFrame = new JFrame();
    private final JTextField test = new JTextField("");
    private final JTextField widthTextArea;
    private final JTextField heightTextArea;
    private final JButton applySizeButton = new JButton("Apply");
    private final JButton activeColorDisplay = new JButton();
    private final JButton deadColorDisplay = new JButton();
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

        figuresMenu.add(save);
        figuresMenu.add(load);
        figuresMenu.add(staticMenu);
        figuresMenu.add(oscMenu);
        figuresMenu.add(shipsMenu);
        figuresMenu.add(methMenu);
        figuresMenu.add(gunsMenu);
        figuresMenu.add(otherMenu);

        menuBar.add(figuresMenu);
        sliderMenu.add(speedSlider);
        menuBar.add(sliderMenu);
        menuBar.setBackground(Color.LIGHT_GRAY);

        applySizeButton.setActionCommand("size");
        activeColorDisplay.setActionCommand("acc");
        deadColorDisplay.setActionCommand("dcc");
        test.setActionCommand("h");
        test.setColumns(7);

        frame.setTitle("Game of Life " + openWindows);
        frame.setJMenuBar(menuBar);
        frame.setSize(new Dimension(1014, 1060));
        frame.setDefaultCloseOperation(openWindows == 1 ? WindowConstants.EXIT_ON_CLOSE : WindowConstants.DISPOSE_ON_CLOSE);
        frame.setContentPane(this);
        frame.setVisible(true);
    }

    public void setListeners(ActionListener al, KeyListener kl, MouseListener ml, MouseMotionListener mml, ChangeListener cl, WindowFocusListener wfl, WindowListener wl) {
        frame.addKeyListener(kl);
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
        activeColorDisplay.addActionListener(al);
        deadColorDisplay.addActionListener(al);
        save.addActionListener(al);
        load.addActionListener(al);
        test.addActionListener(al);
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
        setSizeFrame.setLayout(new FlowLayout());
        setSizeFrame.add(widthTextArea);
        setSizeFrame.add(heightTextArea);
        setSizeFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSizeFrame.setSize(100, 200);
        setSizeFrame.setVisible(true);
        setSizeFrame.add(applySizeButton);
    }

    public void disposeSetSizeFrame() {
        setSizeFrame.dispose();
    }

    public void updateCellColor(Color aColor, Color dColor) {
        JFrame setColorFrame = new JFrame();
        setColorFrame.setLayout(new FlowLayout());
        JLabel activeColorTag = new JLabel("Active Cell color:");
        JLabel deadColorTag = new JLabel("Dead Cell color:");

        activeColorDisplay.setBackground(aColor);
        deadColorDisplay.setBackground(dColor);
        setColorFrame.add(activeColorTag);
        setColorFrame.add(activeColorDisplay);
        setColorFrame.add(deadColorTag);
        setColorFrame.add(deadColorDisplay);
        setColorFrame.setSize(100, 200);
        setColorFrame.setVisible(true);
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

    public int getChosenFigure() {
        return Integer.parseInt(test.getText());
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

    public JFrame getFrame(){
        return frame;
    }
    public void setNewTitle(int number){
        frame.setTitle("Game of Life " + number);
    }
}
