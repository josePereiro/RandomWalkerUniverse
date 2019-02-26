package Core.Engine.Tools;

import Core.Engine.Iterations.Iterations;
import Core.Engine.Iterations.OnIterationActionHandler;
import Core.Engine.Vector.Vector;

import java.util.Random;

public class _OverLineTest2 {

    public static void main(String... args) {
        boolean fails = false;
        Vector v1, v2;
        int maxCoordinate = 500;
        Random r = new Random();
        int rx, ry;
        int[] values = new int[]{-1, 1};
        float precision = 0.9F;
        OnIterationActionHandler<Vector[], Float, Boolean> iterationActionHandler =
                new OnIterationActionHandler<Vector[], Float, Boolean>() {
                    @Override
                    public void action(int x, int y) {
                        Vector currentVector = new Vector(x, y);
                        //System.out.println("current: " + currentVector);
                        boolean ok = Tools.isOverLine(currentVector, getExtraOne()[0], getExtraOne()[1], getExtraTwo());
                        if (!ok) {
                            setExtraThree(true);
                            break_ = true;
                        }
                    }
                };
        iterationActionHandler.setExtraThree(fails);

        int c = 0;
        while (!fails) {
            rx = r.nextInt(maxCoordinate) * values[r.nextInt(2)];
            ry = r.nextInt(maxCoordinate) * values[r.nextInt(2)];
            v1 = new Vector(rx, ry);
            rx = r.nextInt(maxCoordinate) * values[r.nextInt(2)];
            ry = r.nextInt(maxCoordinate) * values[r.nextInt(2)];
            v2 = new Vector(rx, ry);

            //Info
            System.out.println("Iterating... " + c);
            System.out.println("v1: " + v1);
            System.out.println("v2: " + v2);
            System.out.println("Precision: " + precision);
            System.out.println();

            iterationActionHandler.setExtraOne(new Vector[]{v1, v2});
            iterationActionHandler.setExtraTwo(precision);
            Iterations.iterateLine(v1.x, v1.y, v2.x, v2.y, iterationActionHandler);
            fails = iterationActionHandler.getExtraThree();
            c++;

            if (precision < 0) break;

        }

        System.out.println("Very bad news!!!");
        System.out.println("Precision: " + precision);
    }

}
