package spacegame.view;

import spacegame.Settings;
import spacegame.controller.Controller;
import spacegame.model.Model;
import spacegame.model.basics.Point;
import spacegame.model.things.BaseShape;
import spacegame.view.thingview.Painter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Václav Blažej
 */
public class View extends JPanel implements ActionListener {

    private final Timer timer;
    private final Model model;
    private final Controller controller;
    private final Settings settings;
    private final spacegame.view.thingview.Painter painter;
    private final AffineTransform defaultTransform = new AffineTransform();
    private int fps, fpscnt, targetFps = 20;
    private int frame;
    private int border = 40;

    public View(Model model, Controller controllerArg, Settings settings) {
        super(true);
        this.model = model;
        this.controller = controllerArg;
        this.settings = settings;
        this.setFocusable(true);
        this.timer = new Timer(1000 / targetFps, this);
        fpscnt = 0;
        frame = 0;
        new Timer(500, e -> {
            fps = 2 * fpscnt;
            fpscnt = 0;
        }).start();
        this.painter = new Painter(model);
        SwingUtilities.invokeLater(timer::start);
    }

    public Model getModel() {
        return model;
    }

    @Override
    public void paint(Graphics gg) {
        super.paint(gg);
        final Graphics2D g = (Graphics2D) gg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //Set  anti-alias!
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); // Set anti-alias for text
        gg.setColor(Color.BLACK);
        gg.fillRect(0, 0, getWidth(), getHeight());
        final AffineTransform transform = settings.getViewTransform();
        painter.paint(gg, this, transform);
        g.setColor(Color.GREEN);
        g.setTransform(defaultTransform);
        if (settings.isShowInfo()) {
            int y = 20;
            g.drawString("POSITION " + transform, 10, y);
            y += 20;
            g.drawString("FPS " + fps, 10, y);
            y += 20;
            g.drawString("FRAME ( " + (frame + 1) + " / " + model.size() + " )", 10, y);
        }
        fpscnt++;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public void nextState() {
        this.frame = Math.min(model.size() - 1, frame + 1);
    }

    public void prevState() {
        this.frame = Math.max(0, frame - 1);
    }

    public int getFrame() {
        return frame;
    }

    public void center() {
        final Point<Double> mx = new Point<>(Double.MIN_VALUE, Double.MIN_VALUE);
        final Point<Double> mn = new Point<>(Double.MAX_VALUE, Double.MAX_VALUE);
        final java.util.List<BaseShape> shapes = model.getShapes(frame);
        final List<Point<Double>> points = shapes.stream().map(BaseShape::getPosition).collect(Collectors.toList());
        final List<Point<Double>> check = new ArrayList<>();
        for (Point<Double> point : points) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if(i*i+j*j == 2) {
                        check.add(new Point<>(point.x + i * border, point.y + j * border));
                    }
                }
            }
        }
        for (Point<Double> pt : check) {
            mx.x = Math.max(mx.x, pt.x);
            mx.y = Math.max(mx.y, pt.y);
            mn.x = Math.min(mn.x, pt.x);
            mn.y = Math.min(mn.y, pt.y);
        }
        setView(new Point<>(mn.x.intValue(), mn.y.intValue()), new Point<>(mx.x.intValue(), mx.y.intValue()));
    }

    public void setView(Point<Integer> mn, Point<Integer> mx) {
        final AffineTransform transform = new AffineTransform();
        Point<Integer> size = new Point<>(mx.x - mn.x, mx.y - mn.y);
        final double scale = 1 / Math.max((double) size.x / getWidth(), (double) size.y / getHeight());
        transform.scale(scale, scale);
        transform.translate(-mn.x, -mn.y);
        settings.setViewTransform(transform);
    }
}
