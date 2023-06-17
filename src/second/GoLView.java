package second;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Hashtable;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 15/06/2023
 */
public class GoLView extends JPanel {
    private BufferedImage canvas;
    private JMenuBar menuBar = new JMenuBar();
    private JMenu optionsMenu = new JMenu("Menü");
    private JMenu figuresMenu = new JMenu("Figuren");
    private JMenu sliderMenu = new JMenu("Geschwindigkeit");
    private JSlider gSlider = new JSlider();
    private JMenuItem save = new JMenuItem("Speichern");
    private JMenuItem load = new JMenuItem("Laden");
    private JMenuItem clearButton = new JMenuItem("Löschen");
    private JMenuItem setSizeButton = new JMenuItem("Auflösung");
    private JMenuItem setColorButton = new JMenuItem("Farben");
    private JMenuItem startButton = new JMenuItem("Laufen");
    private JMenuItem malenButton = new JMenuItem("Malen");
    private JMenuItem setzenButton = new JMenuItem("Setzen");
    private JFrame setSizeFrame = new JFrame();
    private JTextField test = new JTextField("");
    private JTextField widthTextArea;
    private JTextField heightTextArea;
    private JButton applySizeButton = new JButton("Apply");
    private JButton activeColorDisplay = new JButton();
    private JButton deadColorDisplay = new JButton();
    private JFrame frame = new JFrame();
    private JMenuItem newWindow = new JMenuItem("Neues Fenster");

    public GoLView(BufferedImage canvas) {
        this.canvas = canvas;
        widthTextArea = new JTextField(String.valueOf(canvas.getTileWidth()));
        widthTextArea.setColumns(7);
        heightTextArea = new JTextField(String.valueOf(canvas.getTileHeight()));
        heightTextArea.setColumns(7);

        gSlider.setMinimum(1);
        gSlider.setMaximum(100);
        gSlider.setMajorTickSpacing(10);
        gSlider.setMinorTickSpacing(5);
        gSlider.createStandardLabels(5);
        gSlider.setPaintTicks(true);
        gSlider.setPaintLabels(true);
        gSlider.setValue(10);
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        for (int i = 10; i <= 100; i += 10) {
            labelTable.put(i, new JLabel(Integer.toString(i)));
        }
        gSlider.setLabelTable(labelTable);


        optionsMenu.add(clearButton);
        optionsMenu.add(setSizeButton);
        optionsMenu.add(setColorButton);
        optionsMenu.add(startButton);
        optionsMenu.add(malenButton);
        optionsMenu.add(setzenButton);
        optionsMenu.add(newWindow);
        menuBar.add(optionsMenu);
        figuresMenu.add(save);
        figuresMenu.add(load);
        menuBar.add(figuresMenu);
        sliderMenu.add(gSlider);
        menuBar.add(sliderMenu);
        menuBar.setBackground(Color.LIGHT_GRAY);

        applySizeButton.setActionCommand("size");
        activeColorDisplay.setActionCommand("acc");
        deadColorDisplay.setActionCommand("dcc");
        test.setActionCommand("h");
        test.setColumns(7);

        frame.setTitle("Game of Life");
        frame.setJMenuBar(menuBar);
        frame.setSize(new Dimension(1014, 1060));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(this);
        frame.setVisible(true);
    }


    public void setListeners(ActionListener al, KeyListener kl, MouseListener ml, MouseMotionListener mml, ChangeListener cl) {
        frame.addKeyListener(kl);
        addMouseMotionListener(mml);
        addMouseListener(ml);
        clearButton.addActionListener(al);
        setSizeButton.addActionListener(al);
        applySizeButton.addActionListener(al);
        setColorButton.addActionListener(al);
        startButton.addActionListener(al);
        malenButton.addActionListener(al);
        setzenButton.addActionListener(al);
        newWindow.addActionListener(al);
        activeColorDisplay.addActionListener(al);
        deadColorDisplay.addActionListener(al);
        save.addActionListener(al);
        load.addActionListener(al);
        test.addActionListener(al);
        gSlider.addChangeListener(cl);

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

    public void figureSelect() {
        JFrame figureFrame = new JFrame();
        figureFrame.setSize(new Dimension(500,500));
        figureFrame.setLayout(new FlowLayout());
        figureFrame.add(test);
        figureFrame.setVisible(true);
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

    public int getFrameWidth() {
        return frame.getWidth();
    }

    public int getChoosenFigure() {
        return Integer.parseInt(test.getText());
    }
  
    public int getSliderstat(){ return gSlider.getValue();}
}
