package cz.cvut.linviz.view.thingview;

import cz.cvut.linviz.Meta;
import cz.cvut.linviz.Settings;
import cz.cvut.linviz.model.Model;
import cz.cvut.linviz.model.basics.Ellipse;
import cz.cvut.linviz.model.basics.Line;
import cz.cvut.linviz.model.basics.Point;
import cz.cvut.linviz.model.basics.Polygon;
import cz.cvut.linviz.model.things.BaseShape;
import cz.cvut.linviz.model.things.VectorShape;
import cz.cvut.linviz.view.View;

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
    public static final int SCALING = 1000;
    private Model model;
    private Settings settings;
    private Map<String, Method> drawMethods = new HashMap<>();

    public Painter(Model model, Settings settings) {
        this.model = model;
        this.settings = settings;
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
        final List<BaseShape> shapes = model.getShapes(view.getFrame(), view.getSubframe());
        draw(g, view, transform, shapes);
    }

    private void draw(Graphics2D g, View view, AffineTransform transform, final List<BaseShape> shapes) {
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
    public void drawRectangle(Graphics2D g, cz.cvut.linviz.model.things.Rectangle rectangle) {
        drawVectorShape(g, rectangle);
    }

    @Drawer
    public void drawEllipse(Graphics2D g, Ellipse ellipse) {
        g.setColor(new Color(1f, 1f, 1f, 0.6f));
        fillOval(g, -ellipse.width / 2, -ellipse.height / 2, ellipse.width, ellipse.height);
        final double dotSize = settings.dotSize;
        g.setColor(Color.GREEN);
        AffineTransform viewTransform = settings.getViewTransform();
        double scale = viewTransform.getScaleX();
        g.fillOval((int) (-dotSize / 2. / scale), (int) (-dotSize / 2. / scale), (int) (dotSize / scale), (int) (dotSize / scale));
    }

    @Drawer
    public void drawLine(Graphics2D g, Line line) {
        AffineTransform viewTransform = settings.getViewTransform();
        double scaleX = viewTransform.getScaleX();
        Stroke dashed = new BasicStroke((int) (1 + 2. / scaleX), BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{(float) (Math.max(40., (9. / scaleX)))}, 0);
        g.setStroke(dashed);
        g.setColor(Color.RED);
        System.out.println(line.a.x + " a " + line.a.y);
        System.out.println(line.b.x + " b " + line.b.y);
        g.drawLine((int) (SCALING * line.a.x), (int) (SCALING * line.a.y),
                (int) (SCALING * line.b.x), (int) (SCALING * line.b.y));
    }

    @Drawer
    public void drawPolygon(Graphics2D g, Polygon polygon) {
        fillPolygon(g, polygon.getPoints());
    }

    @Drawer
    public void drawVectorShape(Graphics2D g, VectorShape vectorShape) {
        final List<Polygon> polygons = vectorShape.getPolygons();
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
        g.setColor(new Color(0.2f, 0.7f, 1.0f));
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
