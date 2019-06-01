package test.MyTests.Vector2D;

import Core.Basics.Vector2D.Vector2DCache;
import test.JUnit.WallTimeTester;

import java.awt.*;

public class _CachedMgVsComputingIt_TimeTes {

    static WallTimeTester timeTester;


    public static void main(String[] args) throws Throwable {

        timeTester = new WallTimeTester("_CachedMgVsComputingIt_TimeTes");

        int x = 1;
        int y = 1;
        final long[] accumulator = {0};

        final Vector2DCache cache = new Vector2DCache(10, 10);

        timeTester.addTest("(float) Point.distance(0, 0, x, y)",
                () -> accumulator[0] += (float) Point.distance(0, 0, x, y)
        );

        timeTester.addTest("cache.get(x,y).mg",
                () -> accumulator[0] += cache.get(x, y).mg
        );

        timeTester.addTest("cache.getPositive(x,y).mg",
                () -> accumulator[0] += cache.getPositive(x, y).mg
        );

        timeTester.runTests(1000, 1000000, false, true);
        timeTester.printResults();

    }

}