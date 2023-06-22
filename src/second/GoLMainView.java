package second;
import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Hashtable;

public class GoLMainView extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu menu = new JMenu("Menu");
    private final JMenuItem newWindow = new JMenuItem("Neues Fenster");
    private final JMenuItem showHotKeysButton = new JMenuItem("Hotkeys");
    private final JFrame hotKeyFrame = new JFrame("Hotkeys");
    private final JMenuItem runAllButton = new JMenuItem("Alle Laufen");
    private final JMenu m = new JMenu("Auswahl");
    private final JMenu speed = new JMenu("Geschwindigkeit");
    private final JSlider mainSpeedSlider = new JSlider();
    private final JMenu figuresMenu = new JMenu("Figuren");
    private final JMenu staticMenu = new JMenu("Statische");
    private final JMenu oscMenu = new JMenu("Oszillierende");
    private final JMenu shipsMenu = new JMenu("Raum Schiffe");
    private final JMenu methMenu = new JMenu("Methuselahs");
    private final JMenu gunsMenu = new JMenu("Gleiter Kanonen");
    private final JMenu otherMenu = new JMenu("Andere");
    private final ArrayList<JMenuItem> figures = new ArrayList<>();
    private final JMenu recentFiguresMenu = new JMenu("Zuletzt benutzt");
    private final JMenuItem save = new JMenuItem("Speichern");
    private final JMenuItem load = new JMenuItem("Laden");

    public GoLMainView(){
        desktopPane.setDesktopManager(new DefaultDesktopManager());

        setContentPane(desktopPane);
        setTitle("Game of Life Hauptfenster");
        setSize(new Dimension(500,500));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(Color.LIGHT_GRAY);

        menu.add(newWindow);
        menuBar.add(menu);
        setJMenuBar(menuBar);
        m.add(showHotKeysButton);
        m.add(runAllButton);
        speed.add(mainSpeedSlider);
        menuBar.add(m);
        menuBar.add(speed);

        //Füge dem "figuresMenu" die verschiedenen Optionen, Menus, etc. hinzu und Füge es der "menuBar" hinzu
        figuresMenu.add(save);
        figuresMenu.add(load);
        figuresMenu.add(staticMenu);
        figuresMenu.add(oscMenu);
        figuresMenu.add(shipsMenu);
        figuresMenu.add(methMenu);
        figuresMenu.add(gunsMenu);
        figuresMenu.add(otherMenu);
        menuBar.add(figuresMenu);
        menuBar.add(recentFiguresMenu);

        // Konfiguriere den Schieberegler für die Geschwindigkeit
        mainSpeedSlider.setMinimum(1); // Minimaler Wert des Schiebereglers: 1
        mainSpeedSlider.setMaximum(100); // Maximaler Wert des Schiebereglers: 100
        mainSpeedSlider.setMajorTickSpacing(10); // Hauptintervall zwischen den Markierungen: 10
        mainSpeedSlider.setMinorTickSpacing(5); // Nebenintervall zwischen den Markierungen: 5
        mainSpeedSlider.createStandardLabels(5); // Erzeuge Standardbeschriftungen für den Schieberegler
        mainSpeedSlider.setPaintTicks(true); // Zeige die Markierungen auf dem Schieberegler an
        mainSpeedSlider.setPaintLabels(true); // Zeige die Beschriftungen auf dem Schieberegler an
        mainSpeedSlider.setValue(10); // Setze den Standardwert des Schiebereglers auf 10

        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        for (int i = 10; i <= 100; i += 10) {
            labelTable.put(i, new JLabel(Integer.toString(i)));
        }
        mainSpeedSlider.setLabelTable(labelTable);

        hotKeyFrame.setSize(new Dimension(250, 250));
        hotKeyFrame.setResizable(false);
        hotKeyFrame.setLayout(new GridLayout(13, 2));

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

        setVisible(true);
    }

    public void setMainListener(ActionListener al, ChangeListener cl, KeyListener kl){
        newWindow.addActionListener(al);
        showHotKeysButton.addActionListener(al);
        runAllButton.addActionListener(al);
        save.addActionListener(al);
        load.addActionListener(al);
        mainSpeedSlider.addChangeListener(cl);
        int index = 0;
        for (JMenuItem j : figures) {
            j.addActionListener(al);
            j.setActionCommand(String.valueOf(index++));
        }
        addKeyListener(kl);
    }

    public void addInternalFrame(JInternalFrame jif, Point randomPos){
        desktopPane.add(jif);
        jif.setLocation(randomPos);
        jif.setVisible(true);
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

    public void showHotKeys() {
        hotKeyFrame.setVisible(true);
    }

    public int getMainSliderstat() {
        return mainSpeedSlider.getValue();
    }

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
