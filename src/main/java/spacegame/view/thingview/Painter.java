package spacegame.view.thingview;

import spacegame.Meta;
import spacegame.model.Model;
import spacegame.model.basics.Ellipse;
import spacegame.model.basics.Point;
import spacegame.model.basics.Polygon;
import spacegame.model.things.*;
import spacegame.view.View;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Václav Blažej
 */
public class Painter {

    // for precision while painting (java cannot output non-integer values into graphics)
    public static final int SCALING = 100;
    private Model model;
    private Map<String, Method> drawMethods = new HashMap<>();

    public Painter(Model model) {
        this.model = model;
        final List<Method> annotatedMethods = Meta.getAnnotatedMethods(Painter.class, Drawer.class);
        for (Method method : annotatedMethods) {
            final String name = method.getName();
            final String draw = "draw";
            if (name.startsWith(draw)) {
                drawMethods.put(name.substring(draw.length()), method);
            }
        }
    }

    public void paint(Graphics gg, View view, AffineTransform transform) {
        Graphics2D g = (Graphics2D) gg;
        if (view.getFrame() > 0) {
            final java.util.List<BaseShape> shapes = model.getShapes(view.getFrame() - 1);
            g.setColor(new Color(0.2f, 1f, 0.4f, 0.4f));
            draw(g, view, transform, shapes);
        }
        {
            final java.util.List<BaseShape> shapes = model.getShapes(view.getFrame());
            g.setColor(new Color(1f, 1f, 1f, 0.8f));
            draw(g, view, transform, shapes);
        }
    }

    private void draw(Graphics2D g, View view, AffineTransform transform, final java.util.List<BaseShape> shapes) {
        for (BaseShape shape : shapes) {
            g.setTransform(transform);
            addTrans(g, shape);
            Class<? extends BaseShape> aClass = shape.getClass();
            String className = aClass.getSimpleName();
            if (drawMethods.containsKey(className)) {
                try {
                    drawMethods.get(className).invoke(this, g, shape);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    System.out.println("Failed to invoke draw method for class " + className);
                    e.printStackTrace();
                }
            } else {
                System.out.println("Draw method for this class is not implemented: " + aClass);
            }
        }
    }

    @Drawer
    public void drawRectangle(Graphics2D g, spacegame.model.things.Rectangle rectangle) {
        drawVectorShape(g, rectangle);
    }

    @Drawer
    public void drawEllipse(Graphics2D g, Ellipse ellipse) {
        fillOval(g, -ellipse.width / 2, -ellipse.height / 2,
                ellipse.width, ellipse.height);
        final double dotSize = 10;
        g.setColor(Color.RED);
        g.fillOval((int) -dotSize / 2, (int) -dotSize / 2, (int) dotSize, (int) dotSize);
    }

    @Drawer
    public void drawPolygon(Graphics2D g, Polygon polygon) {
        fillPolygon(g, polygon.getPoints());
    }

    @Drawer
    public void drawVectorShape(Graphics2D g, VectorShape vectorShape) {
        final java.util.List<spacegame.model.basics.Polygon> polygons = vectorShape.getPolygons();
        for (Polygon polygon : polygons) {
            drawPolygon(g, polygon);
        }
    }

    private void addTrans(Graphics2D g, BaseShape b) {
        final AffineTransform tx = new AffineTransform();
        final Point<Double> position = b.getPosition();
        tx.translate(SCALING * position.x, SCALING * position.y);
        tx.rotate(b.rotation);
        g.transform(tx);
    }

    private void fillPolygon(Graphics2D g, List<Point<Double>> points) {
        final java.awt.Polygon p = new java.awt.Polygon();
        for (Point<Double> point : points) {
            p.addPoint((int) (SCALING * point.x), (int) (SCALING * point.y));
        }
        g.fillPolygon(p);
    }

    private void fillOval(Graphics2D g, double x, double y, double width, double height) {
        g.fillOval((int) (SCALING * x), (int) (SCALING * y), (int) (SCALING * width), (int) (SCALING * height));
    }

    private void drawOval(Graphics2D g, double x, double y, double width, double height) {
        g.drawOval((int) (SCALING * x), (int) (SCALING * y), (int) (SCALING * width), (int) (SCALING * height));
    }

    // annotate any method which name-matches drawn shape
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    private @interface Drawer {
    }
}
