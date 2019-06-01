package test.JUnit.Vector2D;

import Core.Basics.Vector2D.Vector2D;
import Core.Basics.Vector2D.Vector2DCache;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.util.Random;


public class Vector2DCacheTest {

    private static final int DEFAULT_W = 25;
    private static final int DEFAULT_H = 50;
    private static final int REPEAT = 50;
    private Vector2DCache cache;
    private Vector2D v;
    private int x, y;

    @BeforeEach
    void setUp() {
        Random r = new Random();
        x = r.nextInt(2 * DEFAULT_W - 1) - (DEFAULT_W - 1);
        y = r.nextInt(2 * DEFAULT_H - 1) - (DEFAULT_H - 1);
        cache = new Vector2DCache(DEFAULT_W, DEFAULT_H);
    }

    @RepeatedTest(REPEAT)
    @DisplayName("cache.get returns a vector of the proper coordinates")
    void get_sameCoordinates() {
        v = cache.get(x, y);
        Assertions.assertTrue(v.x == x && v.y == y);
    }

    @RepeatedTest(REPEAT)
    @DisplayName("cache.getPositive returns a vector of the proper coordinates")
    void getPositive_sameCoordinates() {
        v = cache.getPositive(Math.abs(x), Math.abs(y));
        Assertions.assertTrue(v.x == Math.abs(x) && v.y == Math.abs(y));
    }

    @RepeatedTest(REPEAT)
    @DisplayName("cache.getAndCheck returns a vector of the proper coordinates")
    void getAndCheck_sameCoordinates() {
        v = cache.getAndCheck(x, y);
        Assertions.assertTrue(v.x == x && v.y == y);
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

}
