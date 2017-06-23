package cz.cvut.linviz.model.things;

import cz.cvut.linviz.model.basics.Point;

/**
 * @author Václav Blažej
 */
public class BaseShape {

    public final Point<Double> position;
    public final Point<Double> velocity;
    public Double rotation;
    public Double angularVelocity;

    public BaseShape(Point<Double> position, Double rotation) {
        this(position, rotation, new Point<>(0., 0.), 0.);
    }

    public BaseShape(Point<Double> position, Double rotation, Point<Double> velocity, Double angularVelocity) {
        this.position = position;
        this.rotation = rotation;
        this.velocity = velocity;
        this.angularVelocity = angularVelocity;
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
