package second;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 22/06/2023
 */

public class GoLMainController extends GoLAdapter {
    private final Random random = new Random();
    private final GoLMainView mainView = new GoLMainView();
    private final GoLMainModel mainModel = new GoLMainModel();
    private final JFileChooser mainFileChooser = new JFileChooser();
    private final List<GoLController> instances = new ArrayList<>();

    /**
     * Erstellt neuen GoLMainWindow
     */
    public GoLMainController() {
        mainView.initFiguresMenu(mainModel.getPreMadeFigures());
        mainView.setMainListener(this, this, this);
    }

    /**
     * Main-Methode, das Programm wird gestartet.
     *
     * @param args
     */
    public static void main(String[] args) {
        new GoLMainController();
    }

    /**
     * Lädt eine "Figur" aus einem vorher gespeicherten Canvas
     */
    private void loadSavedFigure() {
        int returnValue = mainFileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String filePath = mainFileChooser.getSelectedFile().getAbsolutePath();
            try {
                FileInputStream fs = new FileInputStream(filePath);
                ObjectInputStream os = new ObjectInputStream(fs);
                GoLPrefab m = (GoLPrefab) os.readObject();
                mainModel.updateRecentFigures(m);
                calculateCenter();
                for (GoLController c : instances) {
                    c.setPlacingFigure(true);
                }
                updateRecentFiguresMenu(mainModel.getRecentFigures());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Fehler beim laden des Objekts: " + e.getMessage());
            }
        }
    }

    /**
     * Neues Fenster wird hinzugefügt.
     */
    private void addNewWindow() {
        GoLController controller = new GoLController(this, mainModel);
        JInternalFrame internalFrame = controller.view.getFrame();
        instances.add(controller);
        Point frameSize = new Point(mainView.getWidth(), mainView.getHeight());
        mainView.addInternalFrame(internalFrame, new Point(random.nextInt(0, frameSize.x - internalFrame.getWidth()), random.nextInt(0, frameSize.y - internalFrame.getHeight())));
        updateWindowNumbers();
    }

    /**
     * Berechnet die Mitte der Figur.
     */
    public void calculateCenter() {
        Point center = new Point();
        for (Point p : mainModel.getCurrentFigure().cells()) {
            center.x = Math.max(center.x, p.x);
            center.y = Math.max(center.y, p.y);
        }
        center.x /= 2;
        center.y /= 2;
        mainModel.setCenter(center);
    }

    /**
     * Aktualisiert Fenster Nummern.
     */
    public void updateWindowNumbers() {
        int number = 1;
        for (GoLController c : instances) {
            c.view.addIFL(this);
            c.view.setNewTitle();
            c.setCurrentWindowNumber(number++);
        }
    }

    /**
     * Aktualisiert das Figuren Menu der letzten verwendeten Figuren.
     *
     * @param recent
     */
    public void updateRecentFiguresMenu(ArrayList<GoLPrefab> recent) {
        mainView.updateRecentFiguresMenu(recent, this);
    }

    /**
     * Legt fest, für welchen Fall des ActionCommands was tun soll
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Neues Fenster" -> addNewWindow();
            case "Hotkeys" -> mainView.showHotKeys();
            case "Laden" -> loadSavedFigure();
            case "Alle Laufen" -> {
                for (GoLController c : instances) {
                    c.startRunning();
                }
            }
            case "recent" -> {
                for (GoLController c : instances) {
                    c.setPlacingFigure(true);
                }
                String name = ((JMenuItem) e.getSource()).getText();
                mainModel.updateRecentFigures(name);
                mainView.updateRecentFiguresMenu(mainModel.getRecentFigures(), this);
                calculateCenter();
            }
            default -> {
                int number = Integer.parseInt(e.getActionCommand());
                for (GoLController c : instances) {
                    c.setPlacingFigure(true);
                }
                mainModel.updateRecentFigures(mainModel.getPreMadeFigures(number));
                mainView.updateRecentFiguresMenu(mainModel.getRecentFigures(), this);
                calculateCenter();
            }
        }
    }

    /**
     * Slider Wert wird geändert.
     *
     * @param e a ChangeEvent object
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        for (GoLController c : instances) {
            c.view.updateSlider(mainView.getMainSliderstat());
        }
        updateWindowNumbers();
    }

    @Override
    public void internalFrameClosing(InternalFrameEvent e) {
        instances.removeIf(g -> e.getSource().equals(g.view.getFrame()));
        updateWindowNumbers();
    }
}
