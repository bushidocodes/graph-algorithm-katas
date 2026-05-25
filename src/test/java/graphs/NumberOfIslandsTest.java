package graphs;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NumberOfIslandsTest {
    private static int count(String landscape) {
        return new NumberOfIslands(landscape).countIslands();
    }

    @Test
    public void emptyStringProcesses() {
        assertTrue(count("") == 0);
    }

    @Test
    public void singleCellProcesses() {
        assertTrue(count("1") == 1);
    }

    @Test
    public void singleRowProcesses() {
        assertTrue(count(String.join(System.getProperty("line.separator"), "111")) == 1);
        assertTrue(count(String.join(System.getProperty("line.separator"), "101")) == 2);
    }

    @Test
    public void multiRowProcesses() {
        assertTrue(count(String.join(System.getProperty("line.separator"),

                "11000",

                "11000",

                "00100",

                "00011"

        )) == 3);
    }
}
