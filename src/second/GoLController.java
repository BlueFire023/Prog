package second;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Denis Schaffer, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Filip Schepers
 * @version 1, 15/06/2023
 */
public class GoLController implements ActionListener, KeyListener, MouseMotionListener, MouseListener, Runnable {
    private GoLModel model = new GoLModel();
    private GoLView view;
    private Point prevPos = new Point();
    private Point lastCell = new Point(0, 0);
    private Set<Point> savedFigure = new HashSet<Point>();
    private boolean placingFigure = false;
    private int highestX, highestY;
    Set<Point> lastCells = new HashSet<>();

    public GoLController() {
        view = new GoLView(model.getCanvas());
        view.setListeners(this, this, this, this);
        updateCanvasColors();
        new Thread(this).start();
    }

    public static void main(String[] args) {
        new GoLController();
    }

    public void calculateNextGeneration() {
        Set<Point> cellsToAdd = new HashSet<>();
        Set<Point> cellsToRemove = new HashSet<>();
        Set<Point> deadCellsToCheck = new HashSet<>();
        int aliveCellsCount;
        for (Point p : model.getAliveCells()) {
            aliveCellsCount = 0;
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    Point newPos = new Point(p.x + i, p.y + j);
                    if (newPos.equals(p)) {
                        continue;
                    }
                    if (model.isCellAlive(calculateWrap(newPos))) {
                        aliveCellsCount++;
                    } else {
                        deadCellsToCheck.add(new Point(newPos.x, newPos.y));
                    }
                }
            }
            if (aliveCellsCount < 2) {
                cellsToRemove.add(p);
            }
            if (aliveCellsCount > 3) {
                cellsToRemove.add(p);
            }
        }
        for (Point p : deadCellsToCheck) {
            aliveCellsCount = 0;
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    Point newPos = new Point(p.x + i, p.y + j);
                    if (newPos.equals(p)) {
                        continue;
                    }
                    if (model.isCellAlive(calculateWrap(newPos))) {
                        aliveCellsCount++;
                    }
                }
            }
            if (aliveCellsCount == 3) {
                cellsToAdd.add(p);
            }
        }
        for (Point p : cellsToRemove) {
            model.setCell(calculateWrap(p), false);
        }
        for (Point p : cellsToAdd) {
            model.setCell(calculateWrap(p), true);
        }
    }

    private void drawLineBresenham(Point prev, Point curr) {
        int dx = Math.abs(curr.x - prev.x), dy = Math.abs(curr.y - prev.y);
        int sx = prev.x < curr.x ? 1 : -1, sy = prev.y < curr.y ? 1 : -1;
        int err = dx - dy, e2;
        while (true) {
            model.setCell(calculateWrap(prev), true);
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
        updateCanvasColors();
    }

    private void updateCanvasColors() {
        for (int i = 0; i < model.getCanvasWidth(); i++) {
            for (int j = 0; j < model.getCanvasHeight(); j++) {
                model.setCell(calculateWrap(new Point(i, j)), model.isCellAlive(new Point(i, j)));
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Clear" -> clearCanvas();
            case "Set Size" -> view.updateCanvasSize();
            case "Set Color" -> view.updateCellColor(model.getAliveCellColor(), model.getDeadCellColor());
            case "size" -> {
                clearCanvas();
                model.setCanvas(new BufferedImage(view.getNewWidth(), view.getNewHeight(), BufferedImage.TYPE_INT_RGB));
                view.disposeSetSizeFrame();
                view.updateCanvasObject(model.getCanvas());
                updateCanvasColors();
            }
            case "acc" -> {
                Color newColor = JColorChooser.showDialog(view, "Wähle eine Farbe", model.getAliveCellColor());
                if (newColor == null) {
                    newColor = model.getAliveCellColor();
                }
                model.setAliveCellColor(newColor);
                ((JButton) e.getSource()).setBackground(newColor);
                updateCanvasColors();
            }
            case "dcc" -> {
                Color newColor = JColorChooser.showDialog(view, "Wähle eine Farbe", model.getDeadCellColor());
                if (newColor == null) {
                    newColor = model.getDeadCellColor();
                }
                model.setDeadCellColor(newColor);
                ((JButton) e.getSource()).setBackground(newColor);
                updateCanvasColors();
            }
            case "Save" -> {
                savedFigure.clear();
                highestX = 0;
                highestY = 0;
                int lowestX = model.getCanvasWidth(), lowestY = model.getCanvasHeight();
                for (Point p : model.getAliveCells()) {
                    if (lowestX > p.x) {
                        lowestX = p.x;
                    }
                    if (highestX < p.x) {
                        highestX = p.x;
                    }
                    if (lowestY > p.y) {
                        lowestY = p.y;
                    }
                    if (highestY < p.y) {
                        highestY = p.y;
                    }
                }
                highestX -= lowestX;
                highestY -= lowestY;
                for (Point p : model.getAliveCells()) {
                    savedFigure.add(new Point(p.x - lowestX, p.y - lowestY));
                }
            }
            case "Load" -> {
                placingFigure = true;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        calculateNextGeneration();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        prevPos = calculateMousePosition(e.getPoint());
        if (!placingFigure) {
            model.setCell(calculateWrap(prevPos), true);
        } else {
            for (Point p : savedFigure) {
                model.setCell(calculateWrap(new Point(p.x + prevPos.x - (highestX / 2), p.y + prevPos.y - (highestY / 2))), true);
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
        Point currPos = calculateMousePosition(e.getPoint());
        drawLineBresenham(prevPos, currPos);
        prevPos = currPos;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Point pos = calculateMousePosition(e.getPoint());
        if (!placingFigure) {
            model.setCanvasRGB(lastCell, model.isCellAlive(lastCell) ? model.getAliveCellColor() : model.getDeadCellColor());
            model.setCanvasRGB(pos, model.isCellAlive(pos) ? model.getAliveCellColor() : invertColor(model.getDeadCellColor()));
            lastCell = pos;
        } else {
            for (Point p : lastCells) {
                model.setCanvasRGB(p, model.isCellAlive(p) ? model.getAliveCellColor() : model.getDeadCellColor());
            }
            lastCells.clear();
            for (Point p : savedFigure) {
                Point calculatedPoint = new Point(p.x + pos.x - (highestX / 2), p.y + pos.y - (highestY / 2));
                model.setCanvasRGB(calculateWrap(calculatedPoint), model.isCellAlive(calculatedPoint) ? model.getAliveCellColor() : invertColor(model.getDeadCellColor()));
                lastCells.add(calculateWrap(calculatedPoint));
            }
        }
    }

    @Override
    public void run() {

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
}
