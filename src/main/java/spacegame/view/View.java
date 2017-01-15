package spacegame.view;

import spacegame.Settings;
import spacegame.controller.Controller;
import spacegame.model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Václav Blažej
 */
public class View extends JPanel implements ActionListener {

    private final Timer timer;
    private final Model model;
    private final Controller controller;
    private final Settings settings;
    private Boolean showPoints;

    public View(Model model, Controller controllerArg, Settings settings) {
        this.model = model;
        this.controller = controllerArg;
        this.settings = settings;
        this.showPoints = false;
        this.setPreferredSize(new Dimension(800, 600));
        this.setFocusable(true);
        this.timer = new Timer(100, this);
        SwingUtilities.invokeLater(timer::start);
    }

    public Model getModel() {
        return model;
    }

    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public void setShowPoints(Boolean showPoints) {
        this.showPoints = showPoints;
    }
}
