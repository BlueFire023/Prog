package second.GoL;

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

public class MainController extends Adapter {
    private final Random random = new Random();
    private final MainView mainView = new MainView();
    private final MainModel mainModel = new MainModel();
    private final JFileChooser mainFileChooser = new JFileChooser();
    private final List<Controller> instances = new ArrayList<>();
    private boolean allRunning = false;

    /**
     * Erstellt neuen GoLMainWindow
     */
    public MainController() {
        mainView.initFiguresMenu(mainModel.getPreMadeFigures());
        mainView.setMainListener(this, this, this);
    }

    /**
     * Main-Methode, das Programm wird gestartet.
     *
     * @param args
     */
    public static void main(String[] args) {
        new MainController();
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
                Prefab m = (Prefab) os.readObject();
                mainModel.updateRecentFigures(m);
                calculateCenter();
                for (Controller c : instances) {
                    c.setPlacingFigure(true);
                }
                mainView.updateRunButton(false);
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
        Controller controller = new Controller(this, mainModel);
        JInternalFrame internalFrame = controller.getView().getFrame();
        instances.add(controller);
        if (mainView.getWidth() < 1500 && mainView.getHeight() < 1000) {
            internalFrame.setSize(new Dimension(mainView.getHeight() - 50, mainView.getHeight() - 50));
        }
        mainView.addInternalFrame(internalFrame, new Point(random.nextInt(0, mainView.getWidth() - internalFrame.getWidth()), random.nextInt(0, mainView.getHeight() - internalFrame.getHeight())));
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
        for (Controller c : instances) {
            c.getView().addIFL(this);
            c.getView().setNewTitle();
            c.getView().updateSetColorFrameTitle();
            c.getView().updateSetSizeFrameTitle();
            c.setCurrentWindowNumber(number++);
        }
    }

    /**
     * Aktualisiert das Figuren Menu der letzten verwendeten Figuren.
     *
     * @param recent
     */
    public void updateRecentFiguresMenu(ArrayList<Prefab> recent) {
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
            case "run" -> {
                mainView.updateRunButton(((JMenuItem) e.getSource()).getText().equals("Alle Laufen"));
                if (allRunning) {
                    allRunning = false;
                    for (Controller c : instances) {
                        c.stopRunning();
                    }
                } else {
                    allRunning = true;
                    for (Controller c : instances) {
                        c.startRunning();
                    }
                }
            }
            case "recent" -> {
                allRunning = false;
                mainView.updateRunButton(false);
                for (Controller c : instances) {
                    c.setPlacingFigure(true);
                }
                String name = ((JMenuItem) e.getSource()).getText();
                mainModel.updateRecentFigures(name);
                mainView.updateRecentFiguresMenu(mainModel.getRecentFigures(), this);
                calculateCenter();
            }
            default -> {
                allRunning = false;
                mainView.updateRunButton(false);
                int number = Integer.parseInt(e.getActionCommand());
                for (Controller c : instances) {
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
        for (Controller c : instances) {
            c.getView().updateSlider(mainView.getMainSliderstat());
        }

    }

    /**
     * Aktualisiert die noch vorhanden Instances an GoLController und disposed alle übrigen Frames
     *
     * @param e an {@code InternalFrameEvent} with information about the
     *          {@code JInteralFrame} that originated the event
     */
    @Override
    public void internalFrameClosing(InternalFrameEvent e) {
        for (Controller c : instances) {
            if (e.getSource().equals(c.getView().getFrame())) {
                c.getView().disposeSetSizeFrame();
                c.getView().disposeSetColorFrame();
                instances.remove(c);
                break;
            }
        }
        updateWindowNumbers();
        updateAllRunButton();
    }

    /**
     * Setzt den AllRunButton auf alle Stoppen
     */
    public void updateAllRunButton() {
        for (Controller c : instances) {
            if (c.getActiveMode().equals("RUNNING")) {
                mainView.updateRunButton(true);
                allRunning = true;
                return;
            }
        }
        mainView.updateRunButton(false);
        allRunning = false;
    }
}
