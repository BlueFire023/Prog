package second;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Hashtable;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 15/06/2023
 */

public class GoLView extends JPanel {
    private final JTextField widthTextArea;
    private final JTextField heightTextArea;
    private final JLabel aliveCellColorTag = new JLabel(" Lebende Zellen:");
    private final JLabel deadCellColorTag = new JLabel(" Tote Zellen:");
    private final JSlider speedSlider = new JSlider();
    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu modeMenu = new JMenu("Modus");
    private final JMenu extraMenu = new JMenu("Extras");
    private final JMenu shapesMenu = new JMenu("Formen");
    private final JMenu sliderMenu = new JMenu("Geschwindigkeit");
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
    private final JMenuItem saveButton = new JMenuItem("Speichern");
    private final JButton applySizeButton = new JButton("Apply");
    private final JButton aliveCellColorDisplay = new JButton();
    private final JButton deadCellColorDisplay = new JButton();
    private final JFrame setSizeFrame = new JFrame();
    private final JFrame setColorFrame = new JFrame();
    private final JInternalFrame frame = new JInternalFrame();
    private final GoLModel model;

    /**
     * Konstruktor der View Klasse.
     *
     * @param model
     */
    public GoLView(GoLModel model) {

        this.model = model;

        widthTextArea = new JTextField(String.valueOf(model.getCanvas().getTileWidth()));
        widthTextArea.setColumns(7);

        heightTextArea = new JTextField(String.valueOf(model.getCanvas().getTileHeight()));
        heightTextArea.setColumns(7);

        shapesMenu.add(frameButton);
        shapesMenu.add(crossButton);
        shapesMenu.add(plusButton);

        modeMenu.add(runButton);
        modeMenu.add(paintButton);
        modeMenu.add(setButton);
        modeMenu.add(lineButton);

        extraMenu.add(clearButton);
        extraMenu.add(setSizeButton);
        extraMenu.add(setColorButton);

        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        for (int i = 10; i <= 100; i += 10) {
            labelTable.put(i, new JLabel(Integer.toString(i)));
        }

        speedSlider.setMinimum(1);
        speedSlider.setMaximum(100);
        speedSlider.setMajorTickSpacing(10);
        speedSlider.setMinorTickSpacing(5);
        speedSlider.createStandardLabels(5);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.setValue(10);
        speedSlider.setLabelTable(labelTable);

        saveButton.setMargin(new Insets(2,2,2,2));
        saveButton.setMaximumSize(new Dimension(saveButton.getPreferredSize().width - 10, saveButton.getPreferredSize().height));
        saveButton.setFocusable(false);
        saveButton.setBackground(Color.LIGHT_GRAY);

        sliderMenu.add(speedSlider);

        menuBar.add(modeMenu);
        menuBar.add(shapesMenu);
        menuBar.add(saveButton);
        menuBar.add(extraMenu);
        menuBar.add(sliderMenu);
        menuBar.setBackground(Color.LIGHT_GRAY);

        JPanel deadLayoutPanel = new JPanel();
        JPanel aliveLayoutPanel = new JPanel();
        JPanel widthPanel = new JPanel(new GridLayout(1, 2));
        JPanel widthLabelLayout = new JPanel(new GridBagLayout());
        JPanel heightPanel = new JPanel(new GridLayout(1, 2));
        JPanel heightLabelLayout = new JPanel(new GridBagLayout());
        JPanel widthPanelLayout = new JPanel(new GridBagLayout());
        JPanel heightPanelLayout = new JPanel(new GridBagLayout());
        JPanel applyPanel = new JPanel(new GridBagLayout());

        JLabel widthLabel = new JLabel("Breite:");
        JLabel heightLabel = new JLabel("Höhe:");

        aliveLayoutPanel.setLayout(new GridBagLayout());
        aliveLayoutPanel.add(aliveCellColorDisplay);

        deadLayoutPanel.setLayout(new GridBagLayout());
        deadLayoutPanel.add(deadCellColorDisplay);

        setColorFrame.setLayout(new GridLayout(2, 2));
        setColorFrame.setTitle("Farben");
        setColorFrame.add(aliveCellColorTag);
        setColorFrame.add(aliveLayoutPanel);
        setColorFrame.add(deadCellColorTag);
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
        setSizeFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSizeFrame.add(heightPanel);
        setSizeFrame.add(widthPanel);
        setSizeFrame.add(applyPanel);

        widthLabelLayout.add(widthLabel);
        widthPanelLayout.add(widthTextArea);
        widthPanel.add(widthLabelLayout);
        widthPanel.add(widthPanelLayout);

        heightLabelLayout.add(heightLabel);
        heightPanel.add(heightLabelLayout);
        heightPanelLayout.add(heightTextArea);
        heightPanel.add(heightPanelLayout);

        frame.setJMenuBar(menuBar);
        frame.setSize(new Dimension(800, 800));
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setClosable(true);
        frame.setIconifiable(true);
        frame.setContentPane(this);
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setMaximizable(true);

        applySizeButton.setActionCommand("size");
        applySizeButton.setBackground(Color.WHITE);
        applyPanel.add(applySizeButton);
    }

