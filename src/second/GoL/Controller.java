package second.GoL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.*;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 21/06/2023
 */

public class Controller extends Adapter {
    private final Model model = new Model();
    private final MainModel mainModel;
    private final MainController mainController;
    private final Set<Point> lastCells = Collections.synchronizedSet(new HashSet<>());
    private final JFileChooser fileChooser = new JFileChooser();
    private final View view;
    private boolean painting, mouseHeld, placingFigure, mouseInFrame;
    private Point prevPos = new Point(), mousePos = new Point();
    private final Object lock = new Object();

    /**
     * Konstruktor der Controller Klasse. Wird für jedes neue JInternalFrame aufgerufen.
     *
     * @param mainController MainController
     * @param mainModel MainModel
     */
    public Controller(MainController mainController, MainModel mainModel) {
        this.mainModel = mainModel;
        this.mainController = mainController;
        view = new View(model);
        view.setListeners(this, this, this, this, this, this);
        view.updateCurrentMode();
        refreshCanvas();
    }

    /**
     * Berechnet die Daten, um die nächste Generation darzustellen
     */
    public synchronized void calculateNextGeneration() {
        synchronized (lock) {
            Map<Point, Boolean> cellsToUpdate = new Hashtable<>();
            Set<Point> deadCellsToCheck = new HashSet<>();
            for (Point p : Collections.synchronizedSet(new HashSet<>(model.getAliveCells()))) {
                int aliveCellsCount = 0;
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (i == 0 && j == 0) {
                            continue;
                        }
                        Point newPos = new Point(p.x + i, p.y + j);
                        if (model.isCellAlive(calculateWrap(newPos))) {
                            aliveCellsCount++;
                        } else {
                            deadCellsToCheck.add(newPos);
                        }
                    }
                }
                if (aliveCellsCount < 2 || aliveCellsCount > 3) {
                    cellsToUpdate.put(p, false);
                }
            }
            for (Point p : deadCellsToCheck) {
                int aliveCellsCount = 0;
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (i == 0 && j == 0) {
                            continue;
                        }
                        Point newPos = new Point(p.x + i, p.y + j);
                        if (model.isCellAlive(calculateWrap(newPos))) {
                            aliveCellsCount++;
                        }
                        if (aliveCellsCount > 3) {
                            break;
                        }
                    }
                    if (aliveCellsCount > 3) {
                        break;
                    }
                }
                if (aliveCellsCount == 3) {
                    cellsToUpdate.put(p, true);
                }
            }
            for (Map.Entry<Point, Boolean> entry : cellsToUpdate.entrySet()) {
                model.setCell(calculateWrap(entry.getKey()), entry.getValue());
            }
            if (mouseInFrame) {
                showPreview();
            } else {
                refreshCanvas();
            }
        }
    }

    /**
     * Berechnet über den Bresenham Algorithmus die nötigen Daten, um eine Linie zu zeichnen
     *
     * @param curr aktuelle Position der Maus als Point
     * @param preview ob es nur als Vorschau funktioniert
     */
    private void drawLineBresenham(Point curr, Boolean preview) {
        Point prev = new Point(prevPos.x, prevPos.y);
        int dx = Math.abs(curr.x - prev.x), dy = Math.abs(curr.y - prev.y);
        int sx = prev.x < curr.x ? 1 : -1, sy = prev.y < curr.y ? 1 : -1;
        int err = dx - dy, e2;
        while (true) {
            paintPixel(prev, preview);
            if (prev.x == curr.x && prev.y == curr.y) {
                break;
            }
            e2 = err * 2;
            if (e2 > -dy) {
                err -= dy;
                prev.x += sx;
            }
            if (e2 < dx) {
                err += dx;
                prev.y += sy;
            }
        }
    }

    /**
     * Führt die Berechnungen durch, um ein Pixel zu setzen
     *
     * @param p der Point an dem gezeichnet wird
     * @param preview ob es nur als Vorschau funktioniert
     */
    private void paintPixel(Point p, boolean preview) {
        int brushSize = mainModel.getBrushSize();
        for (int i = 0; i < brushSize; i++) {
            for (int j = 0; j < brushSize; j++) {
                if (((int) (brushSize / 2.5d)) + p.x - i >= 0 && ((int) (brushSize / 2.5d)) + p.y - j >= 0 && ((int) (brushSize / 2.5d)) + p.x - i < model.getCanvasWidth() && ((int) (brushSize / 2.5d)) + p.y - j < model.getCanvasHeight()) {
                    Point point = new Point(((int) (brushSize / 2.5d) + p.x - i), ((int) (brushSize / 2.5d) + p.y - j));
                    if (preview) {
                        model.setCanvasRGB(point, model.getInvertedColor());
                        lastCells.add(point);
                    } else {
                        model.setCell(point, painting);
                    }
                }
            }
        }
    }

    /**
     * Torus Welt berechnung.
     *
     * @param pos Point
     * @return der gewrappte Punkt
     */
    public Point calculateWrap(Point pos) {
        return new Point(Math.floorMod(pos.x, model.getCanvasWidth()), Math.floorMod(pos.y, model.getCanvasHeight()));
    }

    /**
     * Der Canvas bereich wird "leergeräumt".
     */
    public void clearCanvas() {
        model.clearAliveCells();
        refreshCanvas();
        model.setActiveMode(model.isActiveMode(Model.Mode.RUNNING) ? model.getLastMode() : model.getActiveMode());
        mainController.updateAllRunButton();
    }

    /**
     * Der Canvas wird aktualisiert, um eventuelle Änderungen anzuzeigen
     */
    private void refreshCanvas() {
        for (int i = 0; i < model.getCanvasWidth(); i++) {
            for (int j = 0; j < model.getCanvasHeight(); j++) {
                model.setCell(new Point(i, j), model.isCellAlive(new Point(i, j)));
            }
        }
    }

    /**
     * Speichert den aktuellen Canvas als eine "Figur"
     */
    private void saveFigure() {
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();
            Prefab figureToSave = new Prefab(selectedFile.getName(), normalizePosition(model.getAliveCells()));
            try {
                if (model.getAliveCells().isEmpty()) {
                    throw new Exception("Leeres Objekt");
                }
                FileOutputStream fileOut = new FileOutputStream(filePath);
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                objectOut.writeObject(figureToSave);
                mainModel.setCurrentFigure(figureToSave);
                objectOut.close();
                fileOut.close();
                mainController.calculateCenter();
                refreshCanvas();
                placingFigure = true;
                mainController.updateRecentFiguresMenu(mainModel.getRecentFigures());
                mainController.updateAllRunButton();
                JOptionPane.showMessageDialog(null, "Das Objekt wurde erfolgreich gespeichert.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Fehler beim Schreiben des Objekts: " + e.getMessage());
            }
        }
    }

    /**
     * Zeigt Vorschau der Figur.
     */
    private void showPreview() {
        if (placingFigure) {
            for (Point p : mainModel.getCurrentFigure().cells()) {
                Point calculatedPoint = new Point(p.x + mousePos.x - mainModel.getCenter().x, p.y + mousePos.y - mainModel.getCenter().y);
                model.setCanvasRGB(calculateWrap(calculatedPoint), model.getInvertedColor());
                lastCells.add(calculateWrap(calculatedPoint));
            }
        } else if (model.isActiveMode(Model.Mode.LINE) && mouseHeld) {
            drawLineBresenham(mousePos, true);
        } else {
            paintPixel(mousePos, true);
        }
    }

    /**
     * Invertiert die Farbe.
     *
     * @param initialColor Farbe
     * @return negative Color
     */
    private Color invertColor(Color initialColor) {
        return new Color(255 - initialColor.getRed(), 255 - initialColor.getGreen(), 255 - initialColor.getBlue());
    }

    /**
     * Berechnet die Position der Maus.
     *
     * @param pos Maus Position als Point
     * @return projected MousePosition
     */
    private Point calculateMousePosition(Point pos) {
        double scaleX = (double) model.getCanvasWidth() / view.getWidth();
        double scaleY = (double) model.getCanvasHeight() / view.getHeight();
        int posOnCanvasX = (int) (pos.x * scaleX);
        int posOnCanvasY = (int) (pos.y * scaleY);
        return new Point(posOnCanvasX, posOnCanvasY);
    }

    /**
     * Lässt die Figuren Rotieren.
     *
     * @param direction Integer
     */
    private void rotate(int direction) {
        Set<Point> figure = mainModel.getCurrentFigure().cells();
        mainController.calculateCenter();
        Point center = mainModel.getCenter();

        Set<Point> rotatedFigure = new HashSet<>();
        for (Point p : figure) {
            Point relative = new Point(p.x - center.x, p.y - center.y);

            double radians = Math.toRadians(direction);
            int rotatedX = (int) Math.round(relative.x * Math.cos(radians) - relative.y * Math.sin(radians));
            int rotatedY = (int) Math.round(relative.x * Math.sin(radians) + relative.y * Math.cos(radians));

            rotatedFigure.add(new Point(rotatedX + center.x, rotatedY + center.y));
        }
        Prefab rotatedPrefab = new Prefab(mainModel.getCurrentFigure().name(), normalizePosition(rotatedFigure));
        mainModel.updateRecentFigures(rotatedPrefab);
        mainController.updateRecentFiguresMenu(mainModel.getRecentFigures());
        mainController.calculateCenter();
        showPreview();
    }

    /**
     * Spiegelt die Figur horizontal.
     *
     * @param horizontal Wie geflippt werden soll
     */
    private void flip(boolean horizontal) {
        Set<Point> mirroredFigure = new HashSet<>();
        for (Point p : mainModel.getCurrentFigure().cells()) {
            mirroredFigure.add(new Point(horizontal ? -p.x : p.x, horizontal ? p.y : -p.y));
        }
        Prefab mirroredPrefab = new Prefab(mainModel.getCurrentFigure().name(), normalizePosition(mirroredFigure));
        mainModel.updateRecentFigures(mirroredPrefab);
        mainController.updateRecentFiguresMenu(mainModel.getRecentFigures());
        mainController.calculateCenter();
        showPreview();
    }

    /**
     * Normalisiert die Position der Figur.
     *
     * @param figure Set
     * @return Figure Set
     */
    private Set<Point> normalizePosition(Set<Point> figure) {
        int lowestX = model.getCanvasWidth();
        int lowestY = model.getCanvasHeight();

        for (Point p : figure) {
            lowestX = Math.min(lowestX, p.x);
            lowestY = Math.min(lowestY, p.y);
        }
        Set<Point> adjustedFigure = new HashSet<>();

        for (Point p : figure) {
            Point adjustedPoint = new Point(p.x - lowestX, p.y - lowestY);
            adjustedFigure.add(adjustedPoint);
        }
        return adjustedFigure;
    }

    /**
     * Platziert die Figuren.
     *
     * @param placingFigure boolean
     */
    public void setPlacingFigure(boolean placingFigure) {
        this.placingFigure = placingFigure;
        view.updateCurrentMode();
    }

    /**
     * Ein neuer Thread wird gestartet und das Game of Life fängt an zu laufen
     */
    @SuppressWarnings("BusyWait")
    public void startRunning() {
        model.setLastMode(model.getActiveMode());
        model.setActiveMode(Model.Mode.RUNNING);
        Runnable runningTask = () -> {
            while (model.isActiveMode(Model.Mode.RUNNING)) {
                try {
                    calculateNextGeneration();
                    Thread.sleep(1000 / view.getSliderstat());
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
        Thread runningThread = new Thread(runningTask);
        runningThread.start();
        view.updateCurrentMode();
    }

    /**
     * Legt fest, für welchen Fall des ActionCommands was tun soll
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Löschen" -> clearCanvas();
            case "Auflösung" -> view.updateCanvasSize();
            case "Farben" -> view.updateCellColor(model.getAliveCellColor(), model.getDeadCellColor());
            case "size" -> {
                clearCanvas();
                model.setCanvas(new BufferedImage(view.getNewWidth(), view.getNewHeight(), BufferedImage.TYPE_INT_RGB));
                view.disposeSetSizeFrame();
                model.setActiveMode(model.isActiveMode(Model.Mode.RUNNING) ? model.getLastMode() : model.getActiveMode());
                mainController.updateAllRunButton();
            }
            case "acc" -> {
                Color newColor = JColorChooser.showDialog(view, "Wähle eine Farbe", model.getAliveCellColor());
                if (newColor == null) {
                    newColor = model.getAliveCellColor();
                }
                model.setAliveCellColor(newColor);
                ((JButton) e.getSource()).setBackground(newColor);
            }
            case "dcc" -> {
                Color newColor = JColorChooser.showDialog(view, "Wähle eine Farbe", model.getDeadCellColor());
                if (newColor == null) {
                    newColor = model.getDeadCellColor();
                }
                model.setDeadCellColor(newColor);
                model.setInvertedColor(invertColor(newColor));
                ((JButton) e.getSource()).setBackground(newColor);
            }
            case "Speichern" -> saveFigure();
            case "Laufen" -> {
                startRunning();
                mainController.updateAllRunButton();
            }
            case "Malen" -> {
                placingFigure = false;
                model.setActiveMode(Model.Mode.PAINTING);
                mainController.updateAllRunButton();
            }
            case "Setzen" -> {
                placingFigure = false;
                model.setActiveMode(Model.Mode.SET);
                mainController.updateAllRunButton();
            }
            case "Linien" -> {
                placingFigure = false;
                model.setActiveMode(Model.Mode.LINE);
                mainController.updateAllRunButton();
            }
            case "Kreuz" -> {
                lastCells.forEach(p -> model.setCell(p, false));
                prevPos = new Point(0, 0);
                drawLineBresenham(new Point(model.getCanvasWidth() - 1, model.getCanvasHeight() - 1), false);
                prevPos = new Point(model.getCanvasWidth() - 1, 0);
                drawLineBresenham(new Point(0, model.getCanvasHeight() - 1), false);
            }
            case "Rahmen" -> {
                prevPos = new Point(0, 0);
                drawLineBresenham(new Point(model.getCanvasWidth() - 1, 0), false);
                prevPos = new Point(0, 0);
                drawLineBresenham(new Point(0, model.getCanvasHeight() - 1), false);
                prevPos = new Point(model.getCanvasWidth() - 1, 0);
                drawLineBresenham(new Point(model.getCanvasWidth() - 1, model.getCanvasHeight() - 1), false);
                prevPos = new Point(0, model.getCanvasHeight() - 1);
                drawLineBresenham(new Point(model.getCanvasWidth() - 1, model.getCanvasHeight() - 1), false);
            }
            case "Plus" -> {
                lastCells.forEach(p -> model.setCell(p, false));
                prevPos = new Point(0, (model.getCanvasHeight() - 1) / 2);
                drawLineBresenham(new Point(model.getCanvasWidth(), (model.getCanvasHeight() - 1) / 2), false);
                prevPos = new Point((model.getCanvasWidth() - 1) / 2, 0);
                drawLineBresenham(new Point((model.getCanvasWidth() - 1) / 2, model.getCanvasHeight()), false);
            }
        }
        refreshCanvas();
        view.updateCurrentMode();
    }

    /**
     * Legt fest was passiert, wenn man einen gewissen Knopf auf der Tastatur drückt
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_C -> view.updateCellColor(model.getAliveCellColor(), model.getDeadCellColor());
            case KeyEvent.VK_S -> {
                startRunning();
                mainController.updateAllRunButton();
            }
            case KeyEvent.VK_L -> {
                placingFigure = false;
                model.setActiveMode(Model.Mode.LINE);
                mainController.updateAllRunButton();
            }
            case KeyEvent.VK_R -> clearCanvas();
            case KeyEvent.VK_A -> {
                placingFigure = false;
                model.setActiveMode(Model.Mode.PAINTING);
                view.updateCanvasSize();
                mainController.updateAllRunButton();
            }
            case KeyEvent.VK_D -> {
                placingFigure = false;
                model.setActiveMode(Model.Mode.PAINTING);
                mainController.updateAllRunButton();
            }
            case KeyEvent.VK_P -> {
                placingFigure = false;
                model.setActiveMode(Model.Mode.SET);
                mainController.updateAllRunButton();
            }
        }
        view.updateCurrentMode();
        if (!model.isActiveMode(Model.Mode.RUNNING) && e.getKeyCode() == KeyEvent.VK_SPACE) {
            calculateNextGeneration();
        } else if (placingFigure && (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN)) {
            flip(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT);
        } else {
            mainModel.setBrushSize(Character.isDigit(e.getKeyChar()) ? Character.getNumericValue(e.getKeyChar()) : mainModel.getBrushSize());
            showPreview();
        }
    }

    /**
     * Legt fest, dass wenn man sein Mausrad bewegt, dass dann das zu platzierende Objekt rotiert wird
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (placingFigure) {
            rotate(e.getWheelRotation() > 0 ? 90 : -90);
        }
    }

    /**
     * Stellt fest, ob die Maustaste gedrückt wurde und setzt ein je nach Modus ein Pixel oder eine figur
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        synchronized (lock) {
            prevPos = calculateMousePosition(e.getPoint());
            painting = e.getButton() == 1;
            mouseHeld = true;
            if (placingFigure) {
                mainModel.getCurrentFigure().cells().forEach(p -> model.setCell(calculateWrap(new Point(p.x + prevPos.x - mainModel.getCenter().x, p.y + prevPos.y - mainModel.getCenter().y)), painting));
            } else if (!model.isActiveMode(Model.Mode.LINE)) {
                paintPixel(mousePos, false);
            }
        }
    }

    /**
     * Aktualisiert den Canvas wenn man den Frame verlässt
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {
        mouseInFrame = false;
        refreshCanvas();
    }

    /**
     * verhindert das Laden von zu großen Figuren
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        mouseInFrame = true;
        if (mainModel.getRecentFigures().size() == 0) {
            return;
        }
        for (Point p : mainModel.getCurrentFigure().cells()) {
            if (p.x > model.getCanvasWidth() || p.y > model.getCanvasHeight()) {
                placingFigure = false;
                model.setActiveMode(model.isActiveMode(Model.Mode.RUNNING) ? model.getLastMode() : model.getActiveMode());
                mainController.updateAllRunButton();
            }
        }
    }

    /**
     * Legt fest, was passiert, wenn man seine Maus loslässt und die Funktionen, die dazu gehören sollen
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        mouseHeld = false;
        if (model.isActiveMode(Model.Mode.LINE) && !placingFigure) {
            mousePos = calculateMousePosition(e.getPoint());
            drawLineBresenham(mousePos, false);
        }
    }

    /**
     * Legt fest, was passiert, wenn man seine Maus zieht und die Funktionen, die dazu gehören sollen
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        removePreview();
        mousePos = calculateMousePosition(e.getPoint());
        if (model.isActiveMode(Model.Mode.PAINTING) && !placingFigure) {
            drawLineBresenham(mousePos, false);
            prevPos = mousePos;
        } else {
            mouseHeld = true;
            showPreview();
        }
    }

    /**
     * Legt fest, dass eine Vorschau von dem zu platzierenden Objekt/Pixel angezeigt wird, sobald man seine Maus bewegt
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        removePreview();
        mousePos = calculateMousePosition(e.getPoint());
        showPreview();
    }

    /**
     * Setzt aktuelle Fenster Nummer
     *
     * @param number Integer
     */

    public void setCurrentWindowNumber(int number) {
        model.setCurrentWindowNumber(number);
        view.setNewTitle();
    }

    /**
     * Stoppt das Laufen
     */
    public void stopRunning() {
        model.setActiveMode(model.isActiveMode(Model.Mode.RUNNING) ? model.getLastMode() : model.getActiveMode());
        view.updateCurrentMode();
    }

    /**
     * Gibt das View Objekt zurück
     *
     * @return view
     */
    public View getView() {
        return view;
    }

    /**
     * Gibt den aktuellen Modus zurück
     *
     * @return activeMode
     */
    public String getActiveMode() {
        return model.getActiveMode().toString();
    }

    /**
     * Entfernt das Preview aus dem Canvas
     */
    public void removePreview() {
        for (Point p : lastCells) {
            model.setCanvasRGB(p, model.isCellAlive(p) ? model.getAliveCellColor() : model.getDeadCellColor());
        }
        lastCells.clear();
    }
}