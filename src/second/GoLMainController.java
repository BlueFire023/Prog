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
    private final List<GoLController> instances = new ArrayList<>();
    private final GoLMainView mainView = new GoLMainView();
    private final GoLMainModel mainModel = new GoLMainModel();
    private final Random random = new Random();
    private final JFileChooser mainFileChooser = new JFileChooser();

    /**
     * Erstellt neuen GoLMainWindow
     */
    public GoLMainController() {
        mainView.initFiguresMenu(mainModel.getPreMadeFigures());
        mainView.setMainListener(this, this, this);
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
