package spacegame.controller;

import spacegame.Settings;
import spacegame.model.Model;

import javax.swing.*;

/**
 * @author Václav Blažej
 */
public class Controller {

    private final Model model;
    private final Settings settings;
    private int simulationDelay = 150;
    private Timer timer;
    private int tickNumber;

    public Controller(Model model, Settings settings) {
        this.model = model;
        this.settings = settings;
        this.timer = new Timer(simulationDelay, e -> tick());
    }

    private void tick() {
        simulateMovement();
    }

    private void simulateMovement() {
        tickNumber++;
    }
}
