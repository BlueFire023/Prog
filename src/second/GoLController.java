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

public class GoLController implements ActionListener, KeyListener, MouseMotionListener, MouseListener, ChangeListener, WindowFocusListener, WindowListener {
    private final GoLModel model = new GoLModel();
    private final GoLView view;
    private Point prevPos = new Point(), lastCell = new Point(), mousePos = new Point();
    private boolean painting, mouseHeld;
    private final Set<Point> lastCells = new HashSet<>();
    private final JFileChooser fileChooser = new JFileChooser();
    private Mode activeMode = Mode.PAINT;
    private static final List<GoLController> instances = new ArrayList<>();

    public GoLController() {
        view = new GoLView(model.getCanvas(), instances.size() + 1);
        view.initFiguresMenu(model.getPreMadeFigures());
        view.setListeners(this, this, this, this, this, this, this);
        refreshCanvas();
        instances.add(this);
    }

    public static void main(String[] args) {
        new GoLController();
    }

    public void calculateNextGeneration() {
        Map<Point, Boolean> cellsToUpdate = new HashMap<>();
        Set<Point> deadCellsToCheck = new HashSet<>();
        for (Point p : model.getAliveCells()) {
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


    private void drawLineBresenham(Point curr, Boolean paint, Boolean preview) {
        Point prev = new Point(prevPos.x, prevPos.y);
        int dx = Math.abs(curr.x - prev.x), dy = Math.abs(curr.y - prev.y);
        int sx = prev.x < curr.x ? 1 : -1, sy = prev.y < curr.y ? 1 : -1;
        int err = dx - dy, e2;
        while (true) {
            if (preview) {
                model.setCanvasRGB(calculateWrap(prev), model.getInvertedColor());
                lastCells.add(calculateWrap(prev));
            } else {
                model.setCell(calculateWrap(prev), paint);
            }
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

    public Point calculateWrap(Point pos) {
        return new Point(Math.floorMod(pos.x, model.getCanvasWidth()), Math.floorMod(pos.y, model.getCanvasHeight()));
    }

    public void clearCanvas() {
        model.clearAliveCells();
        refreshCanvas();
    }

    private void refreshCanvas() {
        for (int i = 0; i < model.getCanvasWidth(); i++) {
            for (int j = 0; j < model.getCanvasHeight(); j++) {
                model.setCell(new Point(i, j), model.isCellAlive(new Point(i, j)));
            }
        }
    }

    public void calculateNextGenerationAll() {
        for (GoLController instance : instances) {
            instance.activeMode = Mode.RUN;
            instance.refreshCanvas();

            Runnable runningTask = () -> {
                while (instance.activeMode == Mode.RUN) {
                    instance.calculateNextGeneration();
                    try {
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
            case "Löschen" -> {
                clearCanvas();
                activeMode = Mode.SET;
            }
            case "Neues Fenster" -> {
                new GoLController();
                refreshCanvas();
            }
            case "Auflösung" -> {
                lastCells.clear();
                view.updateCanvasSize();
            }
            case "Farben" -> view.updateCellColor(model.getAliveCellColor(), model.getDeadCellColor());
            case "size" -> {
                clearCanvas();
                model.setCanvas(new BufferedImage(view.getNewWidth(), view.getNewHeight(), BufferedImage.TYPE_INT_RGB));
                view.disposeSetSizeFrame();
                view.updateCanvasObject(model.getCanvas());
                refreshCanvas();
            }
            case "acc" -> {
                Color newColor = JColorChooser.showDialog(view, "Wähle eine Farbe", model.getAliveCellColor());
                if (newColor == null) {
                    newColor = model.getAliveCellColor();
                }
                model.setAliveCellColor(newColor);
                ((JButton) e.getSource()).setBackground(newColor);
                refreshCanvas();
            }
            case "dcc" -> {
                Color newColor = JColorChooser.showDialog(view, "Wähle eine Farbe", model.getDeadCellColor());
                if (newColor == null) {
                    newColor = model.getDeadCellColor();
                }
                model.setDeadCellColor(newColor);
                model.setInvertedColor(invertColor(newColor));
                ((JButton) e.getSource()).setBackground(newColor);
                refreshCanvas();
            }
            case "Speichern" -> saveFigure();
            case "Laden" -> loadSavedFigure();
            case "Laufen" -> calculateNextGenerationAll();
            case "Malen" -> {
                activeMode = Mode.PAINT;
                refreshCanvas();
            }
            case "Setzen" -> {
                activeMode = Mode.SET;
                refreshCanvas();
            }
            case "Linien" -> {
                activeMode = Mode.LINE;
                refreshCanvas();
            }
            default -> {
                model.setCurrentFigure(model.getPreMadeFigures(Integer.parseInt(e.getActionCommand())));
                activeMode = Mode.PLACING;
                calculateCenter();
                refreshCanvas();
            }
        }
    }

    private void saveFigure() {
        Set<Point> figureConstruct = new HashSet<>();
        int lowestX = model.getCanvasWidth(), lowestY = model.getCanvasHeight();
        for (Point p : model.getAliveCells()) {
            if (lowestX > p.x) {
                lowestX = p.x;
            }
            if (lowestY > p.y) {
                lowestY = p.y;
            }
        }
        for (Point p : model.getAliveCells()) {
            figureConstruct.add(new Point(p.x - lowestX, p.y - lowestY));
        }
        GoLPrefab figureToSave = new GoLPrefab("Test", figureConstruct);
        if (!figureToSave.cells().isEmpty() && fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();
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
                model.setCurrentFigure(m);
                calculateCenter();
                refreshCanvas();
                activeMode = Mode.PLACING;
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
        /*if (activeMode != Mode.RUN) {
            calculateNextGeneration();
        }*/
        if (e.getKeyCode() == KeyEvent.VK_R) { // Nur wenn die R-Taste losgelassen wird
            rotate();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        prevPos = calculateMousePosition(e.getPoint());
        painting = e.getButton() == 1;
        mouseHeld = true;
        if (activeMode == Mode.PLACING) {
            for (Point p : model.getCurrentFigure().cells()) {
                model.setCell(calculateWrap(new Point(p.x + prevPos.x - model.getCenter().x, p.y + prevPos.y - model.getCenter().y)), true);
            }
        } else if (activeMode != Mode.LINE) {
            model.setCell(calculateWrap(prevPos), painting);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseHeld = false;
        if (activeMode == Mode.LINE) {
            mousePos = calculateMousePosition(e.getPoint());
            drawLineBresenham(mousePos, painting, false);
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
        if (activeMode == Mode.PAINT) {
            mousePos = calculateMousePosition(e.getPoint());
            drawLineBresenham(mousePos, painting, false);
            prevPos = mousePos;
        } else {
            mouseHeld = true;
            mousePos = calculateMousePosition(e.getPoint());
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
            drawLineBresenham(mousePos, painting, true);
        } else {
            model.setCanvasRGB(calculateWrap(lastCell), model.isCellAlive(lastCell) ? model.getAliveCellColor() : model.getDeadCellColor());
            model.setCanvasRGB(calculateWrap(mousePos), model.getInvertedColor());
            lastCell = mousePos;
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
        Point center = new Point(0, 0);
        for (Point p : model.getCurrentFigure().cells()) {
            if (center.x < p.x) {
                center.x = p.x;
            }
            if (center.y < p.y) {
                center.y = p.y;
            }
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

    private void rotate() {
        Set<Point> objectPoints = new HashSet<>(model.getCurrentFigure().cells());
        int centerX = 0;
        int centerY = 0;
        for (Point point : objectPoints) {
            centerX += point.x;
            centerY += point.y;
        }
        centerX /= objectPoints.size();
        centerY /= objectPoints.size();
        for (Point point : objectPoints) {
            int relativeX = point.x - centerX;
            int relativeY = point.y - centerY;

            // Führe die Rotationsformel durch
            double radians = Math.toRadians(90);
            int rotatedX = (int) Math.round(relativeX * Math.cos(radians) - relativeY * Math.sin(radians));
            int rotatedY = (int) Math.round(relativeX * Math.sin(radians) + relativeY * Math.cos(radians));

            // Aktualisiere den Punkt aufgrund der Rotation
            point.x = rotatedX + centerX;
            point.y = rotatedY + centerY;
        }
        Set<Point> figureConstruct = new HashSet<>();
        int lowestX = 0, lowestY = 0;
        for (Point p : objectPoints) {
            if (lowestX > p.x) {
                lowestX = p.x;
            }
            if (lowestY > p.y) {
                lowestY = p.y;
            }
        }
        for (Point p : objectPoints) {
            figureConstruct.add(new Point(p.x - lowestX, p.y - lowestY));
        }
        model.setCurrentFigure(new GoLPrefab("rotated", figureConstruct));
        calculateCenter();
        showPreview();
    }

    private void updateWindowCountTitle(int number) {
        view.setNewTitle(number);
    }

    private enum Mode {
        RUN, PAINT, SET, PLACING, LINE
    }


}
