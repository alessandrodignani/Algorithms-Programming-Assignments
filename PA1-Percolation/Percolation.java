/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.UF;

public class Percolation {
    private boolean[][] chessboard; // false -> blocked, true -> open
    private int n;
    private int numberOfOpenSites;
    private UF uf;
    private final int startId;
    private final int endId;

    public Percolation(int n) {
        this.n = n;
        this.chessboard = new boolean[n][n];
        this.numberOfOpenSites = 0;

        this.uf = new UF(n * n + 2);
        this.startId = 0;
        this.endId = n * n + 1;
    }

    public boolean isFull(int row, int col) {
        return uf.connected(d2tod1(row, col), startId);
    }

    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > this.n || col <= 0 || col > this.n)
            throw new IllegalArgumentException();
        return chessboard[row - 1][col - 1];
    }

    public boolean percolates() {
        return uf.connected(this.startId, this.endId);
    }

    public void open(int i, int j) {
        if (i <= 0 || i > this.n || j <= 0 || j > this.n) throw new IllegalArgumentException();

        // open up
        if (i == 1) { // if it's first row
            // union with start node
            uf.union(d2tod1(i, j), this.startId);
        }
        else {
            if (isOpen(i - 1, j)) uf.union(d2tod1(i, j), d2tod1(i - 1, j));
        }

        // open right
        if (j != n) { // if it's not last column
            if (isOpen(i, j + 1)) uf.union(d2tod1(i, j), d2tod1(i, j + 1));
        }

        // open down
        if (i == n) { // if it's last row
            // union with end node
            uf.union(d2tod1(i, j), this.endId);
        }
        else {
            if (isOpen(i + 1, j)) uf.union(d2tod1(i, j), d2tod1(i + 1, j));
        }

        // open left
        if (j != 1) { // if it's not first column
            if (isOpen(i, j - 1)) uf.union(d2tod1(i, j), d2tod1(i, j - 1));
        }

        this.chessboard[i - 1][j - 1] = true;
        this.numberOfOpenSites++;
    }

    public int numberOfOpenSites() {
        return this.numberOfOpenSites;
    }

    private void printChessboard() {
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                System.out.print(chessboard[row][col] ? "· " : "O ");
            }
            System.out.println();
        }
    }

    private int d2tod1(int row, int col) {
        return (row - 1) * n + col;
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(10);
        p.open(3, 3);
        p.open(3, 4);
        p.printChessboard();
        System.out.println(p.percolates());

        p.open(1, 4);
        p.open(2, 4);
        p.open(4, 3);
        p.open(5, 3);
        p.open(6, 3);
        p.open(7, 3);
        p.open(8, 3);
        p.open(9, 3);
        p.open(10, 3);
        p.printChessboard();
        System.out.println(p.percolates());
    }
}
