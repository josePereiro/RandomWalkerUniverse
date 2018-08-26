package Core.Engine.Geometry;

import Core.Engine.Tools.Tools;
import Core.Engine.Vector.Vector;

import java.util.function.ToDoubleBiFunction;

public class Geometry {

    public static void main(String... args) {

    }

    public static Vector getSegmentMiddlePoint(int x1, int y1, int x2, int y2) {
        return new Vector(Math.round((x1 + x2) / 2), Math.round((y1 + y2) / 2));
    }

    public static Vector getMiddleRightPoint(int x1, int y1, int x2, int y2) {
        Vector middle = getSegmentMiddlePoint(x1, y1, x2, y2);

        if (Math.abs(x1 - x2) > Math.abs(y1 - y2)) {
            //move in Y
            if (x1 < x2) {
                middle.y--;
            } else {
                middle.y++;
            }
        } else {
            //move in X
            if (y1 < y2) {
                middle.x++;
            } else {
                middle.x--;
            }
        }

        return middle;
    }

}
