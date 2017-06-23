package cz.cvut.linviz.model.basics;

import cz.cvut.linviz.model.things.BaseShape;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Václav Blažej
 */
public final class Polygon extends BaseShape {

    private final List<Point<Double>> points;

    public Polygon() {
        this.points = new ArrayList<>();
    }

    public List<Point<Double>> getPoints() {
        return points;
    }

    public void addPoint(Point<Double> point) {
        points.add(point);
    }
}
