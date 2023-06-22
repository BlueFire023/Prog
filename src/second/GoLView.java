package second;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameListener;
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
    private final JMenu modeMenu = new JMenu("Modus");
    private final JMenu extraMenu = new JMenu("Extras");
    private final JSlider speedSlider = new JSlider();
    private final JMenuItem clearButton = new JMenuItem("Löschen");
    private final JMenuItem setSizeButton = new JMenuItem("Auflösung");
    private final JMenuItem setColorButton = new JMenuItem("Farben");
    private final JMenuItem runButton = new JMenuItem("Laufen");
    private final JMenuItem paintButton = new JMenuItem("Malen");
    private final JMenuItem setButton = new JMenuItem("Setzen");
    private final JMenu shapesMenu = new JMenu("Formen");
    private final JMenuItem lineButton = new JMenuItem("Linien");
    private final JMenuItem frameButton = new JMenuItem("Rahmen");
    private final JMenuItem crossButton = new JMenuItem("Kreuz");
    private final JMenuItem plusButton = new JMenuItem("Plus");
    private final JMenu sliderMenu = new JMenu("Geschwindigkeit");
    private final JFrame setSizeFrame = new JFrame();
    private final ArrayList<JMenuItem> shapes = new ArrayList<>();
    private final JTextField widthTextArea;
    private final JTextField heightTextArea;
    private final JButton applySizeButton = new JButton("Apply");
    private final JButton aliveCellColorDisplay = new JButton();
    private final JButton deadCellColorDisplay = new JButton();
    private final JFrame setColorFrame = new JFrame();
    private final JLabel aliveCellColorTag = new JLabel(" Lebende Zellen:");
    private final JLabel deadCellColorTag = new JLabel(" Tote Zellen:");
    private final JInternalFrame frame = new JInternalFrame();
    private BufferedImage canvas;
    public GoLView(BufferedImage canvas) {
        this.canvas = canvas; // Setze das übergebene BufferedImage-Objekt als das Canvas der Ansicht

        // Initialisiere das Textfeld für die Breite mit dem Canvas
        widthTextArea = new JTextField(String.valueOf(canvas.getTileWidth()));
        widthTextArea.setColumns(7);

        // Initialisiere das Textfeld für die Höhe mit dem Canvas
        heightTextArea = new JTextField(String.valueOf(canvas.getTileHeight()));
        heightTextArea.setColumns(7);

        // Konfiguriere den Schieberegler für die Geschwindigkeit
        speedSlider.setMinimum(1); // Minimaler Wert des Schiebereglers: 1
        speedSlider.setMaximum(100); // Maximaler Wert des Schiebereglers: 100
        speedSlider.setMajorTickSpacing(10); // Hauptintervall zwischen den Markierungen: 10
        speedSlider.setMinorTickSpacing(5); // Nebenintervall zwischen den Markierungen: 5
        speedSlider.createStandardLabels(5); // Erzeuge Standardbeschriftungen für den Schieberegler
        speedSlider.setPaintTicks(true); // Zeige die Markierungen auf dem Schieberegler an
        speedSlider.setPaintLabels(true); // Zeige die Beschriftungen auf dem Schieberegler an
        speedSlider.setValue(10); // Setze den Standardwert des Schiebereglers auf 10

        // Erzeuge ein Hashtable-Objekt, um benutzerdefinierte Beschriftungen für bestimmte Werte des Schiebereglers festzulegen
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        for (int i = 10; i <= 100; i += 10) {
            labelTable.put(i, new JLabel(Integer.toString(i)));
        }
        speedSlider.setLabelTable(labelTable);

        //Füge dem "modeMenu" die Buttons hinzu und Füge das "modeMenu" der "menuBar" hinzu
        modeMenu.add(runButton);
        modeMenu.add(paintButton);
        modeMenu.add(setButton);
        modeMenu.add(lineButton);
        menuBar.add(modeMenu);

        shapesMenu.add(frameButton);
        shapesMenu.add(crossButton);
        shapesMenu.add(plusButton);
        sliderMenu.add(speedSlider);

        //Füge dem "extraMenu" die verschiedenen Optionen und Buttons hinzu und Füge es der "menuBar" hinzu
        extraMenu.add(clearButton);
        extraMenu.add(setSizeButton);
        extraMenu.add(setColorButton);
        menuBar.add(extraMenu);

        menuBar.add(sliderMenu);

        //Setze den Hintergrund der "menuBar" auf "Light_GRAY"
        menuBar.setBackground(Color.LIGHT_GRAY);

        applySizeButton.setActionCommand("size");
        applySizeButton.setBackground(Color.WHITE);

        setColorFrame.setLayout(new GridLayout(2, 2));
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
        setColorFrame.setResizable(false);

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

        frame.setJMenuBar(menuBar);
        frame.setSize(new Dimension(300, 300));
        frame.setBounds(50,50,200,200);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setClosable(true);
        frame.setIconifiable(true);
        frame.setContentPane(this);
        frame.setResizable(true);
        frame.setVisible(true);
    }

    public void setListeners(ActionListener al, KeyListener kl, MouseListener ml, MouseMotionListener mml, ChangeListener cl, MouseWheelListener mwl) {
        addKeyListener(kl);
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
        aliveCellColorDisplay.addActionListener(al);
        deadCellColorDisplay.addActionListener(al);
        frameButton.addActionListener(al);
        frameButton.addMouseListener(ml);
        crossButton.addActionListener(al);
        crossButton.addMouseListener(ml);
        plusButton.addActionListener(al);
        plusButton.addMouseListener(ml);
        speedSlider.addChangeListener(cl);
        int index = 0;
        for (JMenuItem j : shapes) {
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

    public void updateCellColor(Color aColor, Color dColor) {
        aliveCellColorDisplay.setBackground(aColor);
        deadCellColorDisplay.setBackground(dColor);
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

    public int getSliderstat() {
        return speedSlider.getValue();
    }

    public JInternalFrame getFrame() {
        return frame;
    }

    public void setNewTitle(int number) {
        frame.setTitle("Game of Life " + number);
    }

    public void updateSlider(int i){
        speedSlider.setValue(i);
    }

    public void updateCurrentMode(String mode) {
        modeMenu.removeAll();
        modeMenu.add(runButton);
        modeMenu.add(paintButton);
        modeMenu.add(setButton);
        modeMenu.add(lineButton);
        switch (mode) {
            case "RUNNING" -> {
                modeMenu.remove(runButton);
                modeMenu.setText("Laufen");
            }
            case "SET" -> {
                modeMenu.remove(setButton);
                modeMenu.setText("Setzen");
            }
            case "PAINTING" -> {
                modeMenu.remove(paintButton);
                modeMenu.setText("Malen");
            }
            case "LINE" -> {
                modeMenu.remove(lineButton);
                modeMenu.setText("Linien");
            }
        }
    }
    public void addIFL(InternalFrameListener ifl){
        frame.addInternalFrameListener(ifl);
    }
}
