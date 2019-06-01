//package test.MyTests.RandomnessHandler;
//
//import Core.Basics.Collections.CircularIntSet;
//import Core.Basics.RandomnessHandler.RandomnessHandler;
//import test.JUnit.WallTimeTester;
//
//import java.util.Random;
//
//public class _BufferingRandomNumbers_Vs_RandomClass_TimeTest {
//
//    static WallTimeTester timeTester;
//    static Random r = new Random();
//
//
//    public static void main(String[] args) {
//
//        timeTester = new WallTimeTester();
//
//        int[] randomInt = new int[1];
//
//        RandomnessHandler.initRandomBuffer(1000000, 1000);
//        CircularIntSet buffer = RandomnessHandler.randomBuffer;
//
//        timeTester.addTest(new WallTimeTester.Test("buffer.readAndMoveForward();") {
//            @Override
//            public void runTest() {
//                randomInt[0] = buffer.readAndMoveForward();
//            }
//        });
//
//        timeTester.addTest(new WallTimeTester.Test("r.nextInt(1000)") {
//            @Override
//            public void runTest() {
//                randomInt[0] = r.nextInt(1000);
//            }
//        });
//
//        timeTester.runTests(1000,1000000,true,true);
//
//
//    }
//
//}