package second;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 15/06/2023
 */

public class GoLController implements ActionListener, KeyListener, MouseMotionListener, MouseListener, ChangeListener {
    private GoLModel model = new GoLModel();
    private GoLView view;
    private Point prevPos = new Point();
    private Point lastCell = new Point(0, 0);
    private boolean placingFigure = false, painting;
    private Set<Point> lastCells = new HashSet<>();

    private JFileChooser fileChooser = new JFileChooser();

    public GoLController() {
        view = new GoLView(model.getCanvas());
        view.setListeners(this, this, this, this, this);
        refreshCanvas();
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

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Löschen" -> clearCanvas();
            case "Neues Fenster" -> new GoLController();
            case "Auflösung" -> view.updateCanvasSize();
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
            case "Speichern" -> {
                saveFigure();
                calculateCenter();
                refreshCanvas();
                model.setLaufen(false);
                model.setMalen(false);
                model.setSetzen(true);
                placingFigure = true;
            }
            case "Laden" -> {
                loadSavedFigure();
                calculateCenter();
                refreshCanvas();
                model.setLaufen(false);
                model.setMalen(false);
                model.setSetzen(true);
                placingFigure = true;
            }
            case "Laufen" -> {
                model.setLaufen(true);
                model.setMalen(false);
                model.setSetzen(false);

                refreshCanvas();

                Runnable runningTask = () -> {
                    while (model.isLaufen()) {
                        model.setSpeed(view.getSliderstat());
                        long loopDelay = 1000 / model.getSpeed();
                        calculateNextGeneration();
                        try {
                            Thread.sleep(loopDelay);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                };
                Thread runningThread = new Thread(runningTask);
                runningThread.start();
            }
            case "Malen" -> {
                model.setLaufen(false);
                model.setMalen(true);
                model.setSetzen(false);
                refreshCanvas();
            }
            case "Setzen" -> {
                model.setLaufen(false);
                model.setMalen(false);
                model.setSetzen(true);
                placingFigure = false;
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
        //calculateNextGeneration();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (model.isSetzen() || model.isMalen()) {
            prevPos = calculateMousePosition(e.getPoint());
            painting = e.getButton() == 1;
            if (placingFigure && !model.isMalen()) {
                for (Point p : model.getCurrentFigure().cells()) {
                    model.setCell(calculateWrap(new Point(p.x + prevPos.x - (model.getCenter().x / 2), p.y + prevPos.y - (model.getCenter().y / 2))), true);
                }
            } else {
                model.setCell(calculateWrap(prevPos), painting);
            }
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
        if (model.isMalen()) {
            Point currPos = calculateMousePosition(e.getPoint());
            drawLineBresenham(prevPos, currPos, painting);
            prevPos = currPos;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (model.isSetzen() || model.isMalen()) {
            Point pos = calculateMousePosition(e.getPoint());
            if (placingFigure && !model.isMalen()) {
                for (Point p : lastCells) {
                    model.setCanvasRGB(p, model.isCellAlive(p) ? model.getAliveCellColor() : model.getDeadCellColor());
                }
                lastCells.clear();
                for (Point p : model.getCurrentFigure().cells()) {
                    Point calculatedPoint = new Point(p.x + pos.x - (model.getCenter().x / 2), p.y + pos.y - (model.getCenter().y / 2));
                    model.setCanvasRGB(calculateWrap(calculatedPoint), model.getInvertedColor());
                    lastCells.add(calculateWrap(calculatedPoint));
                }
            } else {
                model.setCanvasRGB(calculateWrap(lastCell), model.isCellAlive(lastCell) ? model.getAliveCellColor() : model.getDeadCellColor());
                model.setCanvasRGB(pos, model.getInvertedColor());
                lastCell = pos;
            }
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
        model.setCenter(center);
    }
}
