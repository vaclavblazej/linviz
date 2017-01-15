package spacegame.view.component;

import javax.swing.*;

/**
 * @author Václav Blažej
 */
public class CustomSlider extends JSlider {

    public CustomSlider(String text, int min, int max, int value) {
        super(min, max, value);
        this.setName(text);
    }
}
