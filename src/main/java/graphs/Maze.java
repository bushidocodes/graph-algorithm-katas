package graphs;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// A maze will be given by a 2d integer matrix(int [][]) that only contains 1s
// and 0s. 1 indicates a block that you cannot pass and 0 indicates a clear
// space that you can pass from.
// You should implement a program that reads the given matrix and takes any two
// points as inputs and tells whether there is a path in this maze between such
// points. Note that index starts at 0 for the points. For example, given the
// start point (1, 1) and the end point (1, 8), your program outputs 'YES' if
// there exists at least one clear path between them and 'NO' if no path exists.
public class Maze {
    Boolean visited[][] = {};
    Boolean traversable[][] = {};
    int[][] offsets = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };

    public Maze(int pattern[][]) {
        this.traversable = new Boolean[pattern.length][pattern[0].length];
        this.visited = new Boolean[pattern.length][pattern[0].length];
        for (int x = 0; x < pattern.length; x++) {
            for (int y = 0; y < pattern[x].length; y++) {
                this.traversable[x][y] = pattern[x][y] == 0;
                this.visited[x][y] = false;
            }
        }
    }

    private boolean isValid(int[] coordinates) {
        int x = coordinates[0];
        int y = coordinates[1];
        return x >= 0 && x < this.traversable.length && y >= 0 && y < this.traversable[x].length;
    }

    private boolean isNotVisited(int[] coordinates) {
        int x = coordinates[0];
        int y = coordinates[1];
        return !this.visited[x][y];
    }

    private boolean isTraversable(int[] coordinates) {
        int x = coordinates[0];
        int y = coordinates[1];
        return this.traversable[x][y];
    }

    private Stream<int[]> getPossibleMoves(int[] coordinates) {
        int x = coordinates[0];
        int y = coordinates[1];
        return Arrays.stream(this.offsets)
                .map(offset -> new int[] { x + offset[0], y + offset[1] })
                .filter(coords -> this.isValid(coords))
                .filter(coords -> this.isTraversable(coords))
                .filter(coords -> this.isNotVisited(coords));
    }

    public boolean canTraverse(int[] start, int[] end) {
        // Reset visited state up front so a single Maze instance can answer
        // multiple queries. canTraverse delegates to the recursive traverse
        // helper, which must NOT clear visited itself -- doing so on every
        // recursive call would re-explore cells endlessly and overflow the
        // stack.
        for (Boolean[] row : this.visited) {
            Arrays.fill(row, false);
        }
        return traverse(start, end);
    }

    private boolean traverse(int[] start, int[] end) {
        if (!isValid(start) || !isTraversable(start) || !isValid(end) || !isTraversable(end)) {
            return false;
        }
        int x = start[0];
        int y = start[1];
        if (x == end[0] && y == end[1]) {
            return true;
        } else {
            visited[x][y] = true;
            return this.getPossibleMoves(start).map(coords -> this.traverse(coords, end))
                    .collect(Collectors.toList()).contains(true);
        }
    }

    // Wrapper method to fulfill the exact kata requirements.
    public String canTraverseYesNo(int[] start, int[] end) {
        return canTraverse(start, end) ? "YES" : "NO";
    }
}
