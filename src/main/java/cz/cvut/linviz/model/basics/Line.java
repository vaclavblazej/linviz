package cz.cvut.linviz.model.basics;

import cz.cvut.linviz.model.things.BaseShape;

/**
 * @author Václav Blažej
 */
public final class Line extends BaseShape {

    public final Point<Double> a, b;

    public Line(Point<Double> a, Point<Double> b) {
        this.a = a;
        this.b = b;
    }

}
