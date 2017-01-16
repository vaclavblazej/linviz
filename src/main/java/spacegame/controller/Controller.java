package spacegame.controller;

import spacegame.Settings;
import spacegame.model.Model;
import spacegame.model.things.BaseShape;

import javax.swing.*;

/**
 * @author Václav Blažej
 */
public class Controller {

    private final Model model;
    private final Settings settings;
    private int simulationDelay = 20;
    private Timer timer;
    private int tickNumber;

    public Controller(Model model, Settings settings) {
        this.model = model;
        this.settings = settings;
        this.timer = new Timer(simulationDelay, e -> tick());
        this.timer.start();
    }

    private void tick() {
        simulateMovement();
    }

    private void simulateMovement() {
        tickNumber++;
        for (BaseShape shape : model.getShapes()) {
            shape.rotation += 0.01;
        }
    }
}
