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
    private final JFileChooser fileChooser = new JFileChooser(); //FileChooser wird erstellt zum Sichern der Spiele
    //Textfeld zum Einstellen der Spielfeldgröße wird erstellt
    private final JTextField boardSizeTextField = new JTextField("8");
    private int playerCount = 2; //Standard Spielerzahl wird gesetzt
    //Textfeld zum Erstellen der Spieler wird gesetzt
    private final JTextField playerCountTextField = new JTextField(String.valueOf(playerCount));
    private final JPanel customizationPanel = new JPanel();
    private JPanel[] customizationPanels;
    private int estimatedBoardsize; //Variable zum Errechnen der optimalen Spielfeldgröße anhand der Spieleranzahl


    public MaXxController() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("MaXxGuI");
        setLayout(new BorderLayout());
        JPanel menuBar = new JPanel();
        JLabel playerCountLabel = new JLabel("Player anzahl:");

        // Initalisiert playerCountTextField mit Größe und ActionCommand
        playerCountTextField.setPreferredSize(new Dimension(50, 20));
        playerCountTextField.setActionCommand("updatePlayerCustomization");
        playerCountTextField.addActionListener(this);

        JLabel boardSizeLabel = new JLabel("Spielbrett größe:");
        boardSizeTextField.setPreferredSize(new Dimension(50, 20));

        // Initialisiert den Button mit dem man Spielstände Laden kann
        JButton openSavedGame = new JButton("Laden");
        openSavedGame.addActionListener(this);

        // Initialisiert den Button mit dem man ein frische Spiel starten kann
        JButton newGameButton = new JButton("Neues Spiel");
        newGameButton.addActionListener(this);

        // Initialisert den Button mit dem man sich die Steurung anzeigen lassen kann
        JButton showControlsButton = new JButton("Zeige Steuerung");
        showControlsButton.addActionListener(this);

        // Fügt all Objecte zur menuBar hinzu
        menuBar.add(playerCountLabel);
        menuBar.add(playerCountTextField);
        menuBar.add(boardSizeLabel);
        menuBar.add(boardSizeTextField);
        menuBar.add(newGameButton);
        menuBar.add(openSavedGame);
        menuBar.add(showControlsButton);
        add(menuBar, BorderLayout.NORTH);
        setSize(new Dimension(1000, 1000));
        setVisible(true);
        setResizable(false);
        // Zeigt direkt die zum ändern bereiten Spieler an
        updatePlayerCustomizations();
    }

    public static void main(String[] args) {
        new MaXxController();
    } //Neuer Controller wird erstellt

    //Methode zum Öffnen eines Spielstandes
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
            case "Laden" -> openSavedGame(); //Wird der Button "Laden" gedrückt, wird das Fenster zum Auswählen geöffnet
            case "Neues Spiel" -> openNewGame(); //Wird der Button "Neues Spiel" gedrückt, erstellt sich ein neues Spiel
            //Wird Enter bei einer Eingabe gedrückt, werden alle Daten aktualisiert
            case "updatePlayerCustomization" -> updatePlayerCustomizations();
            //Wird der Button "Zeige Steuerung" gedrückt, öffnet sich das Fenster mit der Steuerungserklärung
            case "Zeige Steuerung" -> showControlWindow();
            //Als Default wird das Farbpanel geöffnet, damit sind alle Eventualitäten abgedeckt
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


    //Hier wird ein neues Spiel geöffnet. Dabei werden alle Parameter gesetzt und das MaXxGuI aufgerufen
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
        //Setzen der Spieler durch Auslesen des Textfeldes
        playerCount = Integer.parseInt(playerCountTextField.getText());
        //Rechnung zur optimalen Spielfeldgröße
        estimatedBoardsize = (int)Math.sqrt((playerCount-1)*MaXxGuI.getSCORESTOWIN())+1;
        boardSizeTextField.setText(String.valueOf(estimatedBoardsize));
        // Fenster wird gefüllt mit den Spielern, die auch noch eingestellt werden können
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


    //Methode, die genutzt wird, um ein Fenster zu öffnen, das die möglichen Bewegungen anzeigt und durch welche Taste
    //diese Ausgelöst werden können
    private void showControlWindow(){
        JFrame controlsWindow = new JFrame();
        controlsWindow.setSize(new Dimension(200,200));
        JPanel moveSetPanel = new JPanel();
        moveSetPanel.setLayout(new GridLayout(3,3));
        JLabel[] controlsLabels = {
                new JLabel("Q = NW"),
                new JLabel("W = N"),
                new JLabel("E = NO"),
                new JLabel("A = W"),
                new JLabel("D = O"),
                new JLabel("Y = SW"),
                new JLabel("X = S"),
                new JLabel("C = SO"),
        };
        for (int j = 0; j < 8; j++) {
            if (j == 4) {
                moveSetPanel.add(new JPanel());
            }
            moveSetPanel.add(controlsLabels[j]);
        }
        controlsWindow.add(moveSetPanel);
        controlsWindow.setVisible(true);
    }
}
