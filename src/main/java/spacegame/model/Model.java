package spacegame.model;

import spacegame.Settings;
import spacegame.model.things.BaseShape;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Václav Blažej
 */
public final class Model {

    private final Settings settings;
    private final List<List<BaseShape>> shapes;

    public Model(Settings settings) {
        this.settings = settings;
        this.shapes = new ArrayList<>();
        this.shapes.add(new ArrayList<>());
    }

    public int size() {
        return shapes.size();
    }

    public void addState() {
        shapes.add(new ArrayList<>());
    }

    public void addShape(BaseShape shape) {
        addShape(size() - 1, shape);
    }

    public void addShape(int state, BaseShape shape) {
        shapes.get(shapes.size() - 1).add(shape);
    }

    public List<BaseShape> getShapes(int state) {
        return shapes.get(state);
    }
}
