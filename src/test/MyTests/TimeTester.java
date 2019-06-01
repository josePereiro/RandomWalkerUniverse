package test.MyTests;

import java.util.ArrayList;
import java.util.Random;

public class TimeTester {

    private final ArrayList<Test> tests;
    private final ArrayList<Long> totalTimes;
    private final ArrayList<Long> averageTimes;
    private TimeInterval interval;

    public TimeTester() {
        tests = new ArrayList<>();
        totalTimes = new ArrayList<>();
        averageTimes = new ArrayList<>();
    }

    public void runTests(int rounds, int itrsPerTest, boolean verbose, boolean randomize) {
        interval = new TimeInterval();

        for (int round = 0; round < rounds; round++) {

            if (randomize) sortData();
            System.out.println("Round " + round + " of " + rounds);

            long testTime;
            long testAve;
            for (int t = 0; t < tests.size(); t++) {
                Test test = tests.get(t);
                if (verbose) System.out.println("Testing " + test.testName + "  " + itrsPerTest + " times...");

                //Running test
                interval.initInterval();
                for (int i = 0; i < itrsPerTest; i++) {
                    test.runTest();
                }
                testTime = interval.getUncheckedInterval();

                if (verbose) System.out.println("Round Time(ms) " + testTime);

                //Updating time data
                totalTimes.set(t, totalTimes.get(t) + testTime);

                //Ave
                testAve = totalTimes.get(t) / (round + 1);
                if (verbose) System.out.println("Average(ms) " + testAve);

                //Updating time data
                averageTimes.set(t, testAve);
            }

            if (verbose) System.out.println();
        }

        //Final Info
        System.out.println("###########################");
        System.out.println("### TEST RESULTS ##########");
        System.out.println("###########################");
        System.out.println("TotalTest " + ((long)rounds * (long)itrsPerTest));
        for (int t = 0; t < tests.size(); t++) {
            System.out.println("Test " + tests.get(t).testName);
            System.out.println("Total Time(ms) " + totalTimes.get(t));
            System.out.println("Ave Time(ms) " + averageTimes.get(t));
            System.out.println();
        }

    }

    public void reset() {
        for (int t = 0; t < tests.size(); t++) {
            totalTimes.set(t, 0L);
            averageTimes.set(t, 0L);
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
        totalTimes.add(0L);
        averageTimes.add(0L);
    }

    public abstract static class Test {

        String testName;

        public Test(String testName) {
            this.testName = testName;
        }

        /**
         * Override this method and run inside the code that you want to test.
         */
        public abstract void runTest();
    }

    private static class TimeInterval {

        private static final int UNINITIALIZED = -1;
        private static long initTime = UNINITIALIZED;
        private static int intervalCount = UNINITIALIZED;

        /**
         * Set the current time as the beginning of the interval!!!
         */
        public void initInterval() {
            initTime = System.currentTimeMillis();
            intervalCount++;
        }

        public long getInterval() throws UninitializedIntervalException {
            if (initTime == UNINITIALIZED) {
                throw new UninitializedIntervalException("The interval has not an initial time. Use " +
                        "initInterval to set one!!!");
            } else {
                return System.currentTimeMillis() - initTime;
            }
        }

        public void printElapsed() throws UninitializedIntervalException {
            long interval = getInterval();
            System.out.println("Interval " + intervalCount + " now equals to " + interval + " ms");
        }

        public void printAndInitInterval() {
            initInterval();
            System.out.println("Interval " + intervalCount + " starts here!!!");
        }

        public long getUncheckedInterval() {
            return System.currentTimeMillis() - initTime;
        }


        public static class UninitializedIntervalException extends Exception {
            private UninitializedIntervalException(String message) {
                super(message);
            }
        }

    }

    private void sortData() {

        Random r = new Random();
        int newrl;
        Test tempTest;
        Long tempLong;
        for (int i = 0; i < tests.size(); i++) {
            newrl = r.nextInt(tests.size());

            //Test
            tempTest = tests.get(i);
            tests.set(i, tests.get(newrl));
            tests.set(newrl, tempTest);

            //Times
            tempLong = totalTimes.get(i);
            totalTimes.set(i, totalTimes.get(newrl));
            totalTimes.set(newrl, tempLong);
            tempLong = averageTimes.get(i);
            averageTimes.set(i, averageTimes.get(newrl));
            averageTimes.set(newrl, tempLong);

        }
    }
}
