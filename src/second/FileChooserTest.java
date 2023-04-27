package second;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
public class FileChooserTest extends JFrame {
    JTextField // Textfelder fuer
            filename = new JTextField(), // Dateinamen
            dir = new JTextField(), // Directory
            exists = new JTextField(), // Existenz-Abfrage
            isdir = new JTextField(); // Directory-Abfrage
    JButton // Knoepfe fuer
            open = new JButton("Öffnen"), // Oeffnen
            save = new JButton("Speichern"); // Speichern
    JFileChooser c = new JFileChooser(new File("c://aabb")); // Start-Directory

    public FileChooserTest() { // Konstruktor
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("FileChooserTest"); // Fenster-Titel
        Container cp = getContentPane(); // Fenster-Container
        open.addActionListener(new OpenL()); // AL registrieren
        cp.add(open, BorderLayout.NORTH); // und einfuegen
        save.addActionListener(new SaveL()); // ...
        cp.add(save, BorderLayout.SOUTH); // ...
        dir.setEditable(false);
        filename.setEditable(false);
        exists.setEditable(false);
        isdir.setEditable(false);
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(4, 1, 2, 2)); // GridLayout fuer Textfelder-Panel
        p.add(filename); // Felder hinzufuegen
        p.add(dir); // ...
        p.add(exists); // ...
        p.add(isdir); // ...
        cp.add(p, BorderLayout.CENTER); // ...
        c.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); // Sel.-Modus
        setVisible(true);
    }

    class OpenL implements ActionListener { // AL fuer Oeffne-Knopf
        public void actionPerformed(ActionEvent e) {
            int rVal = c.showOpenDialog(FileChooserTest.this); // Oeffne-Dialog oeffnen
            if (rVal == JFileChooser.APPROVE_OPTION) { // falls bestaetigt:
                filename.setText("Filename: " + c.getSelectedFile().getName());
                dir.setText("Akt. Directory: " + c.getCurrentDirectory());
                exists.setText("Existiert? " + c.getSelectedFile().exists());
                isdir.setText("Ist Directory? " + c.getSelectedFile().isDirectory());
            }
            if (rVal == JFileChooser.CANCEL_OPTION) { // falls abgebrochen:
                filename.setText("Es wurde Abbrechen gedrückt");
                dir.setText("");
                exists.setText(""); // Texte loeschen
                isdir.setText(""); // ...
            }
        }
    }

    class SaveL implements ActionListener { // AL fuer Speichern-Knopf
        public void actionPerformed(ActionEvent e) {
            int rVal = c.showSaveDialog(FileChooserTest.this); // Speichern-Dialog oeffnen
            if (rVal == JFileChooser.APPROVE_OPTION) { // falls bestaetigt:
                filename.setText("Filename: " + c.getSelectedFile().getName());
                dir.setText("Akt. Directory: " + c.getCurrentDirectory());
                exists.setText("Existiert? " + c.getSelectedFile().exists());
                isdir.setText("Ist Directory? " + c.getSelectedFile().isDirectory());
            }
            if (rVal == JFileChooser.CANCEL_OPTION) { // falls abgebrochen:
                filename.setText("Es wurde Abbrechen gedrückt");
                dir.setText("");
                exists.setText("");
                isdir.setText("");
            }
        }
    }

    public static void main(String[] args) {
        new FileChooserTest();
    }
}

