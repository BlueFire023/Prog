package second;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 06/04/2023
 */

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class SliderTemp extends JFrame implements ChangeListener
{
    JSlider sliderCelcius, sliderFahrenheit;
    JTextField textCelsius, textFahrenheit;

    public SliderTemp()
    {
        setTitle(" Konvertiere Temperaturen! ");
        setDefaultCloseOperation( EXIT_ON_CLOSE );

        sliderCelcius = new JSlider(SwingConstants.VERTICAL, -273, 100, 0);
        sliderCelcius.setMajorTickSpacing(16);
        sliderCelcius.setMinorTickSpacing(8);
        sliderCelcius.setPaintTicks(true);
        sliderCelcius.setPaintLabels(true);
        sliderCelcius.setPreferredSize(new Dimension(300,500));
        sliderCelcius.addChangeListener(this);
        sliderCelcius.setName("Celsius-Skala");
        sliderCelcius.setBorder(BorderFactory.createTitledBorder("Celsius"));
        textCelsius = new JTextField(4);
        textCelsius.setText(sliderCelcius.getValue() + "");
        textCelsius.setToolTipText("Temperatur in Celsius");

        sliderFahrenheit = new JSlider(SwingConstants.VERTICAL, -459, 212, 32);
        sliderFahrenheit.setMajorTickSpacing(28);
        sliderFahrenheit.setMinorTickSpacing(14);
        sliderFahrenheit.setPaintTicks(true);
        sliderFahrenheit.setPaintLabels(true);
        sliderFahrenheit.setPreferredSize(new Dimension(300,500));
        sliderFahrenheit.addChangeListener(this);
        sliderFahrenheit.setName("Fahrenheit-Skala");
        sliderFahrenheit.setBorder(BorderFactory.createTitledBorder("Fahrenheit"));
        textFahrenheit = new JTextField(4);
        textFahrenheit.setText(sliderFahrenheit.getValue() + "");
        textFahrenheit.setToolTipText("Temperatur in Fahrenheit");

        getContentPane().setLayout(new FlowLayout());
        getContentPane().add(textCelsius);
        getContentPane().add(sliderCelcius);
        getContentPane().add(textFahrenheit);
        getContentPane().add(sliderFahrenheit);
        textCelsius.setEditable(false);
        textFahrenheit.setEditable(false);
    }

    public static void main(String[] args)
    {
        SliderTemp tempRechner = new SliderTemp();
        tempRechner.setSize(800, 600);
        tempRechner.setVisible(true);
    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
        JSlider quelle;
        quelle = (JSlider)e.getSource();
        if (quelle.getName().equals("Celsius-Skala"))
        {
            textCelsius.setText(quelle.getValue() + "");
            textFahrenheit.setText(String.valueOf(Integer.valueOf((int) (( quelle.getValue() * 1.8) + 32))));
        }
        if (quelle.getName().equals("Fahrenheit-Skala"))
        {
            textFahrenheit.setText(quelle.getValue() + "");
            textCelsius.setText(String.valueOf((( quelle.getValue()-32 ) * 5 ) / 9));
        }
    }
}
