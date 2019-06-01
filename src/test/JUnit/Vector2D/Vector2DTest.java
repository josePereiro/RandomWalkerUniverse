package test.JUnit.Vector2D;

import Core.Basics.Vector2D.Vector2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import java.awt.*;
import java.util.Random;

public class Vector2DTest {

    private int x, y;
    private static final int RBOUND = 932;
    private static final int REPEAT = 50;

    @BeforeEach
    void setUp() {
        Random r = new Random();
        x = r.nextInt(RBOUND);
        y = r.nextInt(RBOUND);
    }

    @RepeatedTest(REPEAT)
    @DisplayName("comparing Vector2D.mg with computed mg")
    void vectorMg(){
        Assertions.assertTrue(new Vector2D(x,y).mg == (float)new Point(x,y).distance(0,0));
    }
}
