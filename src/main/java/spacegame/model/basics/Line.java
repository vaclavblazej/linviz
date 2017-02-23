package spacegame.model.basics;

import spacegame.model.things.BaseShape;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Václav Blažej
 */
public final class Line extends BaseShape{

    public final Point<Double> a, b;

    public Line(Point<Double> a, Point<Double> b) {
        this.a = a;
        this.b = b;
    }

}
