package cz.cvut.linviz.model.basics;

import cz.cvut.linviz.model.things.BaseShape;

/**
 * @author Václav Blažej
 */
public final class Circle extends BaseShape {

    private final Point<Double> point;

    public Circle(double x, double y) {
        this.point = new Point<>(x, y);
    }

    public Point<Double> getPoint() {
        return point;
    }
}
