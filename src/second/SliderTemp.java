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
    public SliderTemp(){
        setTitle("SliderTemp");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new FlowLayout());
        sliderCelsius = new JSlider(SwingConstants.VERTICAL, -273, 537, 0);
        sliderCelsius.setMajorTickSpacing(100);
        sliderCelsius.setMinorTickSpacing(50);
        sliderCelsius.setPaintTicks(true);
        sliderCelsius.setPaintLabels(true);
        sliderCelsius.setPreferredSize(new Dimension(100, 300));
        sliderCelsius.addChangeListener(this);
        sliderCelsius.setName("Celsius");
        sliderCelsius.setToolTipText("Celsius einstellen");
        getContentPane().add(sliderCelsius);

        sliderFahrenheit = new JSlider(SwingConstants.VERTICAL, -459, 1000, 32);
        sliderFahrenheit.setMajorTickSpacing(100);
        sliderFahrenheit.setMinorTickSpacing(50);
        sliderFahrenheit.setPaintTicks(true);
        sliderFahrenheit.setPaintLabels(true);
        sliderFahrenheit.setPreferredSize(new Dimension(100, 300));
        sliderFahrenheit.addChangeListener(this);
        sliderFahrenheit.setName("Fahrenheit");
        sliderFahrenheit.setToolTipText("Fahrenheit einstellen");
        getContentPane().add(sliderFahrenheit);

        textCelsius = new JTextField(3);
        textCelsius.setText("" + sliderCelsius.getValue());
        textCelsius.setEditable(false);
        getContentPane().add(textCelsius);

        textFahrenheit = new JTextField(3);
        textFahrenheit.setText("" + sliderFahrenheit.getValue());
        textFahrenheit.setEditable(false);
        getContentPane().add(textFahrenheit);
    }
    public static void main(String[] args) {
        SliderTemp sliderTemp = new SliderTemp();
        sliderTemp.setSize(500, 500);
        sliderTemp.setResizable(false);
        sliderTemp.setVisible(true);
    }

    @Override
    public void stateChanged(ChangeEvent evt) {
        JSlider source;
        source = (JSlider) evt.getSource();
        if(source.getValueIsAdjusting() && source.getName().equals("Celsius")){
            textCelsius.setText("" + sliderCelsius.getValue());
            String fahrenheit = "" + (sliderCelsius.getValue() * 1.8) + 32 + "00";
            textFahrenheit.setText(fahrenheit);
            sliderFahrenheit.setValue((int)Double.parseDouble(fahrenheit));
        }
        if(source.getValueIsAdjusting() && source.getName().equals("Fahrenheit")){
            textFahrenheit.setText("" + sliderFahrenheit.getValue());
            String celsius = "" + (sliderFahrenheit.getValue() - 32) / 1.8 + "00";
            textCelsius.setText(celsius);
            sliderCelsius.setValue((int)Double.parseDouble(celsius));
        }
    }
}
