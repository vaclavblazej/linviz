package spacegame.model.things;

import spacegame.model.basics.Point;

/**
 * @author Václav Blažej
 */
public class BaseShape {

    public final Point<Double> position;
    public Double rotation;

    public BaseShape(Point<Double> position, Double rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public BaseShape() {
        this(new Point<>(0.0, 0.0), 0.0);
    }

    public Point<Double> getPosition() {
        return position;
    }

    public Double getRotation() {
        return rotation;
    }
}
