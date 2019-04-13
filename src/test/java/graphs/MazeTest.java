package graphs;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

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
        int[][] mazePattern =

                {

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
}
