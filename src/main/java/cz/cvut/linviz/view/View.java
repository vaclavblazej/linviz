package cz.cvut.linviz.view;

import cz.cvut.linviz.Meta;
import cz.cvut.linviz.Settings;
import cz.cvut.linviz.controller.Controller;
import cz.cvut.linviz.model.Model;
import cz.cvut.linviz.model.basics.Point;
import cz.cvut.linviz.model.things.BaseShape;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Václav Blažej
 */
public class View extends JPanel implements ActionListener {

    private final Timer timer;
    private final Model model;
    private final Controller controller;
    private final Settings settings;
    private final cz.cvut.linviz.view.thingview.Painter painter;
    private final AffineTransform defaultTransform = new AffineTransform();
    private final HashMap<Class, Function<String, Object>> conversionMap = new HashMap<>();
    private int fps, fpscnt, targetFps = 20;
    private int frame;
    private int border = 40;
    private double zoomSpeed = 150;
    private Point<Double> viewCorner;
    private boolean showCmdline = false;
    private long tick = 0;
    private LinkedList<CommandLog> commandLog = new LinkedList<>();
    private StringBuilder cmdInput = new StringBuilder();
    private TerminalCommands terminalCommands;

    public View(Model model, Controller controller, Settings settings) {
        super(true);
        this.model = model;
        this.controller = controller;
        this.settings = settings;
        this.setFocusable(true);
        this.timer = new Timer(1000 / targetFps, this);
        fpscnt = 0;
        frame = 0;
        new Timer(500, e -> {
            fps = 2 * fpscnt;
            fpscnt = 0;
        }).start();
        this.painter = new cz.cvut.linviz.view.thingview.Painter(model, settings);
        SwingUtilities.invokeLater(timer::start);
        viewCorner = new Point<>(0d, 0d);
        conversionMap.put(Integer.class, Integer::valueOf);
        conversionMap.put(Double.class, Double::valueOf);
        conversionMap.put(String.class, o -> o);
        terminalCommands = new TerminalCommands(model, controller, this, settings);
    }

    public Model getModel() {
        return model;
    }

    @Override
    public void paint(Graphics gg) {
        super.paint(gg);
        final Graphics2D g = (Graphics2D) gg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Set anti-alias!
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); // Set anti-alias for text
        gg.setColor(Color.BLACK);
        gg.fillRect(0, 0, getWidth(), getHeight());
        final AffineTransform transform = settings.getViewTransform();
        painter.paint(gg, this, transform);
        g.setColor(Color.GREEN);
        g.setTransform(defaultTransform);
        if (settings.isShowInfo()) {
            int y = 20;
            g.drawString("POSITION " + transform, 10, y);
            y += 20;
            g.drawString("FPS " + fps, 10, y);
            y += 20;
            g.drawString("FRAME ( " + (frame + 1) + " / " + model.size() + " )", 10, y);
        }
        if (showCmdline) {
            int padding = 10;
            int textPadding = 10;
            int size = 500;
            int tickSpeed = 30;
            g.setColor(Color.black);
            g.fillRect(padding, padding, size, size);
            g.setColor(Color.green);
            g.drawRect(padding, padding, size, size);
            int row = 1;
            final Iterator<CommandLog> iterator = commandLog.iterator();
            for (int i = 0; i < 16 && iterator.hasNext(); i++) {
                CommandLog log = iterator.next();
                g.setColor(log.color);
                g.drawString(log.text, padding + textPadding, size - 20 * row);
                row++;
            }
            g.drawString(cmdInput.toString(), padding + textPadding, size);
            String message = cmdInput.toString();
            Font defaultFont = new Font("Helvetica", Font.PLAIN, 12);
            FontMetrics fontMetrics = g.getFontMetrics(defaultFont);
            int textWidth = fontMetrics.stringWidth(message);
            int textHeight = fontMetrics.getHeight();
            if (tick % tickSpeed < tickSpeed / 2) {
                final int x = padding + textPadding + textWidth;
                g.drawLine(x, size + 2, x, size - textHeight + 2);
            }
        }
        fpscnt++;
        tick++;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public void nextState() {
        this.frame = Math.min(model.size() - 1, frame + 1);
    }

    public void prevState() {
        this.frame = Math.max(0, frame - 1);
    }

    public void toggleCommandline() {
        showCmdline = !showCmdline;
    }

    public void sendCmdInput(char c) {
        if (c == '\n') {
            acceptCmdInput();
        } else if (c == '\b') {
            if (cmdInput.length() > 0) {
                cmdInput.deleteCharAt(cmdInput.length() - 1);
            }
        } else {
            cmdInput.append(c);
        }
    }

