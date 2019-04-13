package graphs;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Number of Islands
// Given a 2d grid map of 1s(land) and 0s(water), count the number of islands.
// An island is surrounded by water and is formed by connecting adjacent lands
// horizontally or vertically. You may assume all four edges of the grid are all
// surrounded by water.
// Example:
// Input:
// 11000
// 11000
// 00100
// 00011
// Output: 3
public class NumberOfIslands {
    Boolean landMatrix[][];
    Boolean visited[][] = {};
    int islandCount = 0;
    int[][] offsets = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };

    public NumberOfIslands(String landscape) {

        // Nightmare-fuel attempt to do functional string manipulation.
        this.landMatrix = Arrays.stream(landscape.split("\n")).map(

                row -> Arrays.stream(row.split("")).map(c -> c.equals("1")).toArray(Boolean[]::new)

        ).toArray(Boolean[][]::new);
        System.out.println(this.landMatrix);

        this.visited = new Boolean[this.landMatrix.length][this.landMatrix[0].length];

        for (int x = 0; x < this.landMatrix.length; x++) {
            for (int y = 0; y < this.landMatrix[0].length; y++) {
                this.visited[x][y] = false;
            }
        }
    }

    private boolean isValid(int[] coordinates) {
        int x = coordinates[0];
        int y = coordinates[1];
        return x >= 0 && x < this.landMatrix.length && y >= 0 && y < this.landMatrix[x].length;
    }

    private boolean isNotVisited(int[] coordinates) {
        int x = coordinates[0];
        int y = coordinates[1];
        boolean isVisited = this.visited[x][y];
        return isVisited == false;
    }

    private boolean isLand(int[] coordinates) {
        int x = coordinates[0];
        int y = coordinates[1];
        boolean isLand = this.landMatrix[x][y];
        return isLand;
    }

    private Stream<int[]> getNeighbors(int[] coordinates) {
        int x = coordinates[0];
        int y = coordinates[1];
        return Arrays.stream(this.offsets)

                .map(offset -> {
                    int[] result = { x + offset[0], y + offset[1] };
                    return result;
                })

                .filter(coords -> this.isValid(coords))

                .filter(coords -> this.isLand(coords))

                .filter(coords -> this.isNotVisited(coords));
    }

    private ArrayList<int[]> getCoordinates() {
        ArrayList<int[]> coords = new ArrayList<int[]>();
        for (int x = 0; x < this.landMatrix.length; x++) {
            for (int y = 0; y < this.landMatrix[x].length; y++) {
                int[] coord = { x, y };
                coords.add(coord);
            }
        }
        return coords;

    }

    private List<int[]> findUnvisitedLand() {
        List<int[]> unvisitedLands = this.getCoordinates().stream()

                .filter(coord -> this.isLand(coord))

                .filter(coord -> this.isNotVisited(coord))

                .collect(Collectors.toList());

        return unvisitedLands;
    }

    public void dfs(int[] coordinates) {
        int x = coordinates[0];
        int y = coordinates[1];
        visited[x][y] = true;
        this.getNeighbors(coordinates).forEach(coords -> this.dfs(coords));
    }

    public int countIslands() {
        List<int[]> unusedLand = this.findUnvisitedLand();
        while (unusedLand.size() > 0) {
            this.islandCount++;
            this.dfs(unusedLand.get(0));
            unusedLand = this.findUnvisitedLand();
        }
        return this.islandCount;
    }
}
