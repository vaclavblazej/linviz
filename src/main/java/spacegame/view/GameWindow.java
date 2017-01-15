package spacegame.view;

import spacegame.Settings;
import spacegame.controller.Controller;
import spacegame.controller.Input;
import spacegame.model.Model;

import javax.swing.*;
import java.awt.*;

/**
 * @author Patrik Faistaver
 * @author Václav Blažej
 * @author Štěpán Plachý
 */
public class GameWindow extends JFrame {

    public GameWindow(Model model, Controller controller, Settings settings) {
        super("Cervi 1.0");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLayout(new FlowLayout(SwingConstants.HORIZONTAL));

        View gamePlane = new View(model, controller, settings);
        add(gamePlane);
        EvolutionView evolutionView = new EvolutionView(gamePlane, model, controller, settings);
        add(evolutionView);
        pack();

        setFocusable(true);

        Input input = new Input(model, settings);
        this.addKeyListener(input);
        gamePlane.addKeyListener(input);
    }
}
