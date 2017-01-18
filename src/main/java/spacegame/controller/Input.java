package spacegame.controller;

import spacegame.Settings;
import spacegame.model.Model;
import spacegame.model.basics.Point;
import spacegame.model.things.BaseShape;
import spacegame.model.things.Rectangle;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author Václav Blažej
 */
public class Input implements KeyListener, MouseListener {

    private final Model model;
    private Point<Integer> last;
    private BaseShape lastShape;

    public Input(Model model, Settings settings) {
        this.model = model;
        last = null;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        final Point<Double> position = new Point<>((double) e.getX(), (double) e.getY());
        lastShape = new Rectangle(position, 0., 20.);
        model.addShape(lastShape);
        last = new Point<>(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (last != null) {
            lastShape.velocity.x += (e.getX() - last.x) / 40.;
            lastShape.velocity.y += (e.getY() - last.y) / 40.;
            last = null;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
