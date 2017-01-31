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
public class Input implements KeyListener, MouseListener, MouseWheelListener, MouseMotionListener {

    private final Model model;
    private final Settings settings;
    private View view;
    private double scaleSpeed = 1.3;
    private Point<Double> startDrag = new Point<>(0d, 0d);
    private Point<Double> movedDrag = new Point<>(0d, 0d);
    private Boolean drag = false;

    public Input(View view, Model model, Settings settings) {
        this.view = view;
        this.model = model;
        this.settings = settings;
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
                view.zoomView(scaleSpeed);
                break;
            }
            case 109: { // -
                view.zoomView(1 / scaleSpeed);
                break;
            }
            case 68: { // a
                transform.translate(-speed / transform.getScaleX(), 0.0);
                break;
            }
            case 65: { // d
                transform.translate(+speed / transform.getScaleX(), 0.0);
                break;
            }
            case 87: { // w
                transform.translate(0.0, +speed / transform.getScaleX());
                break;
            }
            case 83: { // s
                transform.translate(0.0, -speed / transform.getScaleX());
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
        if ((e.getModifiersEx() & MouseEvent.BUTTON3_DOWN_MASK) != 0) {
            model.addShape(new Rectangle(positionInView(e), 0d, 20d));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    public Point<Double> positionInView(MouseEvent e) {
        return view.positionInView(new Point<>((double) e.getX(), (double) e.getY()));
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() == 1) {
            view.zoomView(1 / scaleSpeed);
        } else {
            view.zoomView(scaleSpeed);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0) {
            if (drag) {
                Point<Double> now = positionInView(e);
                Point<Double> move = new Point<>(now.x - startDrag.x, now.y - startDrag.y);
                view.moveView(move);
            } else {
                drag = true;
                startDrag = positionInView(e);
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        drag = false;
    }
}
