package spacegame.controller;

import spacegame.Settings;
import spacegame.model.Model;
import spacegame.model.basics.Point;
import spacegame.model.things.Rectangle;
import spacegame.view.View;

import java.awt.event.*;
import java.awt.geom.AffineTransform;

/**
 * @author Václav Blažej
 */
public class Input implements KeyListener, MouseListener, MouseWheelListener {

    private final Model model;
    private final Settings settings;
    private Point<Integer> last;
    private View view;
    private double scaleSpeed = 1.3;

    public Input(View view, Model model, Settings settings) {
        this.view = view;
        this.model = model;
        this.settings = settings;
        last = null;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        final AffineTransform transform = settings.getViewTransform();
        double speed = 3;
        switch (e.getKeyCode()) {
            case 107: { // +
                transform.setToScale(transform.getScaleX() * scaleSpeed, transform.getScaleY() * scaleSpeed);
                break;
            }
            case 109: { // -
                transform.setToScale(transform.getScaleX() / scaleSpeed, transform.getScaleY() / scaleSpeed);
                break;
            }
            case 68: { // a
                transform.translate(-speed, 0.0);
                break;
            }
            case 65: { // d
                transform.translate(+speed, 0.0);
                break;
            }
            case 87: { // w
                transform.translate(0.0, +speed);
                break;
            }
            case 83: { // s
                transform.translate(0.0, -speed);
                break;
            }
            case 37: { // <-
                view.prevState();
                break;
            }
            case 39: { // ->
                view.nextState();
                break;
            }
            case 67: { // c (center view on everything)
                view.center();
                break;
            }
            default:
                System.out.println("input not known key: " + e.getKeyChar() + ", " + e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        final Point<Double> position = positionInView(e);
        model.addShape(view.getFrame(), new Rectangle(position, 0., 20.));
        last = new Point<>(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    public Point<Double> positionInView(MouseEvent e) {
        final java.awt.Point point = e.getPoint();
        final AffineTransform viewTransform = settings.getViewTransform();
        final Point<Double> res = new Point<>(0., 0.);
        res.x = (point.x - viewTransform.getTranslateX()) / viewTransform.getScaleX();
        res.y = (point.y - viewTransform.getTranslateY()) / viewTransform.getScaleY();
        return res;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        final AffineTransform transform = settings.getViewTransform();
        if (e.getWheelRotation() == 1) {
            transform.setToScale(transform.getScaleX() / scaleSpeed, transform.getScaleY() / scaleSpeed);
        } else {
            transform.setToScale(transform.getScaleX() * scaleSpeed, transform.getScaleY() * scaleSpeed);
        }
    }
}
