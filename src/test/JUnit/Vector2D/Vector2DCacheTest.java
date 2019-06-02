package test.JUnit.Vector2D;

import Core.Basics.Vector2D.Vector2D;
import Core.Basics.Vector2D.Vector2DCache;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import test.JUnit.TestSupport.Support;

import java.awt.*;
import java.util.Random;


public class Vector2DCacheTest {

    private static final int DEFAULT_W = 25;
    private static final int DEFAULT_H = 50;
    private static final int REPEAT = 50;
    private final Object[] temp = new Object[1];
    private Vector2DCache cache;
    private Vector2D v2d;
    private int x, y;
    private Random r = new Random();
    private Support.WallTimeTester timeTester;

    @BeforeEach
    void setUp() {
        x = r.nextInt(2 * DEFAULT_W - 1) - (DEFAULT_W - 1);
        y = r.nextInt(2 * DEFAULT_H - 1) - (DEFAULT_H - 1);
        cache = new Vector2DCache(DEFAULT_W, DEFAULT_H);
    }

    @RepeatedTest(REPEAT)
    @DisplayName("cache.get returns a vector of the proper coordinates")
    void get_valueTest() {
        v2d = cache.get(x, y);
        Assertions.assertTrue(v2d.x == x && v2d.y == y);
    }

    @RepeatedTest(REPEAT)
    @DisplayName("cache.getPositive returns a vector of the proper coordinates")
    void getPositive_valueTest() {
        v2d = cache.getPositive(Math.abs(x), Math.abs(y));
        Assertions.assertTrue(v2d.x == Math.abs(x) && v2d.y == Math.abs(y));
    }

    @RepeatedTest(REPEAT)
    @DisplayName("cache.getAndCheck returns a vector of the proper coordinates")
    void getAndCheck_valueTest() {
        v2d = cache.getAndCheck(x, y);
        Assertions.assertTrue(v2d.x == x && v2d.y == y);
    }

    @Test
    @DisplayName("cache.getAndCheck most throw an Exception if indexes are wrong")
    void getAndCheck_ThrowException() {
        Assertions.assertAll(
                () -> Assertions.assertThrows(IndexOutOfBoundsException.class,
                        () -> cache.getAndCheck(DEFAULT_W, y)),
                () -> Assertions.assertThrows(IndexOutOfBoundsException.class,
                        () -> cache.getAndCheck(x, DEFAULT_H)),
                () -> Assertions.assertThrows(IndexOutOfBoundsException.class,
                        () -> cache.getAndCheck(-DEFAULT_W, y)),
                () -> Assertions.assertThrows(IndexOutOfBoundsException.class,
                        () -> cache.getAndCheck(x, -DEFAULT_H))
        );
    }

    @Test
    @DisplayName("Caching must take approximately (tol = 30%) the same time as creating a new Point")
    void cachingVsNew_timeTest() throws Throwable {
        timeTester = new Support.WallTimeTester("CachingVsNew Time Test");

        Executable getPositiveTest = timeTester.addTest("cache.getPositive(1,1)",
                () -> temp[0] = cache.getPositive(1, 1)
        );

        Executable getTest = timeTester.addTest("cache.get(-1,1)",
                () -> temp[0] = cache.get(-1, 1)

        );

        Executable getAndCheckTest = timeTester.addTest("cache.getAndCheck(-1,1)",
                () -> temp[0] = cache.getAndCheck(-1, 1)

        );

        Executable newPointTest = timeTester.addTest("new Point(1,1)",
                () -> temp[0] = new Point(1, 1)

        );

        timeTester.runTests(1000, 1000000);
        timeTester.printResults();

        double getMean = timeTester.getRoundMeanTime(getTest);
        double getPositiveMean = timeTester.getRoundMeanTime(getPositiveTest);
        double getAndCheckMean = timeTester.getRoundMeanTime(getAndCheckTest);
        double newPointMean = timeTester.getRoundMeanTime(newPointTest);
        double tol = newPointMean * 0.3;
        Assertions.assertTrue(
                Support.isApprox(getMean, newPointMean, tol) &&
                        Support.isApprox(getAndCheckMean, newPointMean, tol) &&
                        Support.isApprox(getPositiveMean, newPointMean, tol)
        );


    }

    @Test
    @DisplayName("Caching mg must be faster than compute it")
    void cachingMgVsComputing() throws Throwable {
        timeTester = new Support.WallTimeTester("CachingMgVsComputing Time test");

        Executable computedTest = timeTester.addTest("(float) Point.distance(0, 0, x, y)",
                () -> temp[0] = (float) Point.distance(0, 0, x, y)
        );

        Executable getTest = timeTester.addTest("cache.get(x,y).mg",
                () -> temp[0] = cache.get(x, y).mg
        );

        Executable getAndCheckTest =timeTester.addTest("cache.getAndCheck(x,y).mg",
                () -> temp[0] = cache.getAndCheck(x, y).mg
        );

        timeTester.runTests(1000, 1000000, false, true);

        timeTester.printResults();
        Assertions.assertAll(
                () -> Assertions.assertTrue(timeTester.getRoundMeanTime(getTest) <
                        timeTester.getRoundMeanTime(computedTest)),
                () -> Assertions.assertTrue(timeTester.getRoundMeanTime(getAndCheckTest) <
                        timeTester.getRoundMeanTime(computedTest))
        );

    }

}
