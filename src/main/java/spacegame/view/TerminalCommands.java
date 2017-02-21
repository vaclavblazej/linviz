package spacegame.view;

import spacegame.Settings;
import spacegame.controller.Controller;
import spacegame.model.Model;

/**
 * @author Václav Blažej
 */
public class TerminalCommands {

    private final Model model;
    private final Controller controller;
    private final View view;
    private final Settings settings;

    public TerminalCommands(Model model, Controller controller, View view, Settings settings) {
        this.model = model;
        this.controller = controller;
        this.view = view;
        this.settings = settings;
    }

    @Command
    public String plus(Integer a, Integer b) {
        return Integer.toString(a + b);
    }

    @Command
    public String quit() {
        System.exit(0);
        return "quitting!";
    }
}
