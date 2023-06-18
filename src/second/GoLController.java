package second;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 15/06/2023
 */

public class GoLController implements Runnable, ActionListener, KeyListener, MouseMotionListener, MouseListener, ChangeListener {
    private final GoLModel model = new GoLModel();
    private final GoLView view;
    private Point prevPos = new Point();
    private Point lastCell = new Point(0, 0);
    private boolean painting;
    private final Set<Point> lastCells = new HashSet<>();
    private final JFileChooser fileChooser = new JFileChooser();
    private Mode activeMode = Mode.MALEN;
    private static List<GoLController> instances = new ArrayList<>();


    public GoLController() {
        view = new GoLView(model.getCanvas());
        view.initFiguresMenu(model.getPreMadeFigures());
        view.setListeners(this, this, this, this, this);
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


    private void drawLineBresenham(Point prev, Point curr, Boolean paint) {
        int dx = Math.abs(curr.x - prev.x), dy = Math.abs(curr.y - prev.y);
        int sx = prev.x < curr.x ? 1 : -1, sy = prev.y < curr.y ? 1 : -1;
        int err = dx - dy, e2;
        while (true) {
            model.setCell(calculateWrap(prev), paint);
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
            instance.activeMode = Mode.LAUFEN;
            instance.refreshCanvas();

            Runnable runningTask = () -> {
                while (instance.activeMode == Mode.LAUFEN) {
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
            case "Löschen" -> clearCanvas();
            case "Neues Fenster" -> new GoLController();
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
                activeMode = Mode.MALEN;
                refreshCanvas();
            }
            case "Setzen" -> {
                activeMode = Mode.SETZEN;
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
        model.setCurrentFigure(figureToSave);

        int returnValue = fileChooser.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();
            try {
                FileOutputStream fileOut = new FileOutputStream(filePath);
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                objectOut.writeObject(figureToSave);
                objectOut.close();
                fileOut.close();
                calculateCenter();
                refreshCanvas();
                activeMode = Mode.PLACING;
                JOptionPane.showMessageDialog(null, "Das Objekt wurde erfolgreich gespeichert.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Fehler beim Schreiben des Objekts: " + e.getMessage());
            }
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
        if (activeMode != Mode.LAUFEN) {
            calculateNextGeneration();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        prevPos = calculateMousePosition(e.getPoint());
        painting = e.getButton() == 1;
        if (activeMode == Mode.PLACING) {
            for (Point p : model.getCurrentFigure().cells()) {
                model.setCell(calculateWrap(new Point(p.x + prevPos.x - model.getCenter().x, p.y + prevPos.y - model.getCenter().y)), true);
            }
        } else {
            model.setCell(calculateWrap(prevPos), painting);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (activeMode == Mode.MALEN) {
            Point currPos = calculateMousePosition(e.getPoint());
            drawLineBresenham(prevPos, currPos, painting);
            prevPos = currPos;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Point pos = calculateMousePosition(e.getPoint());
        if (activeMode == Mode.PLACING) {
            for (Point p : lastCells) {
                model.setCanvasRGB(p, model.isCellAlive(p) ? model.getAliveCellColor() : model.getDeadCellColor());
            }
            lastCells.clear();
            for (Point p : model.getCurrentFigure().cells()) {
                Point calculatedPoint = new Point(p.x + pos.x - model.getCenter().x, p.y + pos.y - model.getCenter().y);
                model.setCanvasRGB(calculateWrap(calculatedPoint), model.getInvertedColor());
                lastCells.add(calculateWrap(calculatedPoint));
            }
        } else {
            model.setCanvasRGB(calculateWrap(lastCell), model.isCellAlive(lastCell) ? model.getAliveCellColor() : model.getDeadCellColor());
            model.setCanvasRGB(pos, model.getInvertedColor());
            lastCell = pos;
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
    public void run() {


    }

    private enum Mode {
        LAUFEN, MALEN, SETZEN, PLACING, LINE
    }
}
