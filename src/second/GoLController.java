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
    int prevX, prevY;
    private Point lastCell = new Point(0, 0);


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
        int newX, newY, aliveCellsCount;
        for (Point p : model.getAliveCells()) {
            aliveCellsCount = 0;
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    newX = p.x + i;
                    newY = p.y + j;
                    if (new Point(newX, newY).equals(p)) {
                        continue;
                    }
                    if (model.isCellAlive(calculateWrap(newX, newY))) {
                        aliveCellsCount++;
                    } else {
                        deadCellsToCheck.add(new Point(newX, newY));
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
                    newX = p.x + i;
                    newY = p.y + j;
                    if (new Point(newX, newY).equals(p)) {
                        continue;
                    }
                    if (model.isCellAlive(calculateWrap(newX, newY))) {
                        aliveCellsCount++;
                    }
                }
            }
            if (aliveCellsCount == 3) {
                cellsToAdd.add(p);
            }
        }
        for (Point p : cellsToRemove) {
            model.setCell(calculateWrap(p.x, p.y), false);
        }
        for (Point p : cellsToAdd) {
            model.setCell(calculateWrap(p.x, p.y), true);
        }
    }

    private void drawLineBresenham(int x0, int y0, int x1, int y1) {
        int dx = Math.abs(x1 - x0), dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1, sy = y0 < y1 ? 1 : -1;
        int err = dx - dy, e2;
        while (true) {
            model.setCell(calculateWrap(x0, y0), true);
            if (x0 == x1 && y0 == y1) {
                break;
            }
            e2 = err * 2;
            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }
    }

    public Point calculateWrap(int x, int y) {
        return new Point(Math.floorMod(x, model.getCanvasWidth()), Math.floorMod(y, model.getCanvasHeight()));
    }

    public void clearCanvas() {
        model.clearAliveCells();
        updateCanvasColors();
    }

    private void updateCanvasColors() {
        for (int i = 0; i < model.getCanvasWidth(); i++) {
            for (int j = 0; j < model.getCanvasHeight(); j++) {
                model.setCell(calculateWrap(i, j), model.isCellAlive(new Point(i, j)));
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
        prevX = (e.getX() - (model.getFrameWidth() - view.getWidth())) * model.getCanvasWidth() / view.getWidth();
        prevY = (e.getY() - (model.getFrameHeight() - view.getHeight())) * model.getCanvasHeight() / view.getHeight();
        model.setCell(calculateWrap(prevX, prevY), true);
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
        int x = (e.getX() - (model.getFrameWidth() - view.getWidth())) * model.getCanvasWidth() / view.getWidth();
        int y = (e.getY() - (model.getFrameHeight() - view.getHeight())) * model.getCanvasHeight() / view.getHeight();
        drawLineBresenham(prevX, prevY, x, y);
        prevX = x;
        prevY = y;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = (e.getX() - (model.getFrameWidth() - view.getWidth())) * model.getCanvasWidth() / view.getWidth();
        int y = (e.getY() - (model.getFrameHeight() - view.getHeight())) * model.getCanvasHeight() / view.getHeight();
        try {
            if (!model.isCellAlive(new Point(x, y))) {
                model.setCanvasRGB(lastCell.x, lastCell.y, model.isCellAlive(lastCell) ? model.getAliveCellColor() : model.getDeadCellColor());
                model.setCanvasRGB(x, y, invertColor(model.getDeadCellColor()));
                lastCell = new Point(x, y);
            } else {
                model.setCanvasRGB(lastCell.x, lastCell.y, model.isCellAlive(lastCell) ? model.getAliveCellColor() : model.getDeadCellColor());
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    public void run() {

    }
    private Color invertColor(Color initalColor) {
        return new Color(255 - initalColor.getRed(), 255 - initalColor.getGreen(), 255 - initalColor.getBlue());
    }
}
