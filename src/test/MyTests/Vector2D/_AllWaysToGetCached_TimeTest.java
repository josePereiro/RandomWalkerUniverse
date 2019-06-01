package test.MyTests.Vector2D;

import Core.Basics.Vector2D.Vector2DCache;
import test.MyTests.TimeTester;

public class _AllWaysToGetCached_TimeTest {

    static TimeTester timeTester;


    public static void main(String[] args) {

        timeTester = new TimeTester();

        Object[] objects = new Object[1];

        final Vector2DCache cache = new Vector2DCache(2, 2);

        timeTester.addTest(new TimeTester.Test("cache.getPositive(1,1)") {
            @Override
            public void runTest() {
                objects[0] = cache.getPositive(1,1);
            }
        });

        timeTester.addTest(new TimeTester.Test("cache.get(-1,1)") {
            @Override
            public void runTest() {
                objects[0] = cache.get(-1,1);
            }
        });

        timeTester.addTest(new TimeTester.Test("cache.getAndCheck(-1,1)") {
            @Override
            public void runTest() {
                objects[0] = cache.getAndCheck(-1,1);
            }
        });

        timeTester.runTests(2000,1000000,false,true);


    }

}