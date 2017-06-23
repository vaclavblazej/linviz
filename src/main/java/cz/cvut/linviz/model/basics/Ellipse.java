package cz.cvut.linviz.model.basics;

import cz.cvut.linviz.model.things.BaseShape;

/**
 * @author Václav Blažej
 */
public final class Ellipse extends BaseShape {

    public Double width;
    public Double height;

    public Ellipse(Point<Double> position, Double rotation, Double width, Double height) {
        super(position, rotation);
        this.width = width;
        this.height = height;
    }
}
