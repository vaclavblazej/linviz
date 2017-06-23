package cz.cvut.linviz.model.basics;

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

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
