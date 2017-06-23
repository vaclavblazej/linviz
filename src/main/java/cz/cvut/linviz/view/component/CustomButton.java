package cz.cvut.linviz.view.component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

/**
 * @author Václav Blažej
 */
public class CustomButton extends JButton {

    public CustomButton(String text, Consumer<ActionEvent> action) {
        setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action.accept(e);
            }
        });
        setText(text);
    }
}
