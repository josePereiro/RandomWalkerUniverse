package test.MyTests.Vector2D;

import Core.Basics.Vector2D.Vector2DCache;
import test.MyTests.TimeTester;

import java.awt.*;

public class _CachedMgVsComputingIt_TimeTes {

    static TimeTester timeTester;


    public static void main(String[] args) {

        timeTester = new TimeTester();

        int x = 1;
        int y = 1;
        final long[] accumulator = {0};

        final Vector2DCache cache = new Vector2DCache(10, 10);

        timeTester.addTest(new TimeTester.Test("(float)Point.distance(0,0,x,y)") {
            @Override
            public void runTest() {
                accumulator[0] += (float)Point.distance(0,0,x,y);
            }
        });

        timeTester.addTest(new TimeTester.Test("cache.get(x,y).mg") {
            @Override
            public void runTest() {
                accumulator[0] += cache.get(x,y).mg;
            }
        });

        timeTester.addTest(new TimeTester.Test("cache.getPositive(x,y).mg") {
            @Override
            public void runTest() {
                accumulator[0] += cache.getPositive(x,y).mg;
            }
        });

        timeTester.runTests(1000,1000000,true,true);


    }

}