package second;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 20/04/2023
 */

import first.Fraction;
import first.GameBoard;
import first.MyMath;
import first.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

public class MaXxGuI extends JFrame implements ActionListener, KeyListener, Serializable {
    private JPanel gamePanel = new JPanel(); //Erstellung des Panels, in dem sich später die einzelnen Felder wiederfinden
    private final GameBoard board; //Spielfeld, welches im Hintergrund steht
    private final int PLAYERCOUNT = 2; //Anzahl der Mitspieler (hier 2)
    private final int BOARDSIZE = 8; //Größe des Boards (immer quadratisch; hier 8x8)
    private final double SCORESTOWIN = 53d; //Punktestand, der überschritten werden muss, um zu gewinnen
    private final JPanel[][] fractionPanels; //Panels für das Spielfeld (Array)
    private final JLabel[][] fractionLabels; //Labels für das Spielfeld (Array)
    private JLabel instructionLabel = new JLabel(); //Label für die Instruktionen unter dem Spielfeld
    private int currentPlayerIndex; //Index, der zeigt welcher Spieler am Zug ist
    private static int openWindows; //Anzahl geöffneter Fenster
    private String direction; //String, der Richtung, der bei der Tastatureingabe gesetzt wird
    private final Player[] players; //Array der Spieler
    private final JPanel scoreBoard = new JPanel(); //Panel für die Scores
    private JFileChooser fileChooser = new JFileChooser(); // Erstellung des Filechoosers

    public MaXxGuI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("MaXxGuI");
        setLayout(new BorderLayout());
        //Definierung des GameBoards
        board = new GameBoard(BOARDSIZE);
        // Erstellung des Spieler mit Namen, Abkürzungen, Moveset, Zuweisung zum Board und der Farbe
        players = new Player[PLAYERCOUNT];
        players[0] = new Player("Weiß", "W", new String[]{"N", "O", "S", "W", "NO"}, board, Color.WHITE);
        players[1] = new Player("Schwarz", "B", new String[]{"N", "O", "S", "W", "SW"}, board, Color.BLACK);
        initNewGame();//Methode zum Aufsetzen eines neuen Spiels

        //Definierung der Panel- und Labelarrays
        fractionLabels = new JLabel[BOARDSIZE][BOARDSIZE];
        fractionPanels = new JPanel[BOARDSIZE][BOARDSIZE];

        //Setzen des Layouts, Größe und Hintergrundfarbe des Gamepanels
        gamePanel.setLayout(new GridLayout(BOARDSIZE, BOARDSIZE, 5, 5));
        gamePanel.setPreferredSize(new Dimension(800, 650));
        gamePanel.setBackground(Color.BLACK);

        //Füllen des Panels mit einzelnen Feldern(bestehend aus Panels mit Labeln)
        for (int i = 0; i < BOARDSIZE; i++) {
            for (int j = 0; j < BOARDSIZE; j++) {
                fractionPanels[i][j] = new JPanel();
                fractionPanels[i][j].setLayout(new GridBagLayout());

                fractionLabels[i][j] = new JLabel();
                fractionLabels[i][j].setFont(new Font("Courier", Font.PLAIN, 15));

                fractionPanels[i][j].add(fractionLabels[i][j]);
                gamePanel.add(fractionPanels[i][j]);
            }
        }
        //Labels bekommen Aufdruck(Bruch bzw. Spieler-Symbol)
        updateBoard();
        //GamePanel wird dem Fenster hinzugefügt
        add(gamePanel, BorderLayout.NORTH);

        //Erstellung der ControlsBar, in der später Score und Buttons zu finden sind
        JPanel controlsBar = new JPanel();
        controlsBar.setLayout(new GridLayout(1, 2));

        //Definierung des Scoreboard Panels und dessen Füllung mit Labels für die Anzeige der Punkte
        scoreBoard.setLayout(new GridLayout(PLAYERCOUNT, 2));
        for (Player player : players) {
            scoreBoard.add(new JLabel(player.getName() + ":"));
            scoreBoard.add(new JLabel());
        }
        updateScore();

