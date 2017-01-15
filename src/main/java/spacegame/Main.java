package spacegame;

import spacegame.controller.Controller;
import spacegame.model.Model;
import spacegame.view.GameWindow;

/**
 * @author Václav Blažej
 */
public class Main {

    public static void main(String[] args) {
        Settings settings = new Settings();
        Model model = new Model(settings);
        Controller controller = new Controller(model, settings);
        new GameWindow(model, controller, settings);
    }
}
