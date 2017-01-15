package spacegame.view;

import spacegame.Settings;
import spacegame.controller.Controller;
import spacegame.model.Model;
import spacegame.model.Player;
import spacegame.model.Worm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Patrik Faistaver
 * @author Václav Blažej
 * @author Štěpán Plachý
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
        try {
            Graphics2D g = (Graphics2D) grphcs;
            g.drawImage(model.getImage(), null, this);
            for (Player player : model.getPlayers()) {
                Worm worm = player.getWorm();
                if (worm != null) {
                    Point.Double position = worm.getPosition();
                    if (worm.isPhaseShifted()) {
                        g.setColor(Color.white);
                    } else {
                        g.setColor(player.getColor());
                    }
                    g.drawOval((int) position.x - 1, (int) position.y - 1, 2, 2);
                    if (showPoints) {
                        g.setColor(Color.RED);
                    }
                }
            }
            int pos = 10;
            for (Player player : model.getPlayers()) {
                g.setColor(player.getColor());
                String str = "" + player.getScore();
                g.drawString(str, pos, 10);
                double length = getFontMetrics(getFont()).getStringBounds(str, getGraphics()).getWidth();
                pos += length + 4;
            }
            g.setColor(Color.RED);
        } catch (Exception ignored) {
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public void setShowPoints(Boolean showPoints) {
        this.showPoints = showPoints;
    }
}
