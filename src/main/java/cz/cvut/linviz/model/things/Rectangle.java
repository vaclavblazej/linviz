package cz.cvut.linviz.model.things;

import cz.cvut.linviz.model.basics.Point;
import cz.cvut.linviz.model.basics.Polygon;

/**
 * @author Václav Blažej
 */
public class Rectangle extends VectorShape {

    public Rectangle(Point<Double> position, Double rotation, Double size) {
        super(position, rotation);
        final Polygon polygon = new Polygon();
        polygon.addPoint(new Point<>(size, size));
        polygon.addPoint(new Point<>(size, -size));
        polygon.addPoint(new Point<>(-size, -size));
        polygon.addPoint(new Point<>(-size, size));
        addPolygon(polygon);
    }

    @Override
    public String toString() {
        return "Rectangle{} " + super.toString();
    }
}
