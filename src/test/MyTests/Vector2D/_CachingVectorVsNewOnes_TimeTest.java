package test.MyTests.Vector2D;

import Core.Basics.Vector2D.Vector2DCache;
import org.junit.jupiter.api.Test;
import test.JUnit.WallTimeTester;

import java.awt.*;

public class _CachingVectorVsNewOnes_TimeTest {

    static WallTimeTester timeTester;


    public static void main(String[] args) throws Throwable {

        timeTester = new WallTimeTester("_CachingVectorVsNewOnes_TimeTest");

        Object[] objects = new Object[1];

        final Vector2DCache cache = new Vector2DCache(2, 2);

        timeTester.addTest("cache.getPositive(1,1)",
                () -> objects[0] = cache.getPositive(1, 1)

        );

        timeTester.addTest("cache.get(-1,1)",
                () -> objects[0] = cache.get(-1, 1)

        );

        timeTester.addTest("new Point(1,1)",
                () -> objects[0] = new Point(1, 1)

        );

        timeTester.runTests(1000, 10000000, false, true);
        timeTester.printResults();

    }


}