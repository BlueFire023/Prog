package second;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GoLMainController extends GoLAdapter {
    private final List<GoLController> instances = new ArrayList<>();
    private final GoLMainView mainView = new GoLMainView();
    private final GoLMainModel mainModel = new GoLMainModel();
    private final Random random = new Random();

    /**
     * Erstellt neuen GoLMainWindow
     */
    public GoLMainController() {
        mainView.initFiguresMenu(mainModel.getPreMadeFigures());
        mainView.setMainListener(this, this, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Neues Fenster" -> {
                GoLController controller = new GoLController(this, mainModel);
                JInternalFrame internalFrame = controller.view.getFrame();
                instances.add(controller);
                Point frameSize = new Point(mainView.getWidth(), mainView.getHeight());
                mainView.addInternalFrame(internalFrame, new Point(random.nextInt(0,frameSize.x - internalFrame.getWidth()), random.nextInt(0,frameSize.y - internalFrame.getHeight())));
                update();
            }
            case "Hotkeys" -> mainView.showHotKeys();
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

    public void update() {
        int number = 1;
        for (GoLController c : instances) {
            c.view.addIFL(this);
            c.view.setNewTitle(number++);
        }
    }

    public static void main(String[] args) {
        new GoLMainController();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        for (GoLController c : instances) {
            c.view.updateSlider(mainView.getMainSliderstat());
        }
        update();
    }

    @Override
    public void internalFrameClosing(InternalFrameEvent e) {
        instances.removeIf(g -> e.getSource().equals(g.view.getFrame()));
        update();
    }

    private void calculateCenter() {
        Point center = new Point();
        for (Point p : mainModel.getCurrentFigure().cells()) {
            center.x = Math.max(center.x, p.x);
            center.y = Math.max(center.y, p.y);
        }
        center.x /= 2;
        center.y /= 2;
        mainModel.setCenter(center);
    }

    public void updateRecentFiguresMenu(ArrayList<GoLPrefab> recent) {
        mainView.updateRecentFiguresMenu(recent, this);
    }
}
