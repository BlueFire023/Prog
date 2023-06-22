package second;

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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 21/06/2023
 */

public class GoLController extends GoLAdapter {
    private final GoLModel model = new GoLModel();
    private final GoLMainModel mainModel;
    private final GoLMainController mainController;
    private final Set<Point> lastCells = new HashSet<>();
    private final JFileChooser fileChooser = new JFileChooser();
    private final Object lock = new Object();
    private boolean painting, mouseHeld, placingFigure;
    private Point prevPos = new Point(), mousePos = new Point();
    private Mode activeMode = Mode.PAINTING;
    public final GoLView view;

    /**
     * Konstruktor der Controller Klasse. Wird für jedes neue JInternalFrame aufgerufen.
     *
     * @param mainController
     * @param mainModel
     */
    public GoLController(GoLMainController mainController, GoLMainModel mainModel) {
        this.mainModel = mainModel;
        this.mainController = mainController;
        view = new GoLView(model.getCanvas());
        view.setListeners(this, this, this, this, this, this);
        view.updateCurrentMode(activeMode.toString());
        refreshCanvas();
    }

    /**
     * Berechnet die Daten, um die nächste Generation darzustellen
     */
    public synchronized void calculateNextGeneration() {
        Map<Point, Boolean> cellsToUpdate = new HashMap<>();
        Set<Point> deadCellsToCheck = new HashSet<>();
        for (Point p : new HashSet<>(model.getAliveCells())) {
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
    }

    /**
     * Berechnet über den Bresenham Algorithmus die nötigen Daten, um eine Linie zu zeichnen
     *
     * @param curr
     * @param preview
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
     * @param p
     * @param preview
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
     *  HIER FEHLT NOCH EIN KOMMENTAR !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     *
     * @param pos
     * @return
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
        activeMode = activeMode != Mode.RUNNING ? activeMode : Mode.PAINTING;
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
     *  Ein neuer Thread wird gestartet und das Game of Life fängt an zu laufen
     */
    @SuppressWarnings("BusyWait")
    public void startRunning() {
        activeMode = Mode.RUNNING;
        Runnable runningTask = () -> {
            while (activeMode == Mode.RUNNING) {
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
    }

    /**
     * Legt fest, für welchen Fall der ActionCommand was tun soll
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Löschen" -> clearCanvas();
            case "Auflösung" -> {
                activeMode = Mode.PAINTING;
                view.updateCanvasSize();
            }
            case "Farben" -> view.updateCellColor(model.getAliveCellColor(), model.getDeadCellColor());
            case "size" -> {
                model.setCanvas(new BufferedImage(view.getNewWidth(), view.getNewHeight(), BufferedImage.TYPE_INT_RGB));
                view.disposeSetSizeFrame();
                view.updateCanvasObject(model.getCanvas());
                clearCanvas();
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
            case "Laden" -> loadSavedFigure();
            case "Laufen" -> startRunning();
            case "Malen" -> {
                placingFigure = false;
                activeMode = Mode.PAINTING;
            }
            case "Setzen" -> {
                placingFigure = false;
                activeMode = Mode.SET;
            }
            case "Linien" -> {
                placingFigure = false;
                activeMode = Mode.LINE;
            }
            case "Kreuz" -> {
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
                prevPos = new Point(0, (model.getCanvasHeight() - 1) / 2);
                drawLineBresenham(new Point(model.getCanvasWidth(), (model.getCanvasHeight() - 1) / 2), false);
                prevPos = new Point((model.getCanvasWidth() - 1) / 2, 0);
                drawLineBresenham(new Point((model.getCanvasWidth() - 1) / 2, model.getCanvasHeight()), false);
            }
        }
        refreshCanvas();
        view.updateCurrentMode(activeMode.toString());
    }

    /**
     * Speichert den aktuellen Canvas als eine "Figur"
     */
    private void saveFigure() {
        if (!model.getAliveCells().isEmpty() && fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();
            GoLPrefab figureToSave = new GoLPrefab(selectedFile.getName(), normalizePosition(model.getAliveCells()));
            try {
                FileOutputStream fileOut = new FileOutputStream(filePath);
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                objectOut.writeObject(figureToSave);
                mainModel.setCurrentFigure(figureToSave);
                objectOut.close();
                fileOut.close();
                calculateCenter();
                refreshCanvas();
                placingFigure = true;
                activeMode = Mode.SET;
                mainController.updateRecentFiguresMenu(mainModel.getRecentFigures());
                JOptionPane.showMessageDialog(null, "Das Objekt wurde erfolgreich gespeichert.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Fehler beim Schreiben des Objekts: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Fehler beim Schreiben des Objekts: Leeres Objekt");
        }
    }

    /**
     *  Lädt eine "Figur" aus einem vorher gespeicherten Canvas
     */
    private void loadSavedFigure() {
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            try {
                FileInputStream fs = new FileInputStream(filePath);
                ObjectInputStream os = new ObjectInputStream(fs);
                GoLPrefab m = (GoLPrefab) os.readObject();
                int maxX = 0, maxY = 0;
                for (Point p : m.cells()) {
                    maxX = Math.max(p.x, maxX);
                    maxY = Math.max(p.y, maxY);
                }
                if (maxX > model.getCanvasWidth() || maxY > model.getCanvasHeight()) {
                    throw new Exception("Figur(Breite: " + maxX + ", Höhe: " + maxY + ") ist größer als Auflösung(Breite: " + model.getCanvasWidth() + ", Höhe: " + model.getCanvasHeight() + ")");
                }
                mainModel.updateRecentFigures(m);
                calculateCenter();
                refreshCanvas();
                placingFigure = true;
                activeMode = Mode.SET;
                mainController.updateRecentFiguresMenu(mainModel.getRecentFigures());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Fehler beim laden des Objekts: " + e.getMessage());
            }
        }
    }

    /**
     *
     */
    private void showPreview() {
        if (placingFigure) {
            for (Point p : lastCells) {
                model.setCanvasRGB(p, model.isCellAlive(p) ? model.getAliveCellColor() : model.getDeadCellColor());
            }
            lastCells.clear();
            for (Point p : mainModel.getCurrentFigure().cells()) {
                Point calculatedPoint = new Point(p.x + mousePos.x - mainModel.getCenter().x, p.y + mousePos.y - mainModel.getCenter().y);
                model.setCanvasRGB(calculateWrap(calculatedPoint), model.getInvertedColor());
                lastCells.add(calculateWrap(calculatedPoint));
            }
        } else if (activeMode == Mode.LINE && mouseHeld) {
            for (Point p : lastCells) {
                model.setCanvasRGB(calculateWrap(p), model.isCellAlive(p) ? model.getAliveCellColor() : model.getDeadCellColor());
            }
            lastCells.clear();
            drawLineBresenham(mousePos, true);
        } else {
            for (Point p : lastCells) {
                model.setCanvasRGB(calculateWrap(p), model.isCellAlive(p) ? model.getAliveCellColor() : model.getDeadCellColor());
            }
            lastCells.clear();
            paintPixel(mousePos, true);
        }
    }

    private Color invertColor(Color initalColor) {
        return new Color(255 - initalColor.getRed(), 255 - initalColor.getGreen(), 255 - initalColor.getBlue());
    }

    private Point calculateMousePosition(Point pos) {
        double scaleX = (double) model.getCanvasWidth() / view.getWidth();
        double scaleY = (double) model.getCanvasHeight() / view.getHeight();
        int posOnCanvasX = (int) (pos.x * scaleX);
        int posOnCanvasY = (int) (pos.y * scaleY);
        return new Point(posOnCanvasX, posOnCanvasY);
    }

    private void calculateCenter() {
        Point center = new Point();
        for (Point p : mainModel.getCurrentFigure().cells()) {
            center.x = Math.max(center.x, p.x);
            center.y = Math.max(center.y, p.y);
        }
        center.x /= 2;
        center.y /= 2;
        mainModel.setCenter(center);
    }

    private void rotate(int direction) {
        Set<Point> figure = mainModel.getCurrentFigure().cells();
        calculateCenter();
        Point center = mainModel.getCenter();

        Set<Point> rotatedFigure = new HashSet<>();
        for (Point p : figure) {
            Point relative = new Point(p.x - center.x, p.y - center.y);

            double radians = Math.toRadians(direction);
            int rotatedX = (int) Math.round(relative.x * Math.cos(radians) - relative.y * Math.sin(radians));
            int rotatedY = (int) Math.round(relative.x * Math.sin(radians) + relative.y * Math.cos(radians));

            rotatedFigure.add(new Point(rotatedX + center.x, rotatedY + center.y));
        }
        GoLPrefab rotatedPrefab = new GoLPrefab(mainModel.getCurrentFigure().name(), normalizePosition(rotatedFigure));
        mainModel.updateRecentFigures(rotatedPrefab);
        mainController.updateRecentFiguresMenu(mainModel.getRecentFigures());
        calculateCenter();
        showPreview();
    }

    private void flip(boolean horizontal) {
        Set<Point> mirroredFigure = new HashSet<>();
        for (Point p : mainModel.getCurrentFigure().cells()) {
            mirroredFigure.add(new Point(horizontal ? -p.x : p.x, horizontal ? p.y : -p.y));
        }
        GoLPrefab mirroredPrefab = new GoLPrefab(mainModel.getCurrentFigure().name(), normalizePosition(mirroredFigure));
        mainModel.updateRecentFigures(mirroredPrefab);
        mainController.updateRecentFiguresMenu(mainModel.getRecentFigures());
        calculateCenter();
        showPreview();
    }

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


    private enum Mode {
        RUNNING, PAINTING, SET, LINE
    }

    public void setPlacingFigure(boolean placingFigure) {
        this.placingFigure = placingFigure;
        activeMode = GoLController.Mode.SET;
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
            case KeyEvent.VK_S -> startRunning();
            case KeyEvent.VK_L -> {
                placingFigure = false;
                activeMode = Mode.LINE;
            }
            case KeyEvent.VK_R -> clearCanvas();
            case KeyEvent.VK_A -> {
                placingFigure = false;
                activeMode = Mode.PAINTING;
                view.updateCanvasSize();
            }
            case KeyEvent.VK_D -> {
                placingFigure = false;
                activeMode = Mode.PAINTING;
            }
            case KeyEvent.VK_P -> {
                placingFigure = false;
                activeMode = Mode.SET;
            }
        }
        view.updateCurrentMode(activeMode.toString());
        if (activeMode != Mode.RUNNING && e.getKeyCode() == KeyEvent.VK_SPACE) {
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
     *
     *
     * @param e the event to be processed
     */
    @Override
    public synchronized void mousePressed(MouseEvent e) {
        prevPos = calculateMousePosition(e.getPoint());
        painting = e.getButton() == 1;
        mouseHeld = true;
        if (placingFigure) {
            mainModel.getCurrentFigure().cells().forEach(p -> model.setCell(calculateWrap(new Point(p.x + prevPos.x - mainModel.getCenter().x, p.y + prevPos.y - mainModel.getCenter().y)), painting));
        } else if (activeMode != Mode.LINE) {
            paintPixel(mousePos, false);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        refreshCanvas();
    }

    /**
     *
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        mouseHeld = false;
        if (activeMode == Mode.LINE && !placingFigure) {
            mousePos = calculateMousePosition(e.getPoint());
            drawLineBresenham(mousePos, false);
        }
    }

    /**
     *
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        mousePos = calculateMousePosition(e.getPoint());
        if (activeMode == Mode.PAINTING && !placingFigure) {
            drawLineBresenham(mousePos, false);
            prevPos = mousePos;
        } else {
            mouseHeld = true;
            showPreview();
        }
    }

    /**
     * 
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        mousePos = calculateMousePosition(e.getPoint());
        showPreview();
    }
}