package second;

import org.w3c.dom.css.RGBColor;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 22/06/2023
 */

public class GoLMainView extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private final JFrame hotKeyFrame = new JFrame("Hotkeys");
    private final JSlider mainSpeedSlider = new JSlider();
    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu menu = new JMenu("Menu");
    private final JMenu figuresMenu = new JMenu("Figuren");
    private final JMenu staticMenu = new JMenu("Statische");
    private final JMenu oscMenu = new JMenu("Oszillierende");
    private final JMenu shipsMenu = new JMenu("Raum Schiffe");
    private final JMenu methMenu = new JMenu("Methuselahs");
    private final JMenu gunsMenu = new JMenu("Gleiter Kanonen");
    private final JMenu otherMenu = new JMenu("Andere");
    private final JMenu recentFiguresMenu = new JMenu("Zuletzt benutzt");
    private final JMenu selectionMenu = new JMenu("Auswahl");
    private final JMenu speed = new JMenu("Geschwindigkeit");
    private final JMenuItem newWindow = new JMenuItem("Neues Fenster");
    private final JMenuItem showHotKeysButton = new JMenuItem("Hotkeys");
    private final JMenuItem runAllButton = new JMenuItem("Alle Laufen");
    private final JMenuItem load = new JMenuItem("Laden");
    private final ArrayList<JMenuItem> figures = new ArrayList<>();

    /**
     * Konstruktor der MainView Klasse.
     */
    public GoLMainView() {
        desktopPane.setDesktopManager(new DefaultDesktopManager());

        setContentPane(desktopPane);
        setTitle("Game of Life Hauptfenster");
        setSize(new Dimension(1500, 1000));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(Color.LIGHT_GRAY);
        setJMenuBar(menuBar);

        runAllButton.setSize(menu.getSize());

        menu.add(newWindow);
        menu.add(showHotKeysButton);

        menuBar.add(menu);
        menuBar.add(runAllButton);
        menuBar.add(speed);
        menuBar.add(figuresMenu);
        menuBar.add(recentFiguresMenu);


        figuresMenu.add(load);
        figuresMenu.add(staticMenu);
        figuresMenu.add(oscMenu);
        figuresMenu.add(shipsMenu);
        figuresMenu.add(methMenu);
        figuresMenu.add(gunsMenu);
        figuresMenu.add(otherMenu);

        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        for (int i = 10; i <= 100; i += 10) {
            labelTable.put(i, new JLabel(Integer.toString(i)));
        }
        mainSpeedSlider.setLabelTable(labelTable);
        mainSpeedSlider.setMinimum(1);
        mainSpeedSlider.setMaximum(100);
        mainSpeedSlider.setMajorTickSpacing(10);
        mainSpeedSlider.setMinorTickSpacing(5);
        mainSpeedSlider.createStandardLabels(5);
        mainSpeedSlider.setPaintTicks(true);
        mainSpeedSlider.setPaintLabels(true);
        mainSpeedSlider.setValue(10);

        speed.add(mainSpeedSlider);

        hotKeyFrame.setSize(new Dimension(250, 250));
        hotKeyFrame.setResizable(false);
        hotKeyFrame.setLayout(new GridLayout(13, 2));
        hotKeyFrame.setSize(new Dimension(250, 250));
        hotKeyFrame.setResizable(false);
        hotKeyFrame.setLayout(new GridLayout(11, 2));

        /*JPanel windowLabelPanel = new JPanel(new GridBagLayout());
        JPanel windowPanel = new JPanel(new GridBagLayout());
        JPanel hotkeyLabelPanel = new JPanel(new GridBagLayout());
        JPanel hotkeyPanel = new JPanel(new GridBagLayout());
        */
        JPanel runLabelPanel = new JPanel(new GridBagLayout());
        JPanel runPanel = new JPanel(new GridBagLayout());
        JPanel drawLabelPanel = new JPanel(new GridBagLayout());
        JPanel drawPanel = new JPanel(new GridBagLayout());
        JPanel setLabelPanel = new JPanel(new GridBagLayout());
        JPanel setPanel = new JPanel(new GridBagLayout());
        JPanel lineLabelPanel = new JPanel(new GridBagLayout());
        JPanel linePanel = new JPanel(new GridBagLayout());
        JPanel sizeLabelPanel = new JPanel(new GridBagLayout());
        JPanel sizePanel = new JPanel(new GridBagLayout());
        JPanel clearLabelPanel = new JPanel(new GridBagLayout());
        JPanel clearPanel = new JPanel(new GridBagLayout());
        JPanel colorLabelPanel = new JPanel(new GridBagLayout());
        JPanel colorPanel = new JPanel(new GridBagLayout());
        JPanel nextLabelPanel = new JPanel(new GridBagLayout());
        JPanel nextPanel = new JPanel(new GridBagLayout());
        JPanel flipHLabelPanel = new JPanel(new GridBagLayout());
        JPanel flipHPanel = new JPanel(new GridBagLayout());
        JPanel flipVLabelPanel = new JPanel(new GridBagLayout());
        JPanel flipVPanel = new JPanel(new GridBagLayout());
        JPanel rotateLabelPanel = new JPanel(new GridBagLayout());
        JPanel rotatePanel = new JPanel(new GridBagLayout());
        /*
        windowLabelPanel.add(new JLabel("F"));
        windowPanel.add(new JLabel("Neues Fenster"));

        hotkeyLabelPanel.add(new JLabel("H"));
        hotkeyPanel.add(new JLabel("Hotkeys"));
         */
        runLabelPanel.add(new JLabel("S"));
        runPanel.add(new JLabel("Laufen"));

        drawLabelPanel.add(new JLabel("D"));
        drawPanel.add(new JLabel("Malen"));

        setLabelPanel.add(new JLabel("P"));
        setPanel.add(new JLabel("Setzen"));

        lineLabelPanel.add(new JLabel("L"));
        linePanel.add(new JLabel("Linien"));

        sizeLabelPanel.add(new JLabel("A"));
        sizePanel.add(new JLabel("Auflösung"));

        clearLabelPanel.add(new JLabel("R"));
        clearPanel.add(new JLabel("Löschen"));

        colorLabelPanel.add(new JLabel("C"));
        colorPanel.add(new JLabel("Farben"));

        nextLabelPanel.add(new JLabel("Space"));
        nextPanel.add(new JLabel("Nächste Generation"));

        flipHLabelPanel.add(new JLabel("Rechts Links"));
        flipHPanel.add(new JLabel("Horizontal Spiegeln"));

        flipVLabelPanel.add(new JLabel("Hoch Runter"));
        flipVPanel.add(new JLabel("Vertikal Spiegeln"));

        rotateLabelPanel.add(new JLabel("Mittlere Maus"));
        rotatePanel.add(new JLabel("Rotieren"));

        hotKeyFrame.add(runPanel);
        hotKeyFrame.add(runLabelPanel);
        hotKeyFrame.add(drawPanel);
        hotKeyFrame.add(drawLabelPanel);
        hotKeyFrame.add(setPanel);
        hotKeyFrame.add(setLabelPanel);
        hotKeyFrame.add(linePanel);
        hotKeyFrame.add(lineLabelPanel);
        hotKeyFrame.add(sizePanel);
        hotKeyFrame.add(sizeLabelPanel);
        hotKeyFrame.add(clearPanel);
        hotKeyFrame.add(clearLabelPanel);
        hotKeyFrame.add(colorPanel);
        hotKeyFrame.add(colorLabelPanel);
        /*hotKeyFrame.add(windowPanel);
        hotKeyFrame.add(windowLabelPanel);
        hotKeyFrame.add(hotkeyPanel);
        hotKeyFrame.add(hotkeyLabelPanel);*/
        hotKeyFrame.add(nextPanel);
        hotKeyFrame.add(nextLabelPanel);
        hotKeyFrame.add(flipHPanel);
        hotKeyFrame.add(flipHLabelPanel);
        hotKeyFrame.add(flipVPanel);
        hotKeyFrame.add(flipVLabelPanel);
        hotKeyFrame.add(rotatePanel);
        hotKeyFrame.add(rotateLabelPanel);

        setVisible(true);
    }

    /**
     * HotKeys werden sichtbar gesetzt.
     */
    public void showHotKeys() {
        hotKeyFrame.setVisible(true);
    }

    /**
     * Setzt die Action Listener der HauptKlasse
     *
     * @param al
     * @param cl
     * @param kl
     */
    public void setMainListener(ActionListener al, ChangeListener cl, KeyListener kl) {
        newWindow.addActionListener(al);
        showHotKeysButton.addActionListener(al);
        runAllButton.addActionListener(al);
        load.addActionListener(al);
        mainSpeedSlider.addChangeListener(cl);
        int index = 0;
        for (JMenuItem j : figures) {
            j.addActionListener(al);
            j.setActionCommand(String.valueOf(index++));
        }
        addKeyListener(kl);
    }

    /**
     * JInternalFrame wird hinzugefügt.
     *
     * @param jif
     * @param randomPos
     */
    public void addInternalFrame(JInternalFrame jif, Point randomPos) {
        desktopPane.add(jif);
        jif.setLocation(randomPos);
        try {
            jif.setSelected(true);
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        jif.setVisible(true);
    }

    /**
     * Updated die Auswahl der kürzlich gezeichneten Figuren
     *
     * @param recent
     * @param al
     */
    public void updateRecentFiguresMenu(ArrayList<GoLPrefab> recent, ActionListener al) {
        recentFiguresMenu.removeAll();
        for (int i = recent.size() - 1; i >= Math.max(recent.size() - 1 - 4, 0); i--) {
            JMenuItem item = new JMenuItem(recent.get(i).name());
            item.addActionListener(al);
            item.setActionCommand("recent");
            recentFiguresMenu.add(item);
        }
    }

    /**
     * Gibt den SchiebeReglerWert vom Hauptfenster zurück.
     *
     * @return
     */
    public int getMainSliderstat() {
        return mainSpeedSlider.getValue();
    }

    /**
     * Initialisiert das Figurenmenü.
     *
     * @param preMadeFigures
     */
    public void initFiguresMenu(GoLFigures preMadeFigures) {
        int count = 0;
        for (int i = 0; i < preMadeFigures.getStillLifesCount(); i++) {
            figures.add(new JMenuItem(preMadeFigures.getFigure(i).name()));
            staticMenu.add(figures.get(i));
        }
        count += preMadeFigures.getStillLifesCount();
        for (int i = count; i < preMadeFigures.getOscillatorsCount() + count; i++) {
            figures.add(new JMenuItem(preMadeFigures.getFigure(i).name()));
            oscMenu.add(figures.get(i));
        }
        count += preMadeFigures.getOscillatorsCount();
        for (int i = count; i < preMadeFigures.getSpaceshipsCount() + count; i++) {
            figures.add(new JMenuItem(preMadeFigures.getFigure(i).name()));
            shipsMenu.add(figures.get(i));
        }
        count += preMadeFigures.getSpaceshipsCount();
        for (int i = count; i < preMadeFigures.getMethuselahsCount() + count; i++) {
            figures.add(new JMenuItem(preMadeFigures.getFigure(i).name()));
            methMenu.add(figures.get(i));
        }
        count += preMadeFigures.getMethuselahsCount();
        for (int i = count; i < preMadeFigures.getGgCount() + count; i++) {
            figures.add(new JMenuItem(preMadeFigures.getFigure(i).name()));
            gunsMenu.add(figures.get(i));
        }
        count += preMadeFigures.getGgCount();
        for (int i = count; i < preMadeFigures.getOtherCount() + count; i++) {
            figures.add(new JMenuItem(preMadeFigures.getFigure(i).name()));
            otherMenu.add(figures.get(i));
        }
    }
}
