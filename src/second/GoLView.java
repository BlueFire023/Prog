package second;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 15/06/2023
 */
public class GoLView extends JPanel {
    BufferedImage canvas;
    JMenuBar menuBar = new JMenuBar();
    JMenu menu = new JMenu("Menü");
    JMenuItem clearButton = new JMenuItem("Clear");
    JMenuItem setSizeButton = new JMenuItem("Set Size");
    JMenuItem setColorButton = new JMenuItem("Set Color");
    JFrame setSizeFrame = new JFrame();
    JTextField widthTextArea;
    JTextField heightTextArea;
    JButton applySizeButton = new JButton("Apply");
    JButton applyColorButton = new JButton("Apply");
    JButton activeColorDisplay = new JButton();
    JButton deadColorDisplay = new JButton();
    JFrame frame = new JFrame();

    public GoLView(BufferedImage canvas) {
        this.canvas = canvas;
        widthTextArea = new JTextField(String.valueOf(canvas.getTileWidth()));
        heightTextArea = new JTextField(String.valueOf(canvas.getTileHeight()));
        menu.add(clearButton);
        menu.add(setSizeButton);
        menu.add(setColorButton);
        menuBar.add(menu);
        menuBar.setBackground(Color.LIGHT_GRAY);

        applyColorButton.setActionCommand("color");
        applySizeButton.setActionCommand("size");

        frame.setTitle("Game of Life");
        frame.setJMenuBar(menuBar);
        frame.setSize(new Dimension(1014, 1060));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(this);
        frame.setVisible(true);
    }


    public void setListeners(ActionListener al, KeyListener kl, MouseListener ml, MouseMotionListener mml) {
        frame.addKeyListener(kl);
        frame.addMouseMotionListener(mml);
        frame.addMouseListener(ml);
        clearButton.addActionListener(al);
        setSizeButton.addActionListener(al);
        applySizeButton.addActionListener(al);
        setColorButton.addActionListener(al);
        applyColorButton.addActionListener(al);
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

    public Color[] updateCellColor(Color aColor, Color dColor) {
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
        setColorFrame.add(applyColorButton);
        setColorFrame.setSize(100, 200);
        setColorFrame.setVisible(true);
        activeColorDisplay.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(this, "Wähle eine Farbe", aColor);
            if (newColor == null) {
                newColor = aColor;
            }
            ((JButton) e.getSource()).setBackground(newColor);
        });
        deadColorDisplay.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(this, "Wähle eine Farbe", dColor);
            if (newColor == null) {
                newColor = dColor;
            }
            ((JButton) e.getSource()).setBackground(newColor);
        });

        Color colors[] = new Color[2];
        colors[0] = aColor;
        colors[1] = dColor;

        return colors;
    }
    public Color[] getNewCellColors(){
        Color[] c = new Color[2];
        c[0] = activeColorDisplay.getBackground();
        c[1] = deadColorDisplay.getBackground();
        return c;

    }
    public void updateCanvasObject(BufferedImage canvas){
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
}
