package spacegame.view;

import spacegame.Settings;
import spacegame.controller.Controller;
import spacegame.controller.Input;
import spacegame.model.Model;

import javax.swing.*;
import java.awt.*;

/**
 * @author Václav Blažej
 */
public class GameWindow extends JFrame {

    public GameWindow(Model model, Controller controller, Settings settings) {
        super("space-game");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(new BorderLayout());
        setFocusable(true);

        View gamePlane = new View(model, controller, settings);
        add(gamePlane);
        pack();

        Input input = new Input(gamePlane, model, settings);
        this.addKeyListener(input);
        this.addMouseListener(input);
        gamePlane.addKeyListener(input);
        gamePlane.addMouseListener(input);
        gamePlane.addMouseWheelListener(input);
    }
}
