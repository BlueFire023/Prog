package second;

import first.GameBoard;
import first.Janus;
import first.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class MaXxController extends JFrame implements ActionListener {
    private JFileChooser fileChooser = new JFileChooser();
    JTextField boardSizeTextField = new JTextField("8");
    public int playerCount = 2;
    JTextField playerCountTextField = new JTextField(String.valueOf(playerCount));
    JPanel customizationPanel = new JPanel();
    JPanel[] customizationPanels;

    public MaXxController() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel menuBar = new JPanel();
        JLabel playerCountLabel = new JLabel("Player anzahl:");

        playerCountTextField.setPreferredSize(new Dimension(50, 20));
        JLabel boardSizeLabel = new JLabel("Spielbrett größe:");

        boardSizeTextField.setPreferredSize(new Dimension(50, 20));

        JButton configurePlayers = new JButton("Spieler einstellen");
        configurePlayers.addActionListener(this);
        menuBar.add(configurePlayers);

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
        showPlayerCustomizations();
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
        JButton source = (JButton) e.getSource();
        switch (e.getActionCommand()) {
            case "Laden" -> openSavedGame();
            case "Spieler einstellen" -> showPlayerCustomizations();
            case "Neues Spiel" -> readPlayers();
            default -> source.setBackground(JColorChooser.showDialog(this, "Select Color", Color.WHITE));
        }
    }

    public void readPlayers() {
        GameBoard board = new GameBoard(Integer.parseInt(boardSizeTextField.getText()));

        Player[] players = new Player[playerCount];
        for (int i = 0; i < playerCount; i++) {
            String name = ((JTextField) customizationPanels[i].getComponent(2)).getText();
            String symbol = ((JTextField) customizationPanels[i].getComponent(4)).getText();
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

    public void showPlayerCustomizations() {
        playerCount = Integer.parseInt(playerCountTextField.getText());
        customizationPanels = new JPanel[playerCount];
        customizationPanel.removeAll();
        customizationPanel.setPreferredSize(new Dimension(1000, playerCount * 100));
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
            playerColorButton.setBackground(Color.WHITE);
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
