package cz.cvut.linviz.model;

import cz.cvut.linviz.Settings;
import cz.cvut.linviz.model.things.BaseShape;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Václav Blažej
 */
public class Model {

    private final Settings settings;
    private final List<Frame> frames;
    private final List<BaseShape> background;

    public Model(Settings settings) {
        this.settings = settings;
        this.frames = new ArrayList<>();
        this.background = new ArrayList<>();
        addFrame();
    }

    public void addBackground(BaseShape shape) {
        background.add(shape);
    }

    public int framesSize() {
        return frames.size();
    }

    public int subframesSize(int i) {
        return frames.get(i).size();
    }

    public void addFrame() {
        frames.add(new Frame());
    }

    public void addSubframe() {
        addSubframe(frames.size() - 1);
    }

    public void addSubframe(int sf) {
        frames.get(sf).addSubframe();
    }

    public void addShape(BaseShape shape) {
        addShape(framesSize() - 1, shape);
    }

    public void addShape(int frame, BaseShape shape) {
        frames.get(frame).add(shape);
    }

    public void addShape(int frame, int subframe, BaseShape shape) {
        frames.get(frame).add(subframe, shape);
    }

    public List<BaseShape> getShapes(int frame, int subframe) {
        final List<BaseShape> shapes = new ArrayList<>();
        shapes.addAll(background);
        shapes.addAll(frames.get(frame).getShapes(subframe));
        return shapes;
    }


    public class Frame {
        private final List<Subframe> subframes;

        public Frame() {
            subframes = new ArrayList<>();
            subframes.add(new Subframe());
        }

        public void add(int i, BaseShape shape) {
            subframes.get(i).add(shape);
        }

        public void add(BaseShape shape) {
            add(subframes.size() - 1, shape);
        }

        public void addSubframe() {
            subframes.add(new Subframe());
        }

        public List<BaseShape> getShapes(int sf) {
            final ArrayList<BaseShape> result = new ArrayList<>();
            for (int i = 0; i <= sf; i++) {
                result.addAll(subframes.get(i).getShapes());
            }
            return result;
        }

        public int size() {
            return subframes.size();
        }
    }

    public class Subframe {
        private final List<BaseShape> shapes;

        public Subframe() {
            shapes = new ArrayList<>();
        }

        public void add(BaseShape shape) {
            shapes.add(shape);
        }

        public List<BaseShape> getShapes() {
            return shapes;
        }
    }
}
