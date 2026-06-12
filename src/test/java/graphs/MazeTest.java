package graphs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MazeTest {
    @Test
    public void twoOpenCellsShouldBeTrue() {
        int[][] mazePattern = { { 0, 0 } };
        Maze myMaze = new Maze(mazePattern);
        int[] start = { 0, 0 };
        int[] end = { 0, 1 };
        assertTrue(myMaze.canTraverse(start, end));
    }

    @Test
    public void startEqualsEndOnOpenCellShouldBeTrue() {
        int[][] mazePattern = { { 0 } };
        Maze myMaze = new Maze(mazePattern);
        int[] point = { 0, 0 };
        assertTrue(myMaze.canTraverse(point, point));
    }

    @Test
    public void outOfBoundsEndpointsShouldBeFalse() {
        int[][] mazePattern = { { 0, 0 } };
        Maze myMaze = new Maze(mazePattern);
        int[] inBounds = { 0, 0 };
        int[] outOfBounds = { 5, 5 };
        assertFalse(myMaze.canTraverse(outOfBounds, inBounds));
        assertFalse(myMaze.canTraverse(inBounds, outOfBounds));
    }

    @Test
    public void blockedEndpointsShouldBeFalse() {
        int[][] mazePattern = { { 1, 0, 1 } };
        Maze myMaze = new Maze(mazePattern);
        int[] openCell = { 0, 1 };
        int[] blockedCell = { 0, 0 };
        // A wall cell is not a valid start or end, regardless of reachability.
        assertFalse(myMaze.canTraverse(blockedCell, openCell));
        assertFalse(myMaze.canTraverse(openCell, blockedCell));
    }

    @Test
    public void twoClosedCellsShouldBeFalse() {
        int[][] mazePattern = { { 1, 1 } };
        Maze myMaze = new Maze(mazePattern);
        int[] start = { 0, 0 };
        int[] end = { 0, 1 };
        assertFalse(myMaze.canTraverse(start, end));
    }

    @Test
    public void middleCellBlocksShouldBeFalse() {
        int[][] mazePattern = { { 0, 1, 0 } };
        Maze myMaze = new Maze(mazePattern);
        int[] start = { 0, 0 };
        int[] end = { 0, 2 };
        assertFalse(myMaze.canTraverse(start, end));
    }

    @Test
    public void longOpenCorridorShouldBeTrue() {
        int[][] mazePattern = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };
        Maze myMaze = new Maze(mazePattern);
        int[] start = { 0, 9 };
        int[] end = { 0, 0 };
        assertTrue(myMaze.canTraverse(start, end));
    }

    @Test
    public void firstMaze() {
        int[][] mazePattern = {
                { 0, 0, 0, 1, 0 },
                { 1, 0, 0, 1, 1 },
                { 0, 0, 0, 1, 0 },
                { 1, 0, 0, 0, 0 },
                { 0, 0, 1, 0, 0 }
        };
        Maze myMaze = new Maze(mazePattern);
        int[] start = { 0, 0 };
        int[] end = { 4, 4 };
        assertTrue(myMaze.canTraverse(start, end));
        Maze myMaze2 = new Maze(mazePattern);
        int[] start2 = { 0, 4 };
        int[] end2 = { 4, 0 };
        assertFalse(myMaze2.canTraverse(start2, end2));
    }

    @Test
    public void secondMaze() {
        int[][] mazePattern = {
                { 0, 0, 0, 1, 0 },
                { 1, 0, 0, 1, 1 },
                { 0, 0, 0, 1, 0 },
                { 1, 0, 1, 0, 0 },
                { 0, 0, 1, 0, 0 }
        };
        Maze myMaze = new Maze(mazePattern);
        int[] start = { 0, 0 };
        int[] end = { 4, 4 };
        assertFalse(myMaze.canTraverse(start, end));
        Maze myMaze2 = new Maze(mazePattern);
        int[] start2 = { 0, 4 };
        int[] end2 = { 4, 0 };
        assertFalse(myMaze2.canTraverse(start2, end2));
        Maze myMaze3 = new Maze(mazePattern);
        int[] start3 = { 0, 0 };
        int[] end3 = { 4, 0 };
        assertTrue(myMaze3.canTraverse(start3, end3));
    }

    @Test
    public void canReuseInstanceAcrossMultipleQueries() {
        int[][] mazePattern = {
                { 0, 0, 0, 1, 0 },
                { 1, 0, 0, 1, 1 },
                { 0, 0, 0, 1, 0 },
                { 1, 0, 0, 0, 0 },
                { 0, 0, 1, 0, 0 }
        };
        Maze myMaze = new Maze(mazePattern);
        int[] reachableStart = { 0, 0 };
        int[] reachableEnd = { 4, 4 };
        int[] unreachableStart = { 0, 4 };
        int[] unreachableEnd = { 4, 0 };
        // Reusing one instance for several queries must give consistent
        // results. Before visited state was reset per call, the second query
        // returned a stale answer because cells stayed marked from the first.
        assertTrue(myMaze.canTraverse(reachableStart, reachableEnd));
        assertTrue(myMaze.canTraverse(reachableStart, reachableEnd));
        assertFalse(myMaze.canTraverse(unreachableStart, unreachableEnd));
        assertTrue(myMaze.canTraverse(reachableStart, reachableEnd));
    }

    @Test
    public void secondMazeWithYesNo() {
        int[][] mazePattern = {
                { 0, 0, 0, 1, 0 },
                { 1, 0, 0, 1, 1 },
                { 0, 0, 0, 1, 0 },
                { 1, 0, 1, 0, 0 },
                { 0, 0, 1, 0, 0 }
        };
        Maze myMaze = new Maze(mazePattern);
        int[] start = { 0, 0 };
        int[] end = { 4, 4 };
        assertEquals("NO", myMaze.canTraverseYesNo(start, end));
        Maze myMaze2 = new Maze(mazePattern);
        int[] start2 = { 0, 4 };
        int[] end2 = { 4, 0 };
        assertEquals("NO", myMaze2.canTraverseYesNo(start2, end2));
        Maze myMaze3 = new Maze(mazePattern);
        int[] start3 = { 0, 0 };
        int[] end3 = { 4, 0 };
        assertEquals("YES", myMaze3.canTraverseYesNo(start3, end3));
    }
}
