package spacegame.view.component;

import spacegame.model.basics.Point;

/**
 * @author Václav Blažej
 */
public class CViewElement {

    private Point<Integer> position;
    private Point<Integer> size;

    public CViewElement(Point<Integer> position, Point<Integer> size) {
        this.position = position;
        this.size = size;
    }

    public Point<Integer> getPosition() {
        return position;
    }

    public void setPosition(Point<Integer> position) {
        this.position = position;
    }
}
