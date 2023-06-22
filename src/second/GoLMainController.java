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
    public GoLMainWindow(){
        desktopPane.setDesktopManager(new DefaultDesktopManager());

        setContentPane(desktopPane);
        setSize(new Dimension(500,500));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        newWindow.addActionListener(e -> {
            JInternalFrame internalFrame = new GoLController().view.getFrame();
            desktopPane.add(internalFrame);
            internalFrame.setVisible(true);
        });
        menu.add(newWindow);
        menuBar.add(menu);
        setJMenuBar(menuBar);
        setVisible(true);
    }

    public static void main(String[] args) {
        new GoLMainWindow();
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
        instances.removeIf(g -> e.getSource().equals(g.view.getFrame()));
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
