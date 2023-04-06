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
    Label titel = new Label("Konvertiere Temperaturen!");
    Label fahrLabel = new Label("Fahrenheit");
    Label celsLabel = new Label("Celsius");

    JSlider sliderCelcius, sliderFahrenheit;
    JTextField textCelsius, textFahrenheit;

    int fahrTemp;
    int celsTemp;

    public SliderTemp()
    {
        setTitle(" SliderTemp ");
        setDefaultCloseOperation( EXIT_ON_CLOSE );

        sliderCelcius = new JSlider(SwingConstants.HORIZONTAL, -273, 100, 0);
        sliderCelcius.setMajorTickSpacing(100);
        sliderCelcius.setMinorTickSpacing(50);
        sliderCelcius.setPaintTicks(true);
        sliderCelcius.setPaintLabels(true);
        sliderCelcius.setPreferredSize(new Dimension(300,500));
        sliderCelcius.addChangeListener(this);
        sliderCelcius.setName("Celsius-Skala");
        textCelsius = new JTextField(4);
        textCelsius.setText(sliderCelcius.getValue() + "");
        textCelsius.setToolTipText("Temperatur in Celsius");

        sliderFahrenheit = new JSlider(SwingConstants.HORIZONTAL, -459, 212, 32);
        sliderFahrenheit.setMajorTickSpacing(100);
        sliderFahrenheit.setMinorTickSpacing(50);
        sliderFahrenheit.setPaintTicks(true);
        sliderFahrenheit.setPaintLabels(true);
        sliderFahrenheit.setPreferredSize(new Dimension(300,500));
        sliderFahrenheit.addChangeListener(this);
        sliderFahrenheit.setName("Fahrenheit-Skala");
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
        tempRechner.setSize(400,150);
        tempRechner.setVisible(true);
    }

    public void rechner()
    {
        celsTemp = (( fahrTemp-32 ) * 5 ) / 9;
    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
        JSlider quelle;
        quelle = (JSlider)e.getSource();
        if (!quelle.getValueIsAdjusting() && quelle.getName().equals("Celsius-Skala"))
        {
            textCelsius.setText(quelle.getValue() + "");
        }
        if (quelle.getName().equals("Fahrenheit-Skala"))
        {
            textFahrenheit.setText(quelle.getValue() + "");
        }
    }
}
