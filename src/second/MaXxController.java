package second;

import first.GameBoard;
import first.MyMath;
import first.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class MaXxController extends JFrame implements ActionListener {
    private final JFileChooser fileChooser = new JFileChooser();
    private final JTextField boardSizeTextField = new JTextField("8");
    private int playerCount = 2;
    private final JTextField playerCountTextField = new JTextField(String.valueOf(playerCount));
    private final JPanel customizationPanel = new JPanel();
    private JPanel[] customizationPanels;

    public MaXxController() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("MaXxGuI");
        setLayout(new BorderLayout());
        JPanel menuBar = new JPanel();
        JLabel playerCountLabel = new JLabel("Player anzahl:");

        playerCountTextField.setPreferredSize(new Dimension(50, 20));
        playerCountTextField.setActionCommand("updatePlayerCustomization");
        playerCountTextField.addActionListener(this);
        JLabel boardSizeLabel = new JLabel("Spielbrett größe:");

        boardSizeTextField.setPreferredSize(new Dimension(50, 20));

        JButton openSavedGame = new JButton("Laden");
        openSavedGame.addActionListener(this);

        JButton newGameButton = new JButton("Neues Spiel");
        newGameButton.addActionListener(this);
        menuBar.add(playerCountLabel);
        menuBar.add(playerCountTextField);
        menuBar.add(boardSizeLabel);
        menuBar.add(boardSizeTextField);
        menuBar.add(newGameButton);
        menuBar.add(openSavedGame);
        add(menuBar, BorderLayout.NORTH);
        setSize(new Dimension(1000, 1000));
        setVisible(true);
        updatePlayerCustomizations();
    }

    public static void main(String[] args) {
        new MaXxController();
    }

    private void openSavedGame() {
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            try {
                FileInputStream fs = new FileInputStream(filePath);
                ObjectInputStream os = new ObjectInputStream(fs);
                MaXxGuI m = (MaXxGuI) os.readObject();
                m.setVisible(true);
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Laden" -> openSavedGame();
            case "Neues Spiel" -> openNewGame();
            default -> {
                Color currentColor = ((JButton) e.getSource()).getBackground();
                Color newColor = JColorChooser.showDialog(this, "Wähle eine Farbe aus", currentColor);
                if (newColor == null || newColor.getRGB() == -1118482) {
                    newColor = currentColor;
                }
                ((JButton) e.getSource()).setBackground(newColor);
            }
        }
    }

    public void openNewGame() {
        GameBoard board = new GameBoard(Integer.parseInt(boardSizeTextField.getText()));

        Player[] players = new Player[playerCount];
        for (int i = 0; i < playerCount; i++) {
            String name = ((JTextField) customizationPanels[i].getComponent(2)).getText();
            if (name.equals("")) {
                name = "Spieler " + (i + 1);
            }
            String symbol = ((JTextField) customizationPanels[i].getComponent(4)).getText();
            if (symbol.equals("")) {
                symbol = "P" + (i + 1);
            }
            Color color = customizationPanels[i].getComponent(7).getBackground();
            ArrayList<String> moveSetList = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                JPanel currPanel = (JPanel) customizationPanels[i].getComponent(5);
                j = j == 4 ? 5 : j;
                if (((JCheckBox) currPanel.getComponent(j)).isSelected()) {
                    moveSetList.add(((JCheckBox) currPanel.getComponent(j)).getText());
                }
            }
            String[] moveSet = moveSetList.toArray(new String[0]);
            players[i] = new Player(name, symbol, moveSet, board, color);
        }
        new MaXxGuI(board, players);

    }

    public void updatePlayerCustomizations() {
        playerCount = Integer.parseInt(playerCountTextField.getText());
        customizationPanels = new JPanel[playerCount];
        customizationPanel.removeAll();
        customizationPanel.setLayout(new GridLayout(playerCount, 1));
        for (int i = 0; i < playerCount; i++) {
            customizationPanels[i] = new JPanel();
            JLabel playerNumber = new JLabel((i + 1) + ".");
            customizationPanels[i].add(playerNumber);
            JLabel playerNameLabel = new JLabel("Name:");
            customizationPanels[i].add(playerNameLabel);
            JTextField playerNameTextField = new JTextField();
            playerNameTextField.setPreferredSize(new Dimension(50, 20));
            customizationPanels[i].add(playerNameTextField);
            JLabel playerSymbol = new JLabel("Symbol:");
            customizationPanels[i].add(playerSymbol);
            JTextField playerSymbolTextField = new JTextField();
            playerSymbolTextField.setPreferredSize(new Dimension(50, 20));
            customizationPanels[i].add(playerSymbolTextField);
            JPanel moveSetPanel = new JPanel();
            moveSetPanel.setLayout(new GridLayout(3, 3));
            JCheckBox[] checkBoxes = {
                    new JCheckBox("NW"),
                    new JCheckBox("N"),
                    new JCheckBox("NO"),
                    new JCheckBox("W"),
                    new JCheckBox("O"),
                    new JCheckBox("SW"),
                    new JCheckBox("S"),
                    new JCheckBox("SO")
            };
            for (int j = 0; j < 8; j++) {
                if (j == 4) {
                    moveSetPanel.add(new JPanel());
                }
                checkBoxes[j].setSelected(true);
                moveSetPanel.add(checkBoxes[j]);
            }
            customizationPanels[i].add(moveSetPanel);
            JLabel playerColorLabel = new JLabel("Farbe:");
            customizationPanels[i].add(playerColorLabel);
            JButton playerColorButton = new JButton();
            playerColorButton.setActionCommand(String.valueOf(i));
            playerColorButton.setBackground(new Color(MyMath.rand(0, 255), MyMath.rand(0, 255), MyMath.rand(0, 255)));
            playerColorButton.setPreferredSize(new Dimension(50, 50));
            playerColorButton.addActionListener(this);
            customizationPanels[i].add(playerColorButton);
        }
        for (JPanel p : customizationPanels) {
            customizationPanel.add(p);
        }
        add(customizationPanel, BorderLayout.CENTER);
        validate();
    }
}
