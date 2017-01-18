package spacegame.model.basics;

/**
 * @author Václav Blažej
 */
public final class Circle {

    private final Point<Double> point;

    public Circle(double x, double y) {
        this.point = new Point<>(x, y);
    }

    public Point<Double> getPoint() {
        return point;
    }
}
