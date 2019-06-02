package test.JUnit.Random;

import Core.World.RandomNumbersCache;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import test.JUnit.TestSupport.Support;

import java.util.Random;

public class RandomNumbersCacheTest {

    private static final int REPEAT = 100;
    private static final int BUFFER_LENGTH = 1000000;
    private static RandomNumbersCache randomBuffer;
    private static Random r = new Random();
    private final static float[] temp = new float[1];

    @BeforeEach
    void setUpAll() {
        randomBuffer = new RandomNumbersCache(BUFFER_LENGTH);
    }

    @RepeatedTest(REPEAT)
    @DisplayName("Random numbers between 0 and 1 must have a mean approx to 0.5 (tol = 0.005%)")
    void dataRandomnessTest() {
        Assertions.assertTrue(Support.isApprox(randomBuffer.getMean(), 0.5, 0.005));
    }

    @Test
    @DisplayName("Pick a cached random number must be several times faster than using Random class")
    void pickCachedVsRandomClass() throws Throwable {

        Support.WallTimeTester timeTester = new Support.WallTimeTester("RandomNumbersCache vs Random Time Test");
        int rbound = 1000;

        Executable cacheGetFloatTest = timeTester.addTest("randomBuffer.getNextFloat()",
                () -> temp[0] = randomBuffer.getNextFloat());
        Executable cacheGetIntTest = timeTester.addTest("randomBuffer.getNextInt(rbound)",
                () -> temp[0] = randomBuffer.getNextInt(rbound));
        Executable generatedFloatTest = timeTester.addTest("r.nextFloat()",
                () -> temp[0] = r.nextFloat());
        Executable generatedIntTest = timeTester.addTest("r.nextInt(rbound)",
                () -> temp[0] = r.nextInt(rbound));

        timeTester.runTests(1000, 1000000);

        double cacheGetFloatMeanTime = timeTester.getRoundMeanTime(cacheGetFloatTest);
        double cacheGetIntMeanTime = timeTester.getRoundMeanTime(cacheGetIntTest);
        double generatedFloatMeanTime = timeTester.getRoundMeanTime(generatedFloatTest);
        double generatedIntMeanTime = timeTester.getRoundMeanTime(generatedIntTest);

        timeTester.printResults();
        Assertions.assertTrue( cacheGetFloatMeanTime < generatedFloatMeanTime &&
                cacheGetIntMeanTime < generatedIntMeanTime);

    }

}
