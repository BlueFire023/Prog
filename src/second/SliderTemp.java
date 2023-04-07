package second;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

import first.WordLcm;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 07/04/2023
 */
public class SliderTemp extends JFrame implements ChangeListener {
    JSlider sliderCelsius, sliderFahrenheit; // JSliders für Celsius und Fahrenheit
    JTextField textCelsius, textFahrenheit; // Textfelder für die Anzeige der Werte
    JPanel resultPanel; // Panel für die Anzeige der Ergebnisse

    //Konstruktor für das Fenster und seine Komponenten.
    public SliderTemp() {
        setTitle("SliderTemp");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        // Erstellen des JSliders für Celsius
        sliderCelsius = new JSlider(SwingConstants.VERTICAL, -273, 105, 0);
        sliderCelsius.setMajorTickSpacing(21); // Intervall für die großen Ticks (damit auch 0 angezeigt werden kann)
        sliderCelsius.setPaintTicks(true); // Ticks anzeigen
        sliderCelsius.setPaintLabels(true); // Label für die Ticks anzeigen
        sliderCelsius.setPreferredSize(new Dimension(175, 300)); // Größe des JSliders
        sliderCelsius.setBorder(BorderFactory.createTitledBorder("Celsius")); // Rahmen setzen
        sliderCelsius.addChangeListener(this); // ChangeListener hinzufügen
        sliderCelsius.setName("Celsius"); // Namen setzen
        sliderCelsius.setToolTipText("Celsius einstellen"); // Tooltip setzen
        getContentPane().add(sliderCelsius, BorderLayout.WEST);

        // Erstellen des JSliders für Fahrenheit
        sliderFahrenheit = new JSlider(SwingConstants.VERTICAL, -459, 216, 32);
        sliderFahrenheit.setMajorTickSpacing(27); // Intervall für die großen Ticks (damit auch 0 angezeigt werden kann)
        sliderFahrenheit.setPaintTicks(true); // Ticks anzeigen
        sliderFahrenheit.setPaintLabels(true); // Label für die Ticks anzeigen
        sliderFahrenheit.setPreferredSize(new Dimension(175, 310)); // Größe des JSliders
        sliderFahrenheit.setBorder(BorderFactory.createTitledBorder("Fahrenheit")); // Rahmen setzen
        sliderFahrenheit.addChangeListener(this); // ChangeListener hinzufügen
        sliderFahrenheit.setName("Fahrenheit"); // Namen setzen
        sliderFahrenheit.setToolTipText("Fahrenheit einstellen"); // Tooltip setzen
        getContentPane().add(sliderFahrenheit, BorderLayout.EAST);

        // Erstellen der Textfelder für die Anzeige der Werte
        textCelsius = new JTextField();
        textCelsius.setText("" + sliderCelsius.getValue());
        textCelsius.setEditable(false);

        textFahrenheit = new JTextField();
        textFahrenheit.setText("" + sliderFahrenheit.getValue());
        textFahrenheit.setEditable(false);

        // Erstellen des Panels für die Anzeige der Ergebnisse
        resultPanel = new JPanel(new GridLayout());
        resultPanel.add(new JLabel(" Celsius:"));
        resultPanel.add(textCelsius);
        resultPanel.add(new JLabel(" Fahrenheit:"));
        resultPanel.add(textFahrenheit);
        getContentPane().add(resultPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SliderTemp sliderTemp = new SliderTemp();
        sliderTemp.setSize(362, 500);// Setze die Größe des Fensters auf 362 x 500 Pixel (Formatierung)
        sliderTemp.setResizable(false);
        sliderTemp.setVisible(true);
    }

    @Override
    public void stateChanged(ChangeEvent evt) {
        // Erstelle ein JSlider-Objekt, das das Event ausgelöst hat
        JSlider source = (JSlider) evt.getSource();
        // Wenn der Schieberegler Celsius ist und sich noch in der Veränderung befindet
        if (source.getValueIsAdjusting() && source.getName().equals("Celsius")) {
            textCelsius.setText("" + sliderCelsius.getValue());// Setze den Text des JLabels textCelsius auf den aktuellen Wert des Celsius-Schiebereglers
            int fahrenheit = (int) ((sliderCelsius.getValue() * 1.8) + 32);// Berechne den Wert in Fahrenheit und setze den Text des JLabels textFahrenheit auf den berechneten Wert
            textFahrenheit.setText(String.format("%.02f", ((sliderCelsius.getValue() * 1.8) + 32)));
            sliderFahrenheit.setValue(fahrenheit);// Setze den Wert des Fahrenheit-Schiebereglers auf den berechneten Wert
        }
        // Wenn der Schieberegler Fahrenheit ist und sich noch in der Veränderung befindet
        if (source.getValueIsAdjusting() && source.getName().equals("Fahrenheit")) {
            textFahrenheit.setText("" + sliderFahrenheit.getValue());// Setze den Text des JLabels textFahrenheit auf den aktuellen Wert des Fahrenheit-Schiebereglers
            int celsius = (int) ((sliderFahrenheit.getValue() - 32) / 1.8);// Berechne den Wert in Celsius und setze den Text des JLabels textCelsius auf den berechneten Wert
            textCelsius.setText(String.format("%.02f", ((sliderFahrenheit.getValue() - 32) / 1.8)));
            sliderCelsius.setValue(celsius);// Setze den Wert des Celsius-Schiebereglers auf den berechneten Wert
        }
    }
}