package second;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
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
    private final JMenu saveMenu = new JMenu("Datei");
    private final JMenuItem saveButton = new JMenuItem("Speichern");
    private final JFrame setSizeFrame = new JFrame();
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

    /**
     * HIER FEHLT NOCH EIN KOMMENTAR HIER FEHLT NOCH EIN KOMMENTAR !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     *
     * @param canvas
     */
    public GoLView(BufferedImage canvas) {
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
        modeMenu.add(runButton);
        modeMenu.add(paintButton);
        modeMenu.add(setButton);
        modeMenu.add(lineButton);
        menuBar.add(modeMenu);
        shapesMenu.add(frameButton);
        shapesMenu.add(crossButton);
        shapesMenu.add(plusButton);
        sliderMenu.add(speedSlider);
        menuBar.add(shapesMenu);
        saveMenu.add(saveButton);
        menuBar.add(saveMenu);
        extraMenu.add(clearButton);
        extraMenu.add(setSizeButton);
        extraMenu.add(setColorButton);
        menuBar.add(extraMenu);
        menuBar.add(sliderMenu);
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
        frame.setSize(new Dimension(800, 800));
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setClosable(true);
        frame.setIconifiable(true);
        frame.setContentPane(this);
        frame.setResizable(true);
        frame.setVisible(true);
    }

    /**
     * HIER FEHLT NOCH EIN KOMMENTAR HIER FEHLT NOCH EIN KOMMENTAR !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
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
        saveButton.addActionListener(al);
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
    }

    /**
     * HIER FEHLT NOCH EIN KOMMENTAR HIER FEHLT NOCH EIN KOMMENTAR !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    public void updateCanvasSize() {
        setSizeFrame.setVisible(true);
    }

    /**
     * HIER FEHLT NOCH EIN KOMMENTAR HIER FEHLT NOCH EIN KOMMENTAR !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    public void disposeSetSizeFrame() {
        setSizeFrame.dispose();
    }

    /**
     * HIER FEHLT NOCH EIN KOMMENTAR HIER FEHLT NOCH EIN KOMMENTAR !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     *
     * @param aColor
     * @param dColor
     */
    public void updateCellColor(Color aColor, Color dColor) {
        aliveCellColorDisplay.setBackground(aColor);
        deadCellColorDisplay.setBackground(dColor);
        setColorFrame.setVisible(true);
    }

    /**
     * HIER FEHLT NOCH EIN KOMMENTAR HIER FEHLT NOCH EIN KOMMENTAR !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     *
     * @param canvas
     */
    public void updateCanvasObject(BufferedImage canvas) {
        this.canvas = canvas;
    }

    /**
     * HIER FEHLT NOCH EIN KOMMENTAR HIER FEHLT NOCH EIN KOMMENTAR !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     *
     * @param g the <code>Graphics</code> object to protect
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(canvas, 0, 0, getWidth(), getHeight(), null);
        repaint();
    }

    /**
     * HIER FEHLT NOCH EIN KOMMENTAR HIER FEHLT NOCH EIN KOMMENTAR !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     *
     * @return
     */
    public int getNewWidth() {
        return Integer.parseInt(widthTextArea.getText());
    }

    /**
     * HIER FEHLT NOCH EIN KOMMENTAR HIER FEHLT NOCH EIN KOMMENTAR !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     *
     * @return
     */
    public int getNewHeight() {
        return Integer.parseInt(heightTextArea.getText());
    }

    /**
     * HIER FEHLT NOCH EIN KOMMENTAR HIER FEHLT NOCH EIN KOMMENTAR !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     *
     * @return
     */
    public int getSliderstat() {
        return speedSlider.getValue();
    }

    /**
     * HIER FEHLT NOCH EIN KOMMENTAR HIER FEHLT NOCH EIN KOMMENTAR !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     *
     * @return
     */
    public JInternalFrame getFrame() {
        return frame;
    }

    /**
     * HIER FEHLT NOCH EIN KOMMENTAR HIER FEHLT NOCH EIN KOMMENTAR !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     *
     * @param number
     */
    public void setNewTitle(int number) {
        frame.setTitle("Game of Life " + number);
    }

    /**
     * HIER FEHLT NOCH EIN KOMMENTAR HIER FEHLT NOCH EIN KOMMENTAR !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     *
     * @param i
     */
    public void updateSlider(int i) {
        speedSlider.setValue(i);
    }

    /**
     * HIER FEHLT NOCH EIN KOMMENTAR HIER FEHLT NOCH EIN KOMMENTAR !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
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
     * HIER FEHLT NOCH EIN KOMMENTAR HIER FEHLT NOCH EIN KOMMENTAR !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     *
     * @param ifl
     */
    public void addIFL(InternalFrameListener ifl) {
        frame.addInternalFrameListener(ifl);
    }
}