    /**
     * Listener werden hinzugefügt.
     *
     * @param al
     * @param kl
     * @param ml
     * @param mml
     * @param cl
     * @param mwl
     */
    public void setListeners(ActionListener al, KeyListener kl, MouseListener ml, MouseMotionListener mml, ChangeListener cl, MouseWheelListener mwl) {
        addKeyListener(kl);
        addMouseWheelListener(mwl);
        addMouseMotionListener(mml);
        addMouseListener(ml);

        speedSlider.addChangeListener(cl);
        aliveCellColorDisplay.addActionListener(al);
        deadCellColorDisplay.addActionListener(al);
        saveButton.addActionListener(al);
        clearButton.addActionListener(al);
        setSizeButton.addActionListener(al);
        setColorButton.addActionListener(al);
        runButton.addActionListener(al);
        paintButton.addActionListener(al);
        setButton.addActionListener(al);
        lineButton.addActionListener(al);
        frameButton.addActionListener(al);
        frameButton.addMouseListener(ml);
        crossButton.addActionListener(al);
        crossButton.addMouseListener(ml);
        plusButton.addActionListener(al);
        plusButton.addMouseListener(ml);
        applySizeButton.addActionListener(al);
    }

    /**
     * Updated den aktuellen den aktuellen Modus
     *
     * @param mode
     */
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

    /**
     * Canvas Größe wird aktualisiert.
     */
    public void updateCanvasSize() {
        updateSetSizeFrameTitle();
        setSizeFrame.setVisible(true);
    }

    public void updateSetSizeFrameTitle(){
        setSizeFrame.setTitle("Auflösung " + model.getCurrentWindowNumber());
    }

    /**
     * Farbe der Zellen wird aktualisiert.
     *
     * @param aColor
     * @param dColor
     */
    public void updateCellColor(Color aColor, Color dColor) {
        updateSetColorFrameTitle();
        aliveCellColorDisplay.setBackground(aColor);
        deadCellColorDisplay.setBackground(dColor);
        setColorFrame.setVisible(true);
    }
    public void updateSetColorFrameTitle(){
        setColorFrame.setTitle("Farben " + model.getCurrentWindowNumber());
    }

    /**
     * Schieberegler wird aktualisiert
     *
     * @param i
     */
    public void updateSlider(int i) {
        speedSlider.setValue(i);
    }

    /**
     * Fügt den InternalFrameListener hinzu
     *
     * @param ifl
     */
    public void addIFL(InternalFrameListener ifl) {
        frame.addInternalFrameListener(ifl);
    }

    /**
     * Neue Titel Number wird gesetzt.
     *
     * @param
     */
    public void setNewTitle() {
        frame.setTitle("Game of Life " + model.getCurrentWindowNumber());
    }

    /**
     * Ausgewählte Fläche wird eingefärbt
     *
     * @param g the <code>Graphics</code> object to protect
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(model.getCanvas(), 0, 0, getWidth(), getHeight(), null);
        repaint();
    }

    /**
     * Schließt das Fenster mit den Auflösungen.
     */
    public void disposeSetSizeFrame() {
        setSizeFrame.dispose();
    }

    /**
     * Breite wird aktualisiert
     *
     * @return
     */
    public int getNewWidth() {
        return Integer.parseInt(widthTextArea.getText());
    }

    /**
     * Höhe wird aktualisiert
     *
     * @return
     */
    public int getNewHeight() {
        return Integer.parseInt(heightTextArea.getText());
    }

    /**
     * Status des Schiebereglers wird aktualisiert
     *
     * @return
     */
    public int getSliderstat() {
        return speedSlider.getValue();
    }

    /**
     * JInternalFrame wird aktualisiert.
     *
     * @return
     */
    public JInternalFrame getFrame() {
        return frame;
    }

    /**
     * Schließt das Farben-Fenster
     */
    public void disposeSetColorFrame(){
        setColorFrame.dispose();
    }
}