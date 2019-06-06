package test.JUnit.Vector2D;

import Core.World.Vector2D;
import Core.World.Vector2DCache;
import Core.World.World;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.util.Random;

public class Vector2DTest {

    private static int x, y;
    private static final int RBOUND = 25;
    private static final int REPEAT = 50;
    private static Vector2DCache cache;

    @BeforeAll
    static void beforeAll() {
        World.WorldFactory factory = new World.WorldFactory(RBOUND, RBOUND);
        World world = factory.createNewWorld();
        cache = world.getVector2DCache();
    }

    @BeforeEach
    void setUp() {
        Random r = new Random();
        x = r.nextInt(RBOUND);
        y = r.nextInt(RBOUND);
    }

    @RepeatedTest(REPEAT)
    @DisplayName("comparing values of Vector2D.mg with computed mg")
    void vectorMg_valueTest() {
        Assertions.assertTrue(cache.get(x, y).mg ==
                (float) new Point(x, y).distance(0, 0));
    }

}
