package test.JUnit.Vector2D;

import Core.Basics.Vector2D.Vector2DCache;
import com.sun.org.apache.regexp.internal.RE;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Random;

public class Vector2D {

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
    @DisplayName("")
    void vectorMg(){

    }
}
