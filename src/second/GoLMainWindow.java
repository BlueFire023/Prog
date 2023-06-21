package second;

import javax.swing.*;
import java.awt.*;

public class GoLMainWindow extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();

    public GoLMainWindow(){
        desktopPane.setDesktopManager(new DefaultDesktopManager());
        setContentPane(desktopPane);
        setSize(new Dimension(500,500));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        desktopPane.add(new GoLController().view.getFrame());
        setVisible(true);
    }

    public static void main(String[] args) {
        new GoLMainWindow();
    }
}
