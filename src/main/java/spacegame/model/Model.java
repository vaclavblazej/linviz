package spacegame.model;

import spacegame.Settings;
import spacegame.model.basics.Point;
import spacegame.model.basics.Polygon;
import spacegame.model.things.BaseShape;
import spacegame.model.things.VectorShape;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Václav Blažej
 */
public final class Model {

    private final Settings settings;
    private final List<BaseShape> shapes;

    public Model(Settings settings) {
        this.settings = settings;
        this.shapes = new ArrayList<>();
        final VectorShape shape = new VectorShape(new Point<>(200., 100.), 0.2);
        final List<Polygon> polygons = shape.getPolygons();
        final Polygon e = new Polygon();
        e.addPoint(new Point<>(0., 0.));
        e.addPoint(new Point<>(40., 20.));
        e.addPoint(new Point<>(30., 40.));
        polygons.add(e);
        addShape(shape);
    }

    public void addShape(BaseShape shape) {
        shapes.add(shape);
    }

    public List<BaseShape> getShapes() {
        return shapes;
    }
}
