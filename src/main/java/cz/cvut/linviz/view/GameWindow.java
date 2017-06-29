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

    public View view;

    public GameWindow(Model model, Controller controller, Settings settings) {
        super("linviz");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(new BorderLayout());
        setFocusable(true);
        setPreferredSize(new Dimension(800,600));

        view = new View(model, controller, settings);
        add(view);
        pack();

        Input input = new Input(view, model, settings);
        this.addKeyListener(input);
        this.addMouseListener(input);
        view.addKeyListener(input);
        view.addMouseListener(input);
        view.addMouseWheelListener(input);
        view.addMouseMotionListener(input);
    }
}
