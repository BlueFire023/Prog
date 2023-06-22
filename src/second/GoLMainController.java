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
    private final GoLModel model = new GoLModel();
    private final Random random = new Random();

    /**
     * Erstellt neuen GoLMainWindow
     */
    public GoLMainController() {
        mainView.initFiguresMenu(model.getPreMadeFigures());
        mainView.setMainListener(this, this, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Neues Fenster" -> {
                GoLController controller = new GoLController(this);
                JInternalFrame internalFrame = controller.view.getFrame();
                instances.add(controller);
                Point frameSize = new Point(mainView.getWidth(), mainView.getHeight());
                mainView.addInternalFrame(internalFrame, new Point(random.nextInt(frameSize.x), random.nextInt(frameSize.y)));
                update();
            }
            case "Hotkeys" -> mainView.showHotKeys();
            case "Alle Laufen" -> {
                for (GoLController c : instances) {
                    c.startRunning();
                }
            }
            default -> {
                int number = Integer.parseInt(e.getActionCommand());
                System.out.println(number);
                for(GoLController c : instances){
                    c.setPlacingFigure(true);
                }
                model.updateRecentFigures(model.getPreMadeFigures(number));
                mainView.updateRecentFiguresMenu(model.getRecentFigures(), this);
                calculateCenter();
            }
        }
    }

    public void update(){
        int number = 1;
        for(GoLController c : instances){
            c.view.addIFL(this);
            c.view.setNewTitle(number++);
        }
    }

    public static void main(String[] args) {
        new GoLMainController();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        for(GoLController c : instances){
            c.view.updateSlider(mainView.getMainSliderstat());
        }
        update();
    }

    @Override
    public void internalFrameClosing(InternalFrameEvent e) {
        System.out.println(e.getSource());
        instances.removeIf(g -> e.getSource().equals(g.view.getFrame()));
        instances.forEach(g -> System.out.println(e.getSource().equals(g.view.getFrame())));
    }

    private void calculateCenter() {
        Point center = new Point();
        for (Point p : model.getCurrentFigure().cells()) {
            center.x = Math.max(center.x, p.x);
            center.y = Math.max(center.y, p.y);
        }
        center.x /= 2;
        center.y /= 2;
        model.setCenter(center);
    }
    public void updateRecentFiguresMenu(ArrayList<GoLPrefab> recent, ActionListener al){
        mainView.updateRecentFiguresMenu(recent,al);
    }
}
