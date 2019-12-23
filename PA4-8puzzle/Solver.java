import edu.princeton.cs.algs4.MinPQ;

import java.util.LinkedList;

public class Solver {
    private final boolean isSolvable;
    private final int moves;
    private final LinkedList<Board> solution;

    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final SearchNode predecessor;
        private final int moves;
        private int priority;

        public SearchNode(Board board, SearchNode predecessor, int moves) {
            this.board = board;
            this.predecessor = predecessor;
            this.moves = moves;
            this.priority = -1;
        }

        @Override
        public int compareTo(SearchNode that) {
            // if (that == null) throw new IllegalArgumentException();
            // if (this.priority() > that.priority()) return 1;
            // else if (this.priority() == that.priority())
            //     return Integer.compare(this.board.hamming(), that.board.hamming());
            // else return -1;
            return Integer.compare(this.priority(), that.priority());
        }

        private int priority() {
            if (priority == -1) {
                priority = board.manhattan() + moves;
            }
            return priority;
        }
    }

    public Solver(Board initial) {
        // find a solution to the initial board (using the A* algorithm)
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        MinPQ<SearchNode> original = new MinPQ<SearchNode>();
        original.insert(new SearchNode(initial, null, 0));
        MinPQ<SearchNode> twin = new MinPQ<SearchNode>();
        twin.insert(new SearchNode(initial.twin(), null, 0));

        SearchNode lastSearchNode;
        while (true) {
            lastSearchNode = aStarStep(original);
            if (lastSearchNode != null) {
                isSolvable = true;
                solution = new LinkedList<>();
                moves = lastSearchNode.moves;
                do {
                    solution.addFirst(lastSearchNode.board);
                    lastSearchNode = lastSearchNode.predecessor;
                } while (lastSearchNode != null);
                break;
            }
            lastSearchNode = aStarStep(twin);
            if (lastSearchNode != null) {
                isSolvable = false;
                moves = -1;
                solution = null;
                break;
            }
        }
    }

    private SearchNode aStarStep(MinPQ<SearchNode> pq) {
        SearchNode current = pq.delMin();
        if (current.board.isGoal()) {
            return current;
        }
        Board predecessorBoard = current.predecessor == null ? null : current.predecessor.board;
        for (Board neighbor : current.board.neighbors()) {
            if (!neighbor.equals(predecessorBoard)) {
                pq.insert(new SearchNode(neighbor, current, current.moves + 1));
            }
        }
        return null;
    }

    public boolean isSolvable() {
        // is the initial board solvable?
        return isSolvable;
    }

    public int moves() {
        // min number of moves to solve initial board; -1 if unsolvable
        return moves;
    }

    public Iterable<Board> solution() {
        // sequence of boards in a shortest solution; null if unsolvable
        return solution;
    }

    public static void main(String[] args) {
        Solver s = new Solver(new Board(new int[][] { { 0, 1, 3 }, { 4, 2, 5 }, { 7, 8, 6 } }));
        for (Board b : s.solution()) {
            System.out.println(b);
        }
    }
}