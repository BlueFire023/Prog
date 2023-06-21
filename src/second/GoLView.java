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
    private final JMenu modeMenu = new JMenu("Modus");
    private final JMenu extraMenu = new JMenu("Extras");
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
    private final JMenuItem showHotKeysButton = new JMenuItem("Hotkeys");
    private final JMenuItem runButton = new JMenuItem("Laufen");
    private final JMenuItem paintButton = new JMenuItem("Malen");
    private final JMenuItem setButton = new JMenuItem("Setzen");
    private final JMenuItem lineButton = new JMenuItem("Linien");
    private final JMenuItem frameButton = new JMenuItem("Rahmen");
    private final JMenuItem crossButton = new JMenuItem("Kreuz");
    private final JMenuItem plusButton = new JMenuItem("Plus");
    private final JFrame setSizeFrame = new JFrame();
    private final JFrame hotKeyFrame = new JFrame("Hotkeys");
    private final JTextField widthTextArea;
    private final JTextField heightTextArea;
    private final JButton applySizeButton = new JButton("Apply");
    private final JButton aliveCellColorDisplay = new JButton();
    private final JButton deadCellColorDisplay = new JButton();
    private final JFrame setColorFrame = new JFrame();
    private final JLabel aliveCellColorTag = new JLabel(" Lebende Zellen:");
    private final JLabel deadCellColorTag = new JLabel(" Tote Zellen:");
    private final JInternalFrame frame = new JInternalFrame();
    private final JMenuItem newWindow = new JMenuItem("Neues Fenster");
    private BufferedImage canvas;
    private final int stillLifesCount = 8, oscillatorsCount = 9, spaceshipsCount = 4, methuselahsCount = 3, ggCount = 2, otherCount = 4;

    public GoLView(BufferedImage canvas, int openWindows) {
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

        //Füge dem "figuresMenu" die verschiedenen Optionen, Menus, etc. hinzu und Füge es der "menuBar" hinzu
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
        menuBar.add(recentFiguresMenu);

        //Füge der "menuBar" das "sliderMenu" hinzu und füge dem den "speedSlider" hinzu
        sliderMenu.add(speedSlider);
        menuBar.add(sliderMenu);

        //Füge dem "extraMenu" die verschiedenen Optionen und Buttons hinzu und Füge es der "menuBar" hinzu
        extraMenu.add(clearButton);
        extraMenu.add(setSizeButton);
        extraMenu.add(setColorButton);
        extraMenu.add(newWindow);
        extraMenu.add(showHotKeysButton);
        menuBar.add(extraMenu);

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

        hotKeyFrame.setSize(new Dimension(250, 250));
        hotKeyFrame.setResizable(false);
        hotKeyFrame.setLayout(new GridLayout(13, 2));

        JPanel runLabelPanel = new JPanel(new GridBagLayout());
        runLabelPanel.add(new JLabel("S"));
        JPanel runPanel = new JPanel(new GridBagLayout());
        runPanel.add(new JLabel("Laufen"));
        hotKeyFrame.add(runPanel);
        hotKeyFrame.add(runLabelPanel);

        JPanel drawLabelPanel = new JPanel(new GridBagLayout());
        drawLabelPanel.add(new JLabel("D"));
        JPanel drawPanel = new JPanel(new GridBagLayout());
        drawPanel.add(new JLabel("Malen"));
        ;
        hotKeyFrame.add(drawPanel);
        hotKeyFrame.add(drawLabelPanel);

        JPanel setLabelPanel = new JPanel(new GridBagLayout());
        setLabelPanel.add(new JLabel("P"));
        JPanel setPanel = new JPanel(new GridBagLayout());
        setPanel.add(new JLabel("Setzen"));
        hotKeyFrame.add(setPanel);
        hotKeyFrame.add(setLabelPanel);

        JPanel lineLabelPanel = new JPanel(new GridBagLayout());
        lineLabelPanel.add(new JLabel("L"));
        JPanel linePanel = new JPanel(new GridBagLayout());
        linePanel.add(new JLabel("Linien"));
        hotKeyFrame.add(linePanel);
        hotKeyFrame.add(lineLabelPanel);

        JPanel sizeLabelPanel = new JPanel(new GridBagLayout());
        sizeLabelPanel.add(new JLabel("A"));
        JPanel sizePanel = new JPanel(new GridBagLayout());
        sizePanel.add(new JLabel("Auflösung"));
        hotKeyFrame.add(sizePanel);
        hotKeyFrame.add(sizeLabelPanel);

        JPanel clearLabelPanel = new JPanel(new GridBagLayout());
        clearLabelPanel.add(new JLabel("R"));
        JPanel clearPanel = new JPanel(new GridBagLayout());
        clearPanel.add(new JLabel("Löschen"));
        hotKeyFrame.add(clearPanel);
        hotKeyFrame.add(clearLabelPanel);

        JPanel colorLabelPanel = new JPanel(new GridBagLayout());
        colorLabelPanel.add(new JLabel("C"));
        JPanel colorPanel = new JPanel(new GridBagLayout());
        colorPanel.add(new JLabel("Farben"));
        hotKeyFrame.add(colorPanel);
        hotKeyFrame.add(colorLabelPanel);

        JPanel windowLabelPanel = new JPanel(new GridBagLayout());
        windowLabelPanel.add(new JLabel("F"));
        JPanel windowPanel = new JPanel(new GridBagLayout());
        windowPanel.add(new JLabel("Neues Fenster"));
        hotKeyFrame.add(windowPanel);
        hotKeyFrame.add(windowLabelPanel);

        JPanel hotkeyLabelPanel = new JPanel(new GridBagLayout());
        hotkeyLabelPanel.add(new JLabel("H"));
        JPanel hotkeyPanel = new JPanel(new GridBagLayout());
        hotkeyPanel.add(new JLabel("Hotkeys"));
        hotKeyFrame.add(hotkeyPanel);
        hotKeyFrame.add(hotkeyLabelPanel);

        JPanel nextLabelPanel = new JPanel(new GridBagLayout());
        nextLabelPanel.add(new JLabel("Space"));
        JPanel nextPanel = new JPanel(new GridBagLayout());
        nextPanel.add(new JLabel("Nächste Generation"));
        hotKeyFrame.add(nextPanel);
        hotKeyFrame.add(nextLabelPanel);

        JPanel flipHLabelPanel = new JPanel(new GridBagLayout());
        flipHLabelPanel.add(new JLabel("Rechts Links"));
        JPanel flipHPanel = new JPanel(new GridBagLayout());
        flipHPanel.add(new JLabel("Horizontal Spiegeln"));
        hotKeyFrame.add(flipHPanel);
        hotKeyFrame.add(flipHLabelPanel);

        JPanel flipVLabelPanel = new JPanel(new GridBagLayout());
        flipVLabelPanel.add(new JLabel("Hoch Runter"));
        JPanel flipVPanel = new JPanel(new GridBagLayout());
        flipVPanel.add(new JLabel("Vertikal Spiegeln"));
        hotKeyFrame.add(flipVPanel);
        hotKeyFrame.add(flipVLabelPanel);

        JPanel rotateLabelPanel = new JPanel(new GridBagLayout());
        rotateLabelPanel.add(new JLabel("Mittlere Maus"));
        JPanel rotatePanel = new JPanel(new GridBagLayout());
        rotatePanel.add(new JLabel("Rotieren"));
        hotKeyFrame.add(rotatePanel);
        hotKeyFrame.add(rotateLabelPanel);

        frame.setTitle("Game of Life " + openWindows);
        frame.setJMenuBar(menuBar);
        frame.setSize(new Dimension(300, 300));
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setClosable(true);
        frame.setIconifiable(true);
        frame.setContentPane(this);
        frame.setVisible(true);
    }

    public void setListeners(ActionListener al, KeyListener kl, MouseListener ml, MouseMotionListener mml, ChangeListener cl, WindowFocusListener wfl, WindowListener wl, MouseWheelListener mwl) {
        addKeyListener(kl);
        addMouseWheelListener(mwl);
        addMouseMotionListener(mml);
        addMouseListener(ml);
        clearButton.addActionListener(al);
        setSizeButton.addActionListener(al);
        applySizeButton.addActionListener(al);
        setColorButton.addActionListener(al);
        showHotKeysButton.addActionListener(al);
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
        //frame.addWindowFocusListener(wfl);
        speedSlider.addChangeListener(cl);
        //frame.addWindowListener(wl);
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

    public void updateRecentFiguresMenu(ArrayList<GoLPrefab> recent, ActionListener al) {
        recentFiguresMenu.removeAll();
        for (int i = recent.size() - 1; i >= Math.max(recent.size() - 1 - 4, 0); i--) {
            JMenuItem item = new JMenuItem(recent.get(i).name());
            item.addActionListener(al);
            item.setActionCommand("recent");
            recentFiguresMenu.add(item);
        }
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

    public JInternalFrame getFrame() {
        return frame;
    }

    public void setNewTitle(int number) {
        frame.setTitle("Game of Life " + number);
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

    public void showHotKeys() {
        hotKeyFrame.setVisible(true);
    }
}
