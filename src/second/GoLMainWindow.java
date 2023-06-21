package second;

import javax.swing.*;
import java.awt.*;

public class GoLMainWindow extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();

    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu menu = new JMenu("Menu");
    private final JMenuItem newWindow = new JMenuItem("Neues Fenster");

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
}
