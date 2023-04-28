package second;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 16/04/2023
 */

import first.GameBoard;
import first.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
public class MaXxGUI extends JFrame implements ActionListener {

    private final GameBoard board;

    public MaXxGUI(int size) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        board = new GameBoard(size);
        board.fillBoard();
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(size, size, 5, 5));
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                try {
                    gamePanel.add(newFractionLabel(board.getValue(i, j).getNumerator().intValue(),
                            board.getValue(i, j).getDenominator().intValue()));
                } catch (Exception ignored) {}
            }
        }
        add(gamePanel);

        JPanel controlsBar = new JPanel();
        JButton test  = new JButton("TEST");
        test.addActionListener(this);
        controlsBar.add(test);
        add(controlsBar, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new MaXxGuI(8);
    }

    public JPanel newFractionLabel(int numerator, int denominator) {
        JPanel p = new JPanel();
        JLabel textNumerator = new JLabel();
        textNumerator.setText("<html><p align='center'><u>" + numerator + "</u><br>" + denominator + "</p></html");
        p.add(textNumerator);
        return p;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new MaXxGuI(8);
    }
}
 */
