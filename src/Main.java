
import java.util.ArrayList;

/**
 * @author Steven Dao
 * @version 1.0
 *
 * Date: February 9, 2022
 * Purpose: To test the results received from the StudentSolver class.
 */
public class Main {

    /**
     * Tests the StudentSolver function using the provided test cases.
     * To add additional test cases, add to the provided containers as necessary.
     *
     * @param args the command-line arguments of the application
     */
    public static void main(String[] args) {

        int numOfTests = 0;
        int numOfTestsPassed = 0;
        ArrayList<Integer> inputXs = new ArrayList<>();
        ArrayList<Integer> inputNs = new ArrayList<>();
        ArrayList<Integer> expectedCombinations = new ArrayList<>();


        // test case 1 -------------------------------------------------------------------------------------------------
        ++numOfTests;
        inputXs.add(3);
        inputNs.add(7);
        expectedCombinations.add(1);

        // test case 2 -------------------------------------------------------------------------------------------------
        ++numOfTests;
        inputXs.add(4);
        inputNs.add(7);
        expectedCombinations.add(4);

        // test case 3 -------------------------------------------------------------------------------------------------
        ++numOfTests;
        inputXs.add(6);
        inputNs.add(17);
        expectedCombinations.add(23);

        // test case 4 -------------------------------------------------------------------------------------------------
        ++numOfTests;
        inputXs.add(8);
        inputNs.add(31);
        expectedCombinations.add(83);

        // test case 5 -------------------------------------------------------------------------------------------------
        ++numOfTests;
        inputXs.add(13);
        inputNs.add(69);
        expectedCombinations.add(504);

        ++numOfTests;
        inputXs.add(26);
        inputNs.add(138);
        expectedCombinations.add(504);


        boolean[] inputsPassed = new boolean[numOfTests];
        double[] testTimes = new double[numOfTests];


        System.out.println("""
                
                ================================================================================
                                            Testing Squid Game ...
                ================================================================================""");


        for (int currentIndex = 0; currentIndex < numOfTests; ++currentIndex) {
            int testNumber = currentIndex + 1;
            int inputX = inputXs.get(currentIndex);
            int inputN = inputNs.get(currentIndex);
            int expectedCombination = expectedCombinations.get(currentIndex);

            System.out.printf("""
                    
                    ---------------------------------
                           Input Parameters %s
                    ---------------------------------
                    """, testNumber);
            System.out.printf("""
                                Input x: %d
                                Input n: %d
                                """, inputX, inputN);

            System.out.printf("""
                    
                    -------------------------------
                          Simulated Results %s
                    -------------------------------
                    """, testNumber);
            long startTime = System.nanoTime();

            int resultCombination = StudentSolver.solve(inputX, inputN);

            long endTime = System.nanoTime();
            testTimes[currentIndex] = (double) (endTime - startTime) / 1_000_000_000;

            System.out.printf("""
                    Number of combinations: %d
                    """, resultCombination);

            System.out.printf("""
                    
                    ------------------------------
                          Expected Results %s
                    ------------------------------
                    """, testNumber);
            System.out.printf("""
                    Number of combinations: %d
                    """, expectedCombination);

            if (resultCombination == expectedCombination) {
                inputsPassed[currentIndex] = true;
                ++numOfTestsPassed;
            }

            System.out.println("\n=======================================================================================");
        }

        StringBuilder testLog = new StringBuilder();

        for (int i = 0; i < numOfTests; ++i)
            testLog.append("\nTest ").append(i + 1).append(":\t ").append(inputsPassed[i] ? "+ Passed" : "- Failed");
        testLog.append("\n\nTests passed:\t").append(numOfTestsPassed).append(" / ").append(numOfTests);

        System.out.println(testLog);

        System.out.println("\n=======================================================================================");

        StringBuilder timeLog = new StringBuilder();
        double elapsedTime = 0;

        for (double time : testTimes)
            elapsedTime += time;

        for (int i = 0; i < numOfTests; ++i)
            timeLog.append("\nTest ").append(i + 1).append(" time:\t").append(testTimes[i]).append(" seconds");
        timeLog.append("\n\nTotal execution time:\t").append(elapsedTime).append(" seconds");

        System.out.println(timeLog);

        System.out.println("\n=======================================================================================");
    }
}
