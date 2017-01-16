package spacegame.model.basics;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Václav Blažej
 */
public final class Polygon {

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
