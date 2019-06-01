package test.JUnit.Vector2D;

import Core.Basics.Vector2D.Vector2D;
import Core.Basics.Vector2D.Vector2DCache;
import org.junit.jupiter.api.*;
import test.JUnit.MyTestCalsses.WallTimeTester;

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
    private WallTimeTester timeTester;

    @BeforeEach
    void setUp() {
        x = r.nextInt(2 * DEFAULT_W - 1) - (DEFAULT_W - 1);
        y = r.nextInt(2 * DEFAULT_H - 1) - (DEFAULT_H - 1);
        cache = new Vector2DCache(DEFAULT_W, DEFAULT_H);
    }

    @RepeatedTest(REPEAT)
    @DisplayName("cache.get returns a vector of the proper coordinates")
    void get_sameCoordinates() {
        v2d = cache.get(x, y);
        Assertions.assertTrue(v2d.x == x && v2d.y == y);
    }

    @RepeatedTest(REPEAT)
    @DisplayName("cache.getPositive returns a vector of the proper coordinates")
    void getPositive_sameCoordinates() {
        v2d = cache.getPositive(Math.abs(x), Math.abs(y));
        Assertions.assertTrue(v2d.x == Math.abs(x) && v2d.y == Math.abs(y));
    }

    @RepeatedTest(REPEAT)
    @DisplayName("cache.getAndCheck returns a vector of the proper coordinates")
    void getAndCheck_sameCoordinates() {
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

    @Disabled("cachingVsNew is =hard to test: Just run it to print the results...")
    @Test
    @DisplayName("Caching must take approximately the same time as creating a new Point")
    void cachingVsNew() throws Throwable {
        timeTester = new WallTimeTester("CachingVsNew");

        timeTester.addTest("cache.getPositive(1,1)",
                () -> temp[0] = cache.getPositive(1, 1)
        );

        timeTester.addTest("cache.get(-1,1)",
                () -> temp[0] = cache.get(-1, 1)

        );

        timeTester.addTest("cache.getAndCheck(-1,1)",
                () -> temp[0] = cache.getAndCheck(-1, 1)

        );

        timeTester.addTest("new Point(1,1)",
                () -> temp[0] = new Point(1, 1)

        );

        timeTester.runTests(1000, 1000000);
        timeTester.printResults();

    }

    @Test
    @DisplayName("Caching mg must be faster than computing it")
    void cachingMgVsComputing() throws Throwable {
        timeTester = new WallTimeTester("CachingVsNew");
        timeTester.addTest("(float) Point.distance(0, 0, x, y)",
                () -> temp[0] = (float) Point.distance(0, 0, x, y)
        );

        timeTester.addTest("cache.get(x,y).mg",
                () -> temp[0] = cache.get(x, y).mg
        );

        timeTester.addTest("cache.getAndCheck(x,y).mg",
                () -> temp[0] = cache.getAndCheck(x, y).mg
        );

        timeTester.runTests(1000, 1000000, false, true);

        Assertions.assertAll(
                () -> Assertions.assertTrue(timeTester.getMeanTime("cache.get(x,y).mg") <
                        timeTester.getMeanTime("(float) Point.distance(0, 0, x, y)")),
                () -> Assertions.assertTrue(timeTester.getMeanTime("cache.getAndCheck(x,y).mg") <
                        timeTester.getMeanTime("(float) Point.distance(0, 0, x, y)"))
        );

    }

}
