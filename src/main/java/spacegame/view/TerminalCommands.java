package spacegame.view;

/**
 * @author Václav Blažej
 */
public class TerminalCommands {

    @Command
    public String plus(Integer a, Integer b) {
        return Integer.toString(a + b);
    }
}
