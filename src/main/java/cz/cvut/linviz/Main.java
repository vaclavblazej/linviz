package cz.cvut.linviz;

import cz.cvut.linviz.controller.Controller;
import cz.cvut.linviz.model.Model;
import cz.cvut.linviz.view.GameWindow;
import cz.cvut.linviz.controller.StdInput;

/**
 * @author Václav Blažej
 */
public class Main {

    public static void main(String[] args) {
        Settings settings = new Settings();
        Model model = new Model(settings);
        Controller controller = new Controller(model, settings);
        new GameWindow(model, controller, settings);
        StdInput.loadInput(model, System.in);
    }
}
