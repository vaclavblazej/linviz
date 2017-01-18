package spacegame.model.things;

import spacegame.model.basics.Circle;
import spacegame.model.basics.Point;
import spacegame.model.basics.Polygon;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Václav Blažej
 */
public class VectorShape extends BaseShape {

    private final List<Polygon> polygons;
    private final List<Circle> circles;

    public VectorShape(Point<Double> position, Double rotation) {
        super(position, rotation);
        polygons = new ArrayList<>();
        circles = new ArrayList<>();
    }

    public List<Polygon> getPolygons() {
        return polygons;
    }

    public void addPolygon(Polygon polygon) {
        polygons.add(polygon);
    }

    public List<Circle> getCircles() {
        return circles;
    }

    public void addCircle(Circle circle) {
        circles.add(circle);
    }
}
