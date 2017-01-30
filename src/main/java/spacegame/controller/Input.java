package spacegame.controller;

import spacegame.Settings;
import spacegame.model.Model;
import spacegame.model.basics.Point;
import spacegame.model.things.BaseShape;
import spacegame.model.things.Rectangle;
import spacegame.view.View;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;

/**
 * @author Václav Blažej
 */
public class Input implements KeyListener, MouseListener {

    private final Model model;
    private final Settings settings;
    private Point<Integer> last;
    private BaseShape lastShape;
    private View view;

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
        double scaleSpeed = 1.3;
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
        final Point<Double> position = new Point<>((double) e.getX(), (double) e.getY());
        lastShape = new Rectangle(position, 0., 20.);
        model.addShape(view.getFrame(), lastShape);
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
