package spacegame.view;

import spacegame.Settings;
import spacegame.controller.Controller;
import spacegame.model.Model;
import spacegame.view.thingview.Painter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

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
    private Boolean showPoints;
    private int fps, fpscnt;

    public View(Model model, Controller controllerArg, Settings settings) {
        super(true);
        this.model = model;
        this.controller = controllerArg;
        this.settings = settings;
        this.showPoints = false;
        this.setFocusable(true);
        this.timer = new Timer(16, this);
        fpscnt = 0;
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
        painter.paint(gg);
        g.setColor(Color.GREEN);
        g.setTransform(defaultTransform);
        g.drawString("FPS " + fps, 10, 20);
        fpscnt++;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public void setShowPoints(Boolean showPoints) {
        this.showPoints = showPoints;
    }
}
