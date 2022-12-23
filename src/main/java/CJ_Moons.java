import java.util.*;
import java.io.*;

// Code Jam
public class CJ_Moons {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int numTestcases = Integer.valueOf(reader.readLine()); // Scanner has functions to read ints, longs, strings, chars, etc.
        List<String> results = new ArrayList<>();
        for (int testCase = 1; testCase <= numTestcases; testCase++) {
            String line = reader.readLine();
            String[] splitLine = split(line);
            int ctoJCost = Integer.valueOf(splitLine[0]);
            int jToCCost = Integer.valueOf(splitLine[1]);
            String word = splitLine[2];

            MoonsSolution solution = new CJ_Moons().new MoonsSolution();
            int minCost = solution.minCost(ctoJCost, jToCCost, word);

            results.add("Case #" + testCase + ": " + minCost);
        }

        for (String result : results) {
            System.out.println(result);
        }
    }

    public static String[] split(String list) {
        if (list.length() == 0) {
            return new String[0];
        }

        return list.split(" ");
    }

    class MoonsSolution {
        int minCost(int ctoJCost, int jToCCost, String word) {
            int minCost = 0;
            char prev = '\0';
            for (char character : word.toCharArray()) {
                if (prev == '\0' && character != '?') {
                    prev = character;
                } else if (prev != character && character != '?') {
                    if (prev == 'C') {
                        minCost += ctoJCost;
                    } else {
                        minCost += jToCCost;
                    }
                    prev = character;
                }
            }

            return minCost;
        }
    }
}
