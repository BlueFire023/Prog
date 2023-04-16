package second;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 16/04/2023
 */

import javax.swing.*;
import java.awt.*;
import first.GameBoard;

public class MaXxGuI extends JFrame {

private GameBoard b;
    public MaXxGuI(int size) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        b = new GameBoard(size);
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(size, size, 5,5));
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                gamePanel.add(newFractionPanel( b.getValue(i,j).getNumerator()., j * 100));
            }
        }
        getContentPane().add(gamePanel);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new MaXxGuI(8);
    }

    public JPanel newFractionPanel(int numerator, int denominator) {
        JPanel fractionPanel = new JPanel();
        JTextPane textNumerator = new JTextPane();
        JTextArea textDenominator = new JTextArea();
        JPanel fractionLine = new JPanel();
        fractionPanel.setLayout(new BorderLayout());

        fractionLine.setBackground(Color.BLACK);
        fractionLine.setPreferredSize(new Dimension(30, 1));

        textNumerator.setText("" + numerator);
        textNumerator.setPreferredSize(new Dimension(1, 20));

        textDenominator.setText("" + denominator);
        textDenominator.setPreferredSize(new Dimension(1, 20));

        fractionPanel.add(textNumerator, BorderLayout.NORTH);
        fractionPanel.add(fractionLine, BorderLayout.CENTER);
        fractionPanel.add(textDenominator, BorderLayout.SOUTH);
        return fractionPanel;
    }
}
