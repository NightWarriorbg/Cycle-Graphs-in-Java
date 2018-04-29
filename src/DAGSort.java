import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DAGSort {

    public static int[] sortDAG(int[][] edges)
            throws CycleDetectedException, InvalidNodeException {
        if (edges == null) {
            throw new NullPointerException("Edges can't be null");
        }
        for (int[] row : edges) {
            for (int i : row) {
                if (i < 0 || i >= edges.length) {
                    throw new InvalidNodeException(
                            "Edges array refers to invalid node: " + i);
                }
            }
        }
        List reverseSorted = new ArrayList(edges.length);
        boolean[] visiting = new boolean[edges.length];
        boolean[] visited = new boolean[edges.length];
        Arrays.fill(visiting, false);
        Arrays.fill(visited, false);
        for (int i = 0; i < edges.length; i++) {
            if (!visited[i]) {
                visit(i, edges, visiting, visited, reverseSorted);
            }
        }
        int[] sorted = new int[edges.length];
        for (int i = 0; i < edges.length; i++) {
            sorted[i] = reverseSorted.get(reverseSorted.size() - 1 - i);
        }
        return sorted;
    }

    private static void visit(int i, int[][] edges, boolean[] visiting,
                              boolean[] visited, List reverseSorted)
            throws CycleDetectedException {
        if (visiting[i]) {
            throw new CycleDetectedException();
        }
        if (!visited[i]) {
            visiting[i] = true;
            for (int outNeighbour : edges[i]) {
                visit(outNeighbour, edges, visiting, visited, reverseSorted);
            }
            visiting[i] = false;
            visited[i] = true;
            reverseSorted.add(i);
        }
    }

}