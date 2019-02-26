package Core.Engine.Shape;

import Core.Engine.Vector.Localizable;
import Core.Engine.Vector.Vector;

import java.util.ArrayList;

/**
 * This class should check the creation of all the shapes of the simulation.
 * Its main propose is to ensure that a shape is created only once.
 */
public class ShapesHandler {

    final private ArrayList<PinnedShape> shapes;
    final private ArrayList<Integer> shapesHashCode;

    public ShapesHandler() {
        shapes = new ArrayList<>();
        shapesHashCode = new ArrayList<>();
    }

    /**
     * returns a shape if it was already created in the simulation,
     * null otherwise.
     *
     * @param vertexes
     * @return
     */
    public PinnedShape resolve(Localizable[] vertexes) {
        int hash = PinnedShape.getHashCode(vertexes);
        for (int h = 0; h < shapesHashCode.size(); h++) {
            if (shapesHashCode.get(h) == hash)
                return shapes.get(h);
        }
        return null;
    }

    /**
     * Add a shape to the handler. This method do not check if the
     * shape is already contained in the handler, so, a previous
     * call of resolve should ensure that the shape is in fact
     * a new one.
     * @param shape
     * @return
     */
    public void addShape(PinnedShape shape) {
        shapes.add(shape);
        shapesHashCode.add(PinnedShape.getHashCode(shape));
    }

}
