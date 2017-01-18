package spacegame.view.thingview;

import spacegame.model.Model;
import spacegame.model.basics.Point;
import spacegame.model.basics.Polygon;
import spacegame.model.things.BaseShape;
import spacegame.model.things.VectorShape;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;

import static spacegame.Common.toInt;

/**
 * @author Václav Blažej
 */
public class Painter {

    private Model model;

    public Painter(Model model) {
        this.model = model;
    }

    public void paint(Graphics gg, AffineTransform view) {
        Graphics2D g = (Graphics2D) gg;
        final java.util.List<BaseShape> shapes = model.getShapes();
        for (BaseShape shape : shapes) {
            setTrans(g, shape);
            if (shape instanceof VectorShape) {
                drawVectorShape(g, (VectorShape) shape);
            } else {
                System.out.println("Draw method for this class is not implemented: " + shape.getClass());
            }
            g.setTransform(view);
        }
    }

    private void drawVectorShape(Graphics2D g, VectorShape vectorShape) {
        final java.util.List<spacegame.model.basics.Polygon> polygons = vectorShape.getPolygons();
        g.setColor(Color.WHITE);
        for (Polygon polygon : polygons) {
            final List<Point<Double>> points = polygon.getPoints();
            final int size = points.size();
            for (int i = 0; i < size; i++) {
                drawLine(g, points.get(i), points.get((i + 1) % size));
            }
        }
    }

    private void setTrans(Graphics2D g, BaseShape b) {
        final AffineTransform tx = new AffineTransform();
        final Point<Double> position = b.getPosition();
        tx.translate(position.getX(toInt), position.getY(toInt));
        tx.rotate(b.rotation);
        g.transform(tx);
    }

    private void drawLine(Graphics2D g, Point<Double> a, Point<Double> b) {
        g.drawLine(a.getX(toInt), a.getY(toInt), b.getX(toInt), b.getY(toInt));
    }
}
