package spacegame.controller;

import spacegame.Settings;
import spacegame.model.Model;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Václav Blažej
 */
public class Input implements KeyListener {

    private final Model model;

    public Input(Model model, Settings settings) {
        this.model = model;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public void computeDirection(Integer playerId) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
