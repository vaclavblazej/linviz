package spacegame.model.basics;

import java.util.function.Function;

/**
 * @author Václav Blažej
 */
public final class Point<T> {

    public T x, y;

    public Point(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point<T> p) {
        this.x = p.x;
        this.y = p.y;
    }

    public <Q> Q getX(Function<T, Q> function) {
        return function.apply(x);
    }

    public <Q> Q getY(Function<T, Q> function) {
        return function.apply(y);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
