
import java.util.ArrayList;

/**
 * @author Steven Dao
 * @version 1.0
 *
 * Date: February 9, 2022
 * Purpose: To create the class StudentSolver that, given a value for x and n representing the number of rows and number
 *          of players respectively, counts the number of configurations that will save every person, given the
 *          following rules:
 *          - Players choose to stand in a line in incremental order: Player 1, then Player 2, etc., where they will
 *            stand in the front of the line and remain there in place until the end of the game.
 *          - When a player chooses to stand in a line, the sum of his/her number and the number of the person that
 *            he/she is standing in back of must add up to a perfect square. (This rule does not apply to the first
 *            player in line because he/she is not standing in back of anyone.)
 *          - All lines must be populated by at least one person at the end or everyone gets shot.
 *          If it is a player’s turn and he/she cannot choose a line while respecting the rules, then that player and
 *          all players not in a line already are shot. Do not include duplicate configurations; two configurations
 *          are considered the same if one configuration is a line rearrangement of the other.
 */
public class StudentSolver {

    public static int solve(int x, int n) {

        // store the current line combinations which satisfy the requirements
        ArrayList<ArrayList<ArrayList<Integer>>> currentCombinations;
        // store the new line combinations after new insertions
        ArrayList<ArrayList<ArrayList<Integer>>> newCombinations = new ArrayList<>();
        newCombinations.add(new ArrayList<>());

        // populate the arraylist with empty lines
        for (int i = 0; i < x; ++i)
            newCombinations.get(0).add(new ArrayList<>());

        // traverse through all player numbers
        for (int i = 1; i <= n; ++i) {
            // update our current combinations to our most recently created combinations
            currentCombinations = new ArrayList<>(newCombinations);
            // clear the list, so it can be filled with updated combinations of lines
            newCombinations.clear();

            // fill the arraylist with newly found combinations by traversing through each row
            for (ArrayList<ArrayList<Integer>> currentCombination : currentCombinations) {
                findCombinations(i, 0, x, newCombinations, currentCombination);
            }
        }

        // store the size to prevent excessive method calls within loops
        int newCombinationsSize = newCombinations.size();

        // remove combinations with empty lines
        for (int i = 0; i < newCombinationsSize; ++i) {
            ArrayList<ArrayList<Integer>> currentCombination = newCombinations.get(i);
            if (hasEmptyLines(currentCombination)) {
                newCombinations.remove(i);
                --newCombinationsSize;
                --i;
            }
        }

        // testing with output to the console
        printCombinations(newCombinations);

        // our latest combination list size should have the final amount of combinations
        return newCombinationsSize;
    }

    public static void findCombinations(int currentPlayerNum, int currentRow, int maxRows,
                                 ArrayList<ArrayList<ArrayList<Integer>>> newCombinations,
                                        ArrayList<ArrayList<Integer>> currentCombination) {

        // base case: return once all lines have been traversed
        if (currentRow > maxRows - 1)
            return;

        ArrayList<Integer> currentLine = currentCombination.get(currentRow);

        // add to the end of this line and store the combination if it is either empty or the sum of the head
        // and the current player number equal a perfect square
        if (currentLine.size() == 0 ||
                Math.sqrt(currentLine.get(currentLine.size() - 1) + currentPlayerNum) % 1 == 0) {

            ArrayList<ArrayList<Integer>> newLines = new ArrayList<>();

            // manually create a shallow copy of this row, then add to the end of the row
            for (ArrayList<Integer> integers : currentCombination)
                newLines.add(new ArrayList<>(integers));
            newLines.get(currentRow).add(currentPlayerNum);

            // only add to our new combinations if it does not already contain a matching combination of lines
            if (!containsLines(newCombinations, newLines))
                newCombinations.add(newLines);
         }

        // recursively check all rows
        findCombinations(currentPlayerNum, currentRow + 1, maxRows, newCombinations, currentCombination);
    }

    public static boolean hasEmptyLines(ArrayList<ArrayList<Integer>> inputLines) {

        // return true if even one of the arraylists is empty
        for (ArrayList<Integer> inputLine : inputLines)
            if (inputLine.size() == 0)
                return true;

        return false;
    }

    public static boolean containsLines(ArrayList<ArrayList<ArrayList<Integer>>> combinations,
                                     ArrayList<ArrayList<Integer>> inputLines) {

        // check each line of the input lines
        for (ArrayList<Integer> inputLine : inputLines) {
            boolean found = false;

            // traverse through each line within each combination of lines
            for (ArrayList<ArrayList<Integer>> currentCombination : combinations) {
                for (ArrayList<Integer> currentLine : currentCombination) {
                    // if the compared lines match, update the flag and immediately break out
                    if (currentLine.containsAll(inputLine) && inputLine.containsAll(currentLine)) {
                        found = true;
                        break;
                    }
                }
                if (found)
                    break;
            }
            // return false if the line was not found in the provided combinations
            if (!found)
                return false;
        }

        // if we've reached this point, all input lines were found within the provided line combinations
        return true;
    }

    private static void printCombinations(ArrayList<ArrayList<ArrayList<Integer>>> combinations) {

        for (ArrayList<ArrayList<Integer>> lines : combinations) {
            System.out.println(lines);
        }
        System.out.println();
    }
}
