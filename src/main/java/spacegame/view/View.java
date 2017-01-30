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
    private int fps, fpscnt;
    private int frame;

    public View(Model model, Controller controllerArg, Settings settings) {
        super(true);
        this.model = model;
        this.controller = controllerArg;
        this.settings = settings;
        this.setFocusable(true);
        this.timer = new Timer(1000 / 8, this);
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
        painter.paint(gg, this, settings.getViewTransform());
        g.setColor(Color.GREEN);
        g.setTransform(defaultTransform);
        if (settings.isShowInfo()) {
            g.drawString("FPS " + fps, 10, 20);
            g.drawString("FRAME ( " + (frame + 1) + " / " + model.size() + " )", 10, 40);
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
}
