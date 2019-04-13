package graphs;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class NumberOfIslandsTest {
    private static int wakka(String landscape) {
        NumberOfIslands islandCounter = new NumberOfIslands(landscape);
        int islandCount = islandCounter.countIslands();
        return islandCount;
    }

    @Test
    public void emptyStringProcesses() {
        assertTrue(wakka("") == 0);
    }

    @Test
    public void singleCellProcesses() {
        assertTrue(wakka("1") == 1);
    }

    @Test
    public void singleRowProcesses() {
        assertTrue(wakka(String.join(System.getProperty("line.separator"), "111")) == 1);
        assertTrue(wakka(String.join(System.getProperty("line.separator"), "101")) == 2);
    }

    @Test
    public void multiRowProcesses() {
        assertTrue(wakka(String.join(System.getProperty("line.separator"),

                "11000",

                "11000",

                "00100",

                "00011"

        )) == 3);
    }
}