        //Definierung des ButtonPanels
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3));
        JButton newGameButton = new JButton("Start new Game");

        //Hinzufügen eines Buttons (zum Starten von neuen Spielen) zum ButtonPanel
        buttonPanel.add(newGameButton);
        newGameButton.addActionListener(this);
        newGameButton.addKeyListener(this);
        newGameButton.setBackground(Color.WHITE);

        //Hinzufügen eines Buttons (zum Öffnen von gespeicherten Spielen) zum ButtonPanel
        JButton openButton = new JButton("Open");
        buttonPanel.add(openButton);
        openButton.addActionListener(this);
        openButton.setBackground(Color.WHITE);

        //Hinzufügen eines Buttons (zum Speichern des Spielstandes) zum ButtonPanel
        JButton saveButton = new JButton("Save");
        buttonPanel.add(saveButton);
        saveButton.addActionListener(this);
        saveButton.setBackground(Color.WHITE);

        //Hinzufügen vom ButtonPanel zum ControlsBar Panel
        controlsBar.add(buttonPanel);
        controlsBar.add(scoreBoard);
        add(controlsBar, BorderLayout.CENTER);

        //Definition des InstructionPanels und das Hinzufügen zum Fenster (Immer am unteren Rand des Fensters)
        JPanel instructionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        instructionPanel.setPreferredSize(new Dimension(800, 30));
        instructionPanel.add(instructionLabel);
        add(instructionPanel, BorderLayout.SOUTH);

        //Setzen der Größe und Sichtbarkeit des Fensters
        setSize(800, 800);
        setVisible(true);

        openWindows++;
    }

    public static void main(String[] args) {
        new MaXxGuI(); //Beim Ausführen wird ein neues Objekt der Klasse erstellt bzw. es wird der Konstruktor aufgerufen
    }


    private void initNewGame() {
        board.fillBoard(); // Gameboard wird mit Werten gefüllt
        //Spieler werden zufällig auf das Spielfeld gesetzt
        boolean repeat;
        do {
            repeat = false;
            for (Player player : players) {
                player.initPosValue((MyMath.rand(0, board.getSize() - 1)), (MyMath.rand(0, board.getSize() - 1)));
            }
            for (int i = 0; i < PLAYERCOUNT - 1; i++) {
                if (players[i].getXPosition() == players[i + 1].getXPosition() && players[i].getYPosition() == players[i + 1].getYPosition()) {
                    repeat = true;
                    break;
                }
            }
        } while (repeat);
        for (Player player : players) {
            player.initPosition();
            player.setScore(new Fraction(0, 1)); //Setzen des Scores der Spieler auf 0
        }
        currentPlayerIndex = MyMath.rand(0, PLAYERCOUNT - 1); //Zufällige Auswahl des Spielers, der anfangen darf
        instructionLabel.setText(players[currentPlayerIndex].getName() + " ist dran!"); //Auf dem Fenster wird angezeigt, wer anfangen darf
    }

    private void updateBoard() {
        //Jedes Label des Spielfeldes im Fenster wird geupdated und der neuen Spielsituation angepasst (Player-Symbol wandert weiter, Felder werden Leer, nachdem ein Spieler auf ihnen war)
        for (int i = 0; i < BOARDSIZE; i++) {
            for (int j = 0; j < BOARDSIZE; j++) {
                JLabel currentlabel = fractionLabels[j][i];
                try {
                    for (Player player : players) {
                        if (player.getYPosition() == j && player.getXPosition() == i) {
                            currentlabel.setText("<html><p align ='center'><b>" + player.getSymbol() + "</b></p></html>");
                            break;
                        }
                    }
                    if (board.getValue(i, j).equals(Fraction.NaN)) {
                        currentlabel.setText("");
                    } else {
                        currentlabel.setText("<html><p align='center'><u>" + board.getValue(i, j).getNumerator().intValue()
                                + "</u><br>" + board.getValue(i, j).getDenominator().intValue() + "</p></html");
                    }
                } catch (Exception ignored) {
                }
                fractionPanels[i][j].setBorder(new EmptyBorder(0, 0, 0, 0));
                fractionPanels[i][j].setBackground(Color.LIGHT_GRAY);
                fractionLabels[i][j].setForeground(invertColor(fractionPanels[i][j].getBackground()));
            }
        }
        for (Player player : players) {
            fractionPanels[player.getYPosition()][player.getXPosition()].setBackground(player.getColor());
            fractionLabels[player.getYPosition()][player.getXPosition()].setForeground(invertColor(player.getColor()));
        }
        //Der Spieler der Anfängt wird mit rotem Rand gekennzeichnet
        fractionPanels[players[currentPlayerIndex].getYPosition()][players[currentPlayerIndex].getXPosition()].setBorder(BorderFactory.createLineBorder(Color.RED, 5));
    }

    private void updateScore() {
        //Score auf dem Scoreboard wird aktualisiert und mit dem neuen Ergebnis Angezeigt
        int i = 0;
        for (Player player : players) {
            ((JLabel) scoreBoard.getComponent(2 * i++ + 1)).setText("<html><p align='center'><u>" + player.getScore().getNumerator()
                    + "</u><br>" + player.getScore().getDenominator() + "</p></html>");
            //Sollte der Score eines Spielers über den zu erreichenden Score wandern, wird das Spiel beendet
            if (player.getScore().doubleValue() > SCORESTOWIN) {
                this.dispose();
                if (--openWindows <= 0) {
                    System.exit(0);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Start new Game" -> new MaXxGuI(); //Wird der Button "Start new Game" gedrückt, öffnet sich ein neues Spiel
            case "Open" -> openSavedGame();  // Wird der Button "Open" gedrückt, öffnet sich das Fenster zum Öffnen von Dateien
            case "Save" -> saveGame(); // Wird der Button "Save" gedrückt, öffnet sich das Fenster zum Speichern
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //Hier wird geprüft welche Taste gedrückt wird
        //Für die Tasten W,A,S,D,E,Y,Q und X wird die Richtung dementsprechend gesetzt, in die sich bewegt wird.
        //Der Rest der Tasten wird ignoriert
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> direction = "N";
            case KeyEvent.VK_A -> direction = "W";
            case KeyEvent.VK_S -> direction = "S";
            case KeyEvent.VK_D -> direction = "O";
            case KeyEvent.VK_E -> direction = "NO";
            case KeyEvent.VK_Y -> direction = "SW";
            case KeyEvent.VK_Q -> direction = "NW";
            case KeyEvent.VK_X -> direction = "SO";
        }
        try {
            board.setValue(players[currentPlayerIndex].getXPosition(), players[currentPlayerIndex].getYPosition(), "0"); //Der Wert des Feldes, auf dem der Spieler zuvor war, wird auf 0 gesetzt
            players[currentPlayerIndex].move(direction); //Der Spieler, der aktuell dran ist, bewegt sich in die Richtung, die durch die Tastatur angegeben wurde
        } catch (Exception ex) {
            //Catchen der Exceptions
            if (ex.getClass().equals(ArrayIndexOutOfBoundsException.class)) {
                instructionLabel.setText("Unmögliche Bewegung!");
            } else {
                instructionLabel.setText(ex.getMessage());
            }
            throw new RuntimeException(ex);
        }

        //Der Spieler wird gewechselt, der im Fokus steht
        currentPlayerIndex++;
        if (currentPlayerIndex == PLAYERCOUNT) {
            currentPlayerIndex = 0;
        }
        instructionLabel.setText(players[currentPlayerIndex].getName() + " ist dran!");
        updateBoard();
        updateScore();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    //Methode zum Farbeninvertieren, die genutzt wird, um das Symbol des Spielers invertiert zu seiner Farbe darstellen zu lassen
    private Color invertColor(Color initalColor) {
        return new Color(255 - initalColor.getRed(), 255 - initalColor.getGreen(), 255 - initalColor.getBlue());
    }

    //Methode zum Öffnen alter Games
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

    //Methode zum Speichern
    private void saveGame() {
        fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();
            try {
                FileOutputStream fileOut = new FileOutputStream(filePath);
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                objectOut.writeObject(this);
                objectOut.close();
                fileOut.close();
                JOptionPane.showMessageDialog(null, "Das Objekt wurde erfolgreich gespeichert.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Fehler beim Schreiben des Objekts.");
                e.printStackTrace();
            }
        }
    }
}
