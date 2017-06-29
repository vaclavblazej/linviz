package cz.cvut.linviz.view;

import cz.cvut.linviz.Settings;
import cz.cvut.linviz.controller.Controller;
import cz.cvut.linviz.model.Model;
import cz.cvut.linviz.controller.Input;

import javax.swing.*;
import java.awt.*;

/**
 * @author Václav Blažej
 */
public class GameWindow extends JFrame {

    public GameWindow(Model model, Controller controller, Settings settings) {
        super("linviz");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(new BorderLayout());
        setFocusable(true);
        setPreferredSize(new Dimension(800,600));

        View gamePlane = new View(model, controller, settings);
        add(gamePlane);
        pack();

        Input input = new Input(gamePlane, model, settings);
        this.addKeyListener(input);
        this.addMouseListener(input);
        gamePlane.addKeyListener(input);
        gamePlane.addMouseListener(input);
        gamePlane.addMouseWheelListener(input);
        gamePlane.addMouseMotionListener(input);
    }
}
