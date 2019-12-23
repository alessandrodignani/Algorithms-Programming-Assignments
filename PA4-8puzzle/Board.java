import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Board {
    private final int[][] blocks;
    private final int n;
    private int hamming;
    private int manhattan;
    private Board twin;

    public Board(int[][] blocks) {
        // construct a board from an n-by-n array of blocks {
        // (where blocks[i][j] = block in row i, column j)
        // n-by-n array containing the n^2 integers between 0 and n^2 − 1, where 0 represents the blank square.
        this.blocks = blocks.clone();
        this.n = blocks[0].length;
        this.hamming = -1;
        this.manhattan = -1;
        this.twin = null;
    }

    public int dimension() {
        // board dimension n
        return n;
    }

    public int hamming() {
        // number of blocks out of place
        if (hamming == -1) {
            int[][] goal = goal();
            int h = 0;
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    if (blocks[i][j] != 0 && blocks[i][j] != goal[i][j]) h++;
            hamming = h;
        }
        return hamming;
    }

    public int manhattan() {
        // sum of Manhattan distances between blocks and goal
        if (manhattan == -1) {
            int m = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (blocks[i][j] != 0) {
                        m += Math.abs(i - (blocks[i][j] - 1) / n);
                        m += Math.abs(j - (blocks[i][j] - 1) % n);
                    }
                }
            }
            manhattan = m;
        }
        return manhattan;
    }

    public boolean isGoal() {
        // is this board the goal board?
        int[][] g = goal();
        return Arrays.deepEquals(this.blocks, g);
    }

    private int[][] goal() {
        int num = 1;
        int[][] g = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                g[i][j] = num++;
            }
        }
        g[n - 1][n - 1] = 0;
        return g;
    }

    public Board twin() {
        // a board that is obtained by exchanging any pair of blocks
        if (twin == null) {
            int i0 = 0;
            int i1 = 1;
            int j0 = blocks[0][0] == 0 ? 1 : 0;
            int j1 = blocks[1][0] == 0 ? 1 : 0;

            int[][] twinBlocks = exchange(blocks, i0, j0, i1, j1);

            twin = new Board(twinBlocks);
        }
        return twin;
    }

    private static int[][] exchange(int[][] original, int i0, int j0, int i1, int j1) {
        // clone matrix
        int[][] exchanged = Arrays.stream(original).map(int[]::clone).toArray(int[][]::new);
        int temp = exchanged[i0][j0];
        exchanged[i0][j0] = exchanged[i1][j1];
        exchanged[i1][j1] = temp;
        return exchanged;
    }

    public boolean equals(Object obj) {
        // does this board equal that?
        if (obj == this) return true;
        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;
        Board that = (Board) obj;
        return Arrays.deepEquals(this.blocks, that.blocks);
    }

    public Iterable<Board> neighbors() {
        // all neighboring boards
        int zeroI = -1;
        int zeroJ = -1;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (blocks[i][j] == 0) {
                    zeroI = i;
                    zeroJ = j;
                    break;
                }
        Stack<Board> nb = new Stack<Board>();
        if (zeroI != 0) {
            nb.push(new Board(exchange(blocks, zeroI, zeroJ, zeroI - 1, zeroJ)));
        }
        if (zeroI != n - 1) {
            nb.push(new Board(exchange(blocks, zeroI, zeroJ, zeroI + 1, zeroJ)));
        }
        if (zeroJ != 0) {
            nb.push(new Board(exchange(blocks, zeroI, zeroJ, zeroI, zeroJ - 1)));
        }
        if (zeroJ != n - 1) {
            nb.push(new Board(exchange(blocks, zeroI, zeroJ, zeroI, zeroJ + 1)));
        }

        return nb;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        // unit tests (not graded)
        int[][] blocks = { { 3, 2, 1 }, { 0, 4, 5 }, { 6, 7, 8 } };
        Board b = new Board(blocks);
        StdOut.println(b);
        StdOut.println(b.twin());
        StdOut.println(b.manhattan());
    }
}