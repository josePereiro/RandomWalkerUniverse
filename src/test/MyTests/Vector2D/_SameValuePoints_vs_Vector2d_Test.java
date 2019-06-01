package test.MyTests.Vector2D;

import Core.Basics.Vector2D.Vector2D;
import Core.Basics.Vector2D.Vector2DCache;
import test.MyTests.BoolenTester;

import java.awt.*;
import java.util.Random;

public class _SameValuePoints_vs_Vector2d_Test {

    public static void main(String[] args) {

        BoolenTester tester = new BoolenTester();
        Random r = new Random();
        Vector2DCache cache = new Vector2DCache(100, 100);

        tester.addTest(new BoolenTester.Test("v.x == p.x && v.y == p.y && v.mg == (float) p.distance(0,0)") {

            int x;
            int y;
            Vector2D v;
            Point p;

            @Override
            public boolean runTest() {
                x = r.nextInt(199) - 99;
                y = r.nextInt(199) - 99;
                v = cache.get(x, y);
                p = new Point(x,y);
                return v.x == p.x && v.y == p.y && v.mg == (float) p.distance(0,0);
            }

            @Override
            public void onSomeThingWrong() {
                System.out.println("Test Fails!!! x=" + x + " y=" + y);
                System.out.println("Vector2D (" + v.x + "," + v.y + ")" + " mg = " + v.mg);
                System.out.println("Point (" + p.x + "," + p.y + ")" + " mg = " + (float) p.distance(0,0));
                System.out.println();
            }
        });

        tester.runTests(10, 100000, true);

    }

}
