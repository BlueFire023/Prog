package second;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.*;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 15/06/2023
 */

public class GoLController implements ActionListener, KeyListener, MouseMotionListener, MouseListener, ChangeListener, WindowFocusListener, WindowListener, MouseWheelListener {
    private final GoLModel model = new GoLModel();
    private final GoLView view;
    private Point prevPos = new Point(), mousePos = new Point();
    private boolean painting, mouseHeld;
    private final Set<Point> lastCells = new HashSet<>();
    private final JFileChooser fileChooser = new JFileChooser();
    private Mode activeMode = Mode.PAINTING;
    private static final List<GoLController> instances = new ArrayList<>();
    private final Object lock = new Object();

    public GoLController() {
        view = new GoLView(model.getCanvas(), instances.size() + 1);
        view.initFiguresMenu(model.getPreMadeFigures());
        view.setListeners(this, this, this, this, this, this, this, this);
        refreshCanvas();
        instances.add(this);
    }

    public static void main(String[] args) {
        new GoLController();
    }

    public void calculateNextGeneration() {
        synchronized (lock) {
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
    }


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

    private void paintPixel(Point p, boolean preview) {
        int brushSize = model.getBrushSize();
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

    public Point calculateWrap(Point pos) {
        return new Point(Math.floorMod(pos.x, model.getCanvasWidth()), Math.floorMod(pos.y, model.getCanvasHeight()));
    }

    public void clearCanvas() {
        model.clearAliveCells();
        refreshCanvas();
        activeMode = activeMode != Mode.RUNNING ? activeMode : Mode.PAINTING;
    }

    private void refreshCanvas() {
        for (int i = 0; i < model.getCanvasWidth(); i++) {
            for (int j = 0; j < model.getCanvasHeight(); j++) {
                model.setCell(new Point(i, j), model.isCellAlive(new Point(i, j)));
            }
        }
    }

    @SuppressWarnings("BusyWait")
    public void calculateNextGenerationAll() {
        for (GoLController instance : instances) {
            instance.activeMode = Mode.RUNNING;
            instance.refreshCanvas();

            Runnable runningTask = () -> {
                while (instance.activeMode == Mode.RUNNING) {
                    try {
                        instance.calculateNextGeneration();
                        Thread.sleep(1000 / instance.view.getSliderstat());
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            };
            Thread runningThread = new Thread(runningTask);
            runningThread.start();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Löschen" -> clearCanvas();
            case "Neues Fenster" -> new GoLController();
            case "Auflösung" -> {
                activeMode = Mode.PAINTING;
                view.updateCanvasSize();
            }
            case "Farben" -> {
                view.updateCellColor(model.getAliveCellColor(), model.getDeadCellColor());
            }
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
            case "Laufen" -> calculateNextGenerationAll();
            case "Malen" -> activeMode = Mode.PAINTING;
            case "Setzen" -> activeMode = Mode.SET;
            case "Linien" -> activeMode = Mode.LINE;
            case "Kreuz" -> {
                activeMode = Mode.PAINTING;
                prevPos = new Point(0, 0);
                drawLineBresenham(new Point(model.getCanvasWidth() - 1, model.getCanvasHeight() - 1), false);
                prevPos = new Point(model.getCanvasWidth() - 1, 0);
                drawLineBresenham(new Point(0, model.getCanvasHeight() - 1), false);
            }
            case "Rahmen" -> {
                activeMode = Mode.PAINTING;
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
                activeMode = Mode.PAINTING;
                prevPos = new Point(0, (model.getCanvasHeight() - 1) / 2);
                drawLineBresenham(new Point(model.getCanvasWidth(), (model.getCanvasHeight() - 1) / 2), false);
                prevPos = new Point((model.getCanvasWidth() - 1) / 2, 0);
                drawLineBresenham(new Point((model.getCanvasWidth() - 1) / 2, model.getCanvasHeight()), false);
            }
            case "recent" -> {
                activeMode = Mode.PLACING;
                String name = ((JMenuItem) e.getSource()).getText();
                model.updateRecentFigures(name);
                view.updateRecentFiguresMenu(model.getRecentFigures(), this);
                calculateCenter();
            }
            default -> {
                int number = Integer.parseInt(e.getActionCommand());
                activeMode = Mode.PLACING;
                model.updateRecentFigures(model.getPreMadeFigures(number));
                view.updateRecentFiguresMenu(model.getRecentFigures(), this);
                calculateCenter();
            }
        }
        refreshCanvas();
        view.updateCurrentMode(activeMode.toString());
    }

    private void saveFigure() {
        if (!model.getAliveCells().isEmpty() && fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();
            GoLPrefab figureToSave = new GoLPrefab(selectedFile.getName(), normalizePosition(model.getAliveCells()));
            try {
                FileOutputStream fileOut = new FileOutputStream(filePath);
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                objectOut.writeObject(figureToSave);
                model.setCurrentFigure(figureToSave);
                objectOut.close();
                fileOut.close();
                calculateCenter();
                refreshCanvas();
                activeMode = Mode.PLACING;
                view.updateRecentFiguresMenu(model.getRecentFigures(), this);
                JOptionPane.showMessageDialog(null, "Das Objekt wurde erfolgreich gespeichert.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Fehler beim Schreiben des Objekts: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Fehler beim Schreiben des Objekts: Leeres Objekt");
        }
    }

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
                model.updateRecentFigures(m);
                calculateCenter();
                refreshCanvas();
                activeMode = Mode.PLACING;
                view.updateRecentFiguresMenu(model.getRecentFigures(), this);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Fehler beim laden des Objekts: " + e.getMessage());
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_E){
            clearCanvas();
        }
        if(e.getKeyCode() == KeyEvent.VK_A){
            activeMode = Mode.PAINTING;
            view.updateCanvasSize();
        }
        if(e.getKeyCode() == KeyEvent.VK_F){
            new GoLController();
        }
        if(e.getKeyCode() == KeyEvent.VK_C){
            view.updateCellColor(model.getAliveCellColor(), model.getDeadCellColor());
        }
        if (activeMode != Mode.RUNNING && e.getKeyCode() == KeyEvent.VK_SPACE) {
            calculateNextGeneration();
        } else if (activeMode == Mode.PLACING && (e.getKeyCode() == KeyEvent.VK_H || e.getKeyCode() == KeyEvent.VK_V)) { // Nur wenn die R-Taste losgelassen wird
            flip(e.getKeyCode() == KeyEvent.VK_V);
        } else {
            model.setBrushSize(Character.isDigit(e.getKeyChar()) ? Character.getNumericValue(e.getKeyChar()) : model.getBrushSize());
            showPreview();
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (activeMode == Mode.PLACING) {
            rotate(e.getWheelRotation() > 0 ? 90 : -90);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        synchronized (lock) {
            prevPos = calculateMousePosition(e.getPoint());
            painting = e.getButton() == 1;
            mouseHeld = true;
            if (activeMode == Mode.PLACING) {
                for (Point p : model.getCurrentFigure().cells()) {
                    model.setCell(calculateWrap(new Point(p.x + prevPos.x - model.getCenter().x, p.y + prevPos.y - model.getCenter().y)), painting);
                }
            } else if (activeMode != Mode.LINE) {
                paintPixel(mousePos, false);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseHeld = false;
        if (activeMode == Mode.LINE) {
            mousePos = calculateMousePosition(e.getPoint());
            drawLineBresenham(mousePos, false);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mousePos = calculateMousePosition(e.getPoint());
        if (activeMode == Mode.PAINTING) {
            drawLineBresenham(mousePos, false);
            prevPos = mousePos;
        } else {
            mouseHeld = true;
            showPreview();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePos = calculateMousePosition(e.getPoint());
        showPreview();
    }

    private void showPreview() {
        if (activeMode == Mode.PLACING) {
            for (Point p : lastCells) {
                model.setCanvasRGB(p, model.isCellAlive(p) ? model.getAliveCellColor() : model.getDeadCellColor());
            }
            lastCells.clear();
            for (Point p : model.getCurrentFigure().cells()) {
                Point calculatedPoint = new Point(p.x + mousePos.x - model.getCenter().x, p.y + mousePos.y - model.getCenter().y);
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

    @Override
    public void stateChanged(ChangeEvent e) {
    }

    private void calculateCenter() {
        Point center = new Point();
        for (Point p : model.getCurrentFigure().cells()) {
            center.x = Math.max(center.x, p.x);
            center.y = Math.max(center.y, p.y);
        }
        center.x /= 2;
        center.y /= 2;
        model.setCenter(center);
    }

    @Override
    public void windowGainedFocus(WindowEvent e) {

    }

    @Override
    public void windowLostFocus(WindowEvent e) {
        mouseHeld = false;
        for (Point p : lastCells) {
            model.setCanvasRGB(calculateWrap(p), model.isCellAlive(p) ? model.getAliveCellColor() : model.getDeadCellColor());
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        instances.removeIf(g -> e.getSource().equals(g.view.getFrame()));
    }

    @Override
    public void windowClosed(WindowEvent e) {
        int number = 1;
        for (GoLController g : instances) {
            g.updateWindowCountTitle(number++);
        }
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    private void rotate(int direction) {
        Set<Point> figure = model.getCurrentFigure().cells();
        calculateCenter();
        Point center = model.getCenter();

        Set<Point> rotatedFigure = new HashSet<>();
        for (Point p : figure) {
            Point relative = new Point(p.x - center.x, p.y - center.y);

            double radians = Math.toRadians(direction);
            int rotatedX = (int) Math.round(relative.x * Math.cos(radians) - relative.y * Math.sin(radians));
            int rotatedY = (int) Math.round(relative.x * Math.sin(radians) + relative.y * Math.cos(radians));

            rotatedFigure.add(new Point(rotatedX + center.x, rotatedY + center.y));
        }
        GoLPrefab rotatedPrefab = new GoLPrefab(model.getCurrentFigure().name(), normalizePosition(rotatedFigure));
        model.updateRecentFigures(rotatedPrefab);
        view.updateRecentFiguresMenu(model.getRecentFigures(), this);
        calculateCenter();
        showPreview();
    }

    private void flip(boolean horizontal) {
        Set<Point> mirroredFigure = new HashSet<>();
        for (Point p : model.getCurrentFigure().cells()) {
            mirroredFigure.add(new Point(horizontal ? -p.x : p.x, horizontal ? p.y : -p.y));
        }
        GoLPrefab mirroredPrefab = new GoLPrefab(model.getCurrentFigure().name(), normalizePosition(mirroredFigure));
        model.updateRecentFigures(mirroredPrefab);
        view.updateRecentFiguresMenu(model.getRecentFigures(), this);
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

    private void updateWindowCountTitle(int number) {
        view.setNewTitle(number);
    }

    private enum Mode {
        RUNNING, PAINTING, SET, PLACING, LINE
    }
}