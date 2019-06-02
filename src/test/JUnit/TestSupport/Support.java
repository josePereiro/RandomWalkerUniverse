package test.JUnit.TestSupport;

import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.Random;

public class Support {

    /**
     * It does literally this {@code return Math.abs(v1 - v2) <= Math.max(v1, v2) * tol;}
     *
     * @param v1  a value to compare
     * @param v2  a value to compare
     * @param tol the tolerance percent. It should be a number between 0 and 1.
     *            Ex. 0.3 means that the tolerance is the 30% of the biggest value.
     * @return {@code true} is the difference is inside the tolerance.
     */
    public static boolean isApprox(double v1, double v2, double tol) {

        return Math.abs(v1 - v2) <= Math.max(v1, v2) * tol;
    }


    public static class WallTimeTester {

        private final ArrayList<Executable> tests;
        private final ArrayList<String> testNames;
        private final ArrayList<Long> totalTimes;
        private final ArrayList<Double> roundMeanTimes;
        private TimeInterval interval;
        private long lastRunTotalTests = 0;
        private final String testsName;

        public WallTimeTester(String testsName) {
            tests = new ArrayList<>();
            totalTimes = new ArrayList<>();
            roundMeanTimes = new ArrayList<>();
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
                double roundMean;
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
                    roundMean = totalTimes.get(t) * 1.0 / (round + 1);
                    if (verbose) System.out.println("Average(ms) " + roundMean);

                    //Updating time data
                    roundMeanTimes.set(t, roundMean);
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
            st.append("Results: " + testsName);
            st.append("\n");
            st.append("Total runs per test " + lastRunTotalTests);
            st.append("\n");
            for (int t = 0; t < tests.size(); t++) {
                st.append("Test: " + testNames.get(t));
                st.append("\n   ");
                st.append("Total time(ms) " + totalTimes.get(t));
                st.append("\n   ");
                st.append("Mean round time(ms) " + roundMeanTimes.get(t));
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
                roundMeanTimes.set(t, 0.0);
            }
        }

        public long getTotalTime(Executable test) {
            try {
                return totalTimes.get(tests.indexOf(test));
            } catch (IndexOutOfBoundsException e) {
                throw new IndexOutOfBoundsException("test not found");
            }

        }

        public long getTotalTime(String testName) {
            try {
                return totalTimes.get(testNames.indexOf(testName));
            } catch (IndexOutOfBoundsException e) {
                throw new IndexOutOfBoundsException("Any test have this name");
            }
        }

        public double getRoundMeanTime(Executable test) {
            try {
                return roundMeanTimes.get(tests.indexOf(test));
            } catch (IndexOutOfBoundsException e) {
                throw new IndexOutOfBoundsException("test not found");
            }
        }

        public double getRoundMeanTime(String testName) {
            try {
                return roundMeanTimes.get(testNames.indexOf(testName));
            } catch (IndexOutOfBoundsException e) {
                throw new IndexOutOfBoundsException("Any test have this name");
            }
        }

        /**
         * Add a test to be tested. All the test will be executed a given number of time.
         * By Default the order of execution will be random every iteration.
         *
         * @param test
         */
        public org.junit.jupiter.api.function.Executable addTest(String testName, Executable test) {
            tests.add(test);
            testNames.add(testName);
            totalTimes.add(0L);
            roundMeanTimes.add(0.0);
            return test;
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
            Double tempDouble;
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
                tempDouble = roundMeanTimes.get(i);
                roundMeanTimes.set(i, roundMeanTimes.get(ri));
                roundMeanTimes.set(ri, tempDouble);

            }
        }
    }
}
