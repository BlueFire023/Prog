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

public class MaXxGuI extends JFrame implements ActionListener, KeyListener {
    private JPanel gamePanel = new JPanel();

    private final GameBoard board;
    private final int PLAYERCOUNT = 2;
    private final int BOARDSIZE = 8;
    private final double SCORESTOWIN = 53d;
    private final JPanel[][] fractionPanels;
    private final JLabel[][] fractionLabels;
    private JLabel instructionLabel = new JLabel();
    private int currentPlayerIndex;
    private static int openWindows;
    private String direction;
    private final Player[] players;
    private final JButton newGameButton = new JButton("Start new Game");
    private final JPanel scoreBoard = new JPanel();

    public MaXxGuI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("MaXxGuI");
        setLayout(new BorderLayout());
        board = new GameBoard(BOARDSIZE);
        players = new Player[PLAYERCOUNT];
        players[0] = new Player("Weiß", "W", new String[]{"N", "O", "S", "W", "NO"}, board, Color.WHITE);
        players[1] = new Player("Schwarz", "B", new String[]{"N", "O", "S", "W", "SW"}, board, Color.BLACK);
        initNewGame();

        fractionLabels = new JLabel[BOARDSIZE][BOARDSIZE];
        fractionPanels = new JPanel[BOARDSIZE][BOARDSIZE];

        gamePanel.setLayout(new GridLayout(BOARDSIZE, BOARDSIZE, 5, 5));
        gamePanel.setPreferredSize(new Dimension(800, 650));
        gamePanel.setBackground(Color.BLACK);
        //gamePanel
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
        updateBoard();
        add(gamePanel, BorderLayout.NORTH);

        JPanel controlsBar = new JPanel();
        controlsBar.setLayout(new GridLayout(1, 2));

        scoreBoard.setLayout(new GridLayout(PLAYERCOUNT, 2));
        for (Player player : players) {
            scoreBoard.add(new JLabel(player.getName() + ":"));
            scoreBoard.add(new JLabel());
        }
        updateScore();
        newGameButton.addActionListener(this);
        newGameButton.addKeyListener(this);
        newGameButton.setBackground(Color.WHITE);

        controlsBar.add(newGameButton);
        controlsBar.add(scoreBoard);
        add(controlsBar, BorderLayout.CENTER);

        JPanel instructionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        instructionPanel.setPreferredSize(new Dimension(800, 30));
        instructionPanel.add(instructionLabel);
        add(instructionPanel, BorderLayout.SOUTH);

        setSize(800, 800);
        setVisible(true);
        fractionPanels[players[currentPlayerIndex].getYPosition()][players[currentPlayerIndex].getXPosition()].setBorder(BorderFactory.createLineBorder(Color.RED, 5));
        openWindows++;
    }

    public static void main(String[] args) {
        new MaXxGuI();
    }

    private void initNewGame() {
        board.fillBoard();
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
            player.setScore(new Fraction(0, 1));
        }
        currentPlayerIndex = MyMath.rand(0, PLAYERCOUNT - 1);
        instructionLabel.setText(players[currentPlayerIndex].getName() + " ist dran!");
    }

    private void updateBoard() {
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
        fractionPanels[players[currentPlayerIndex].getYPosition()][players[currentPlayerIndex].getXPosition()].setBorder(BorderFactory.createLineBorder(Color.RED, 5));
    }

    private void updateScore() {
        int i = 0;
        for (Player player : players) {
            ((JLabel) scoreBoard.getComponent(2 * i++ + 1)).setText("<html><p align='center'><u>" + player.getScore().getNumerator().intValue()
                    + "</u><br>" + player.getScore().getDenominator().intValue() + "</p></html>");
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
        new MaXxGuI();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
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
            board.setValue(players[currentPlayerIndex].getXPosition(), players[currentPlayerIndex].getYPosition(), "0");
            players[currentPlayerIndex].move(direction);
        } catch (Exception ex) {
            if(ex.getClass().equals(ArrayIndexOutOfBoundsException.class)){
                instructionLabel.setText("Unmögliche Bewegung!");
            }else{
                instructionLabel.setText(ex.getMessage());
            }
            throw new RuntimeException(ex);
        }

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

    private Color invertColor(Color initalColor) {
        return new Color(255 - initalColor.getRed(), 255 - initalColor.getGreen(), 255 - initalColor.getBlue());
    }
}
