import edu.princeton.cs.algs4.StdRandom;

public class HelloWorld {
    public static void main(String[] args) {

        int[] perm = StdRandom.permutation(5);
        for (int index: perm) {
            System.out.println(index);
        }

        int[][] m = {{0, 0, 0, 0},
                     {0, 0, 0, 0},
                     {0, 0, 0, 0},
                     {0, 0, 0, 0}};
        System.out.println(m[3][3]);
    }
}
