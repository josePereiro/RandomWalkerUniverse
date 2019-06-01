package test.JUnit;

import java.util.ArrayList;
import java.util.Random;

import org.junit.jupiter.api.function.Executable;

public class WallTimeTester {

    private final ArrayList<Executable> tests;
    private final ArrayList<String> testNames;
    private final ArrayList<Long> totalTimes;
    private final ArrayList<Long> meanTimes;
    private TimeInterval interval;
    private long lastRunTotalTests = 0;
    private final String testsName;

    public WallTimeTester(String testsName) {
        tests = new ArrayList<>();
        totalTimes = new ArrayList<>();
        meanTimes = new ArrayList<>();
        testNames = new ArrayList<>();
        this.testsName = testsName;
    }

    public void runTests(int rounds, int itrsPerTest,
                         boolean verbose, boolean randomize) throws Throwable {
        interval = new TimeInterval();

        for (int round = 0; round < rounds; round++) {

            if (randomize) sortData();
            if (verbose) System.out.println("Round " + round + " of " + rounds);

            long testTime;
            long testAve;
            for (int t = 0; t < tests.size(); t++) {
                Executable test = tests.get(t);
                if (verbose) System.out.println("Testing " + testNames.get(t) + "  " + itrsPerTest + " times...");

                //Running test
                interval.initInterval();
                for (int i = 0; i < itrsPerTest; i++) {
                    test.execute();
                }
                testTime = interval.getUncheckedInterval();

                if (verbose) System.out.println("Round Time(ms) " + testTime);

                //Updating time data
                totalTimes.set(t, totalTimes.get(t) + testTime);

                //Ave
                testAve = totalTimes.get(t) / (round + 1);
                if (verbose) System.out.println("Average(ms) " + testAve);

                //Updating time data
                meanTimes.set(t, testAve);
            }

            if (verbose) System.out.println();
        }

        lastRunTotalTests = rounds * itrsPerTest;

        //Final Info
        if (verbose) {
            printResults();
        }
    }

    public void printResults() {
        System.out.println(getPrintableResults());
    }

    public String getPrintableResults() {
        StringBuffer st = new StringBuffer();
        st.append(testsName + " Results");
        st.append("\n");
        st.append("TotalTest " + lastRunTotalTests);
        st.append("\n");
        for (int t = 0; t < tests.size(); t++) {
            st.append("Test " + testNames.get(t));
            st.append("\n");
            st.append("Total Time(ms) " + totalTimes.get(t));
            st.append("\n");
            st.append("Ave Time(ms) " + meanTimes.get(t));
            st.append("\n");
        }
        return st.toString();
    }

    public long getLastRunTotalTests() {
        return lastRunTotalTests;
    }

    public void runTests(int rounds, int itrsPerTest, boolean randomize) throws Throwable {
        runTests(rounds, itrsPerTest, false, randomize);
    }

    public void runTests(int rounds, int itrsPerTest) throws Throwable {
        runTests(rounds, itrsPerTest, false, true);
    }

    public void reset() {
        for (int t = 0; t < tests.size(); t++) {
            totalTimes.set(t, 0L);
            meanTimes.set(t, 0L);
        }
    }

    public long getTotalTime(Executable test) {
        return totalTimes.get(tests.indexOf(test));
    }

    public long getTotalTime(String testName) {
        return totalTimes.get(testNames.indexOf(testName));
    }

    public long getMeanTime(Executable test) {
        return meanTimes.get(tests.indexOf(test));
    }

    public long getMeanTime(String testName) {
        return meanTimes.get(testNames.indexOf(testName));
    }

    /**
     * Add a test to be tested. All the test will be executed a given number of time.
     * By Default the order of execution will be random every iteration.
     *
     * @param test
     */
    public void addTest(String testName, Executable test) {
        tests.add(test);
        testNames.add(testName);
        totalTimes.add(0L);
        meanTimes.add(0L);
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
        int ri;
        Executable tempTest;
        Long tempLong;
        String tempName;
        for (int i = 0; i < tests.size(); i++) {
            ri = r.nextInt(tests.size());

            //Test
            tempTest = tests.get(i);
            tests.set(i, tests.get(ri));
            tests.set(ri, tempTest);

            //Names
            tempName = testNames.get(i);
            testNames.set(i, testNames.get(ri));
            testNames.set(ri, tempName);

            //Times
            tempLong = totalTimes.get(i);
            totalTimes.set(i, totalTimes.get(ri));
            totalTimes.set(ri, tempLong);
            tempLong = meanTimes.get(i);
            meanTimes.set(i, meanTimes.get(ri));
            meanTimes.set(ri, tempLong);

        }
    }
}