    public void acceptCmdInput() {
        String input = cmdInput.toString();
        cmdInput = new StringBuilder();
        final String[] split = input.split(" ");
        String command = split[0];
        final List<String> arguments = new ArrayList<>();
        for (int i = 1; i < split.length; i++) {
            arguments.add(split[i]);
        }
        final String result = runTerminalCommand(command, arguments);
        commandLog.addFirst(new CommandLog(Color.lightGray, input));
        if (!result.isEmpty()) {
            commandLog.addFirst(new CommandLog(Color.green, result));
        }
    }

    public String runTerminalCommand(String command, List<String> arguments) {
        final List<Method> annotatedMethods = Meta.getAnnotatedMethods(TerminalCommands.class, Command.class);
        main:
        for (Method method : annotatedMethods) {
            if (Objects.equals(method.getName(), command) && arguments.size() == method.getParameterCount()) {
                final Class<?>[] parameterTypes = method.getParameterTypes();
                final ArrayList<Object> convertedArguments = new ArrayList<>();
                for (int i = 0; i < parameterTypes.length; i++) {
                    final Class<?> type = parameterTypes[i];
                    final String argument = arguments.get(i);
                    if (conversionMap.containsKey(type)) {
                        final Function<String, Object> conversionFunction = conversionMap.get(type);
                        try {
                            Object convertedArgument = conversionFunction.apply(argument);
                            convertedArguments.add(convertedArgument);
                        } catch (Exception ex) {
                            System.out.println("tried to convert " + argument + " to " + type.getName());
                            continue main;
                        }
                    }
                }
                try {
                    return (String) method.invoke(terminalCommands, convertedArguments.toArray());
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    public int getFrame() {
        return frame;
    }

    public void center() {
        final Point<Double> mx = new Point<>(Double.MIN_VALUE, Double.MIN_VALUE);
        final Point<Double> mn = new Point<>(Double.MAX_VALUE, Double.MAX_VALUE);
        final java.util.List<BaseShape> shapes = model.getShapes(frame);
        final List<Point<Double>> points = shapes.stream().map(BaseShape::getPosition).collect(Collectors.toList());
        final List<Point<Double>> check = new ArrayList<>();
        for (Point<Double> point : points) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i * i + j * j == 2) {
                        check.add(new Point<>(point.x + i * border, point.y + j * border));
                    }
                }
            }
        }
        for (Point<Double> pt : check) {
            mx.x = Math.max(mx.x, pt.x);
            mx.y = Math.max(mx.y, pt.y);
            mn.x = Math.min(mn.x, pt.x);
            mn.y = Math.min(mn.y, pt.y);
        }
        setView(mn, mx);
    }

    public void setView(Point<Double> mn, Point<Double> mx) {
        final AffineTransform transform = settings.getBaseTransform();
        Point<Integer> size = new Point<>((int) (mx.x - mn.x), -(int) (mx.y - mn.y));
        transform.scale(getWidth() / (double) size.x, getHeight() / ((double) size.y));
        transform.translate(-mn.x, -mn.y);
        settings.setViewTransform(transform);
        viewCorner.x = mn.x;
        viewCorner.y = mn.y;
    }

    public void moveView(Point<Double> move) {
        final AffineTransform transform = settings.getViewTransform();
        transform.translate(move.x, move.y);
        viewCorner.x -= move.x;
        viewCorner.y -= move.y;
    }

    public void zoomView(double value) {
        zoomView(value, new Point<>(getWidth() / 2d, getHeight() / 2d));
    }

    public void zoomView(double value, Point<Double> point) {
        final Point<Double> mn = new Point<>(viewCorner);
        final Point<Double> mx = positionInView(new Point<>((double) getWidth(), (double) getHeight()));
        final AffineTransform transform = settings.getViewTransform();
        final Point<Double> ratio = new Point<>(point.x / getWidth(), point.y / getHeight());
        final Point<Double> invert = new Point<>(1 - ratio.x, 1 - ratio.y);
        final double scaleX = transform.getScaleX();
        final double scaleY = -transform.getScaleY();
        final double sign = Math.signum(1 - value);
        final double sum = getWidth() + getHeight();
        final double zoomX = zoomSpeed*getWidth()/sum;
        final double zoomY = zoomSpeed*getHeight()/sum;
        mn.x -= zoomX * ratio.x * sign / scaleX;
        mx.x += zoomX * invert.x * sign / scaleX;
        mn.y += zoomY * ratio.y * sign / scaleY;
        mx.y -= zoomY * invert.y * sign / scaleY;
        setView(mn, mx);
    }

    public Point<Double> positionInView(Point<Double> point) {
        final AffineTransform viewTransform = settings.getViewTransform();
        final Point<Double> res = new Point<>(0., 0.);
        res.x = (point.x - viewTransform.getTranslateX()) / viewTransform.getScaleX();
        res.y = (point.y - viewTransform.getTranslateY()) / viewTransform.getScaleY();
        return res;
    }

    public boolean isShowCmdline() {
        return showCmdline;
    }

    private class CommandLog {
        Color color;
        String text;

        public CommandLog(Color color, String text) {
            this.color = color;
            this.text = text;
        }
    }
}
