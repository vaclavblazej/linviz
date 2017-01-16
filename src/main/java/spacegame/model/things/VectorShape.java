package spacegame.model.things;

import spacegame.model.basics.Point;
import spacegame.model.basics.Polygon;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Václav Blažej
 */
public class VectorShape extends BaseShape {

    private final List<Polygon> polygons;

    public VectorShape(Point<Double> position, Double rotation) {
        super(position, rotation);
        polygons = new ArrayList<>();
    }

    public List<Polygon> getPolygons() {
        return polygons;
    }
}
