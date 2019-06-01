package test.MyTests;

import java.util.ArrayList;
import java.util.Random;

public class BoolenTester {

    private final ArrayList<Test> tests;
    private final ArrayList<Long> rights;

    public BoolenTester() {
        tests = new ArrayList<>();
        rights = new ArrayList<>();
    }

    public void runTests(int rounds, int itrsPerTest, boolean verbose) {

        for (int round = 0; round < rounds; round++) {

            if (verbose) System.out.println("Round " + round + " of " + rounds);

            long testRights;
            for (int t = 0; t < tests.size(); t++) {
                Test test = tests.get(t);

                //Running test
                testRights = 0L;
                for (int i = 0; i < itrsPerTest; i++) {
                    if (test.runTest()) testRights++;
                    else test.onSomeThingWrong();
                }

                //Updating
                rights.set(t, rights.get(t) + testRights);

            }

        }

        //Final Info
        if (verbose) System.out.println();
        System.out.println("###########################");
        System.out.println("### TEST RESULTS ##########");
        System.out.println("###########################");
        for (int t = 0; t < tests.size(); t++) {
            System.out.println("Test " + tests.get(t).testName);
            System.out.println("Right Answers " + rights.get(t) + " of " + (rounds * itrsPerTest));
            System.out.println();
        }

    }

    public void reset() {
        for (int t = 0; t < tests.size(); t++) {
            rights.set(t, 0L);
        }
    }

    /**
     * Add a test to be tested. All the test will be executed a given number of time.
     * By Default the order of execution will be random every iteration.
     *
     * @param test
     */
    public void addTest(Test test) {
        tests.add(test);
        rights.add(0L);
    }

    public abstract static class Test {

        String testName;

        public Test(String testName) {
            this.testName = testName;
        }

        /**
         * Override this method and run inside the code that you want to test.
         */
        public abstract boolean runTest();

        /**
         * Override this method with code that you wanna execute each time the test faild.
         */
        public abstract void onSomeThingWrong();
    }

}
