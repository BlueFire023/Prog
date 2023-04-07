package second;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 07/04/2023
 */
public class SliderTemp extends JFrame implements ChangeListener {
    JSlider sliderCelsius, sliderFahrenheit;
    JTextField textCelsius, textFahrenheit;
    JPanel resultPanel;

    public SliderTemp() {
        setTitle("SliderTemp");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        sliderCelsius = new JSlider(SwingConstants.VERTICAL, -273, 100, 0);
        sliderCelsius.setMajorTickSpacing(100);
        sliderCelsius.setMinorTickSpacing(50);
        sliderCelsius.setPaintTicks(true);
        sliderCelsius.setPaintLabels(true);
        sliderCelsius.setPreferredSize(new Dimension(175, 300));
        sliderCelsius.setBorder(BorderFactory.createTitledBorder("Celsius"));
        sliderCelsius.addChangeListener(this);
        sliderCelsius.setName("Celsius");
        sliderCelsius.setToolTipText("Celsius einstellen");
        getContentPane().add(sliderCelsius, BorderLayout.WEST);

        sliderFahrenheit = new JSlider(SwingConstants.VERTICAL, -459, 212, 32);
        sliderFahrenheit.setMajorTickSpacing(100);
        sliderFahrenheit.setMinorTickSpacing(50);
        sliderFahrenheit.setPaintTicks(true);
        sliderFahrenheit.setPaintLabels(true);
        sliderFahrenheit.setPreferredSize(new Dimension(175, 300));
        sliderFahrenheit.setBorder(BorderFactory.createTitledBorder("Fahrenheit"));
        sliderFahrenheit.addChangeListener(this);
        sliderFahrenheit.setName("Fahrenheit");
        sliderFahrenheit.setToolTipText("Fahrenheit einstellen");
        getContentPane().add(sliderFahrenheit, BorderLayout.EAST);

        textCelsius = new JTextField();
        textCelsius.setText("" + sliderCelsius.getValue());
        textCelsius.setEditable(false);

        textFahrenheit = new JTextField();
        textFahrenheit.setText("" + sliderFahrenheit.getValue());
        textFahrenheit.setEditable(false);

        resultPanel = new JPanel(new GridLayout());
        resultPanel.add(new JLabel(" Celsius:"));
        resultPanel.add(textCelsius);
        resultPanel.add(new JLabel(" Fahrenheit:"));
        resultPanel.add(textFahrenheit);
        getContentPane().add(resultPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SliderTemp sliderTemp = new SliderTemp();
        sliderTemp.setSize(362, 500);
        sliderTemp.setResizable(false);
        sliderTemp.setVisible(true);
    }

    @Override
    public void stateChanged(ChangeEvent evt) {
        JSlider source;
        source = (JSlider) evt.getSource();
        if (source.getValueIsAdjusting() && source.getName().equals("Celsius")) {
            textCelsius.setText("" + sliderCelsius.getValue());
            int fahrenheit = (int) ((sliderCelsius.getValue() * 1.8) + 32);
            textFahrenheit.setText(String.format("%.02f", ((sliderCelsius.getValue() * 1.8) + 32)));
            sliderFahrenheit.setValue(fahrenheit);
        }
        if (source.getValueIsAdjusting() && source.getName().equals("Fahrenheit")) {
            textFahrenheit.setText("" + sliderFahrenheit.getValue());
            int celsius = (int) ((sliderFahrenheit.getValue() - 32) / 1.8);
            textCelsius.setText(String.format("%.02f", ((sliderFahrenheit.getValue() - 32) / 1.8)));
            sliderCelsius.setValue(celsius);
        }
    }
}
