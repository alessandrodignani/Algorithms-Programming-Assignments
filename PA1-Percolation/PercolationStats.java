import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import java.time.Instant;
import java.time.Duration;

public class PercolationStats {
    private double[] samples;

    public PercolationStats(int n,
                            int trials) {   // perform trials independent experiments on an n-by-n grid
        this.samples = new double[trials];

        for (int t = 0; t < trials; t++) {
            samples[t] = experiment(n);
        }
    }

    private double experiment(int n) {
        Percolation perc = new Percolation(n);
        int[] perm = StdRandom.permutation(n * n);
        for (int index: perm) {
            int i = index / n + 1;
            int j = index % n + 1;
            perc.open(i, j);

            if (perc.percolates()) break;
        }
        return (double) perc.numberOfOpenSites() / (n * n);
    }

    public double mean() {  // sample mean of percolation threshold
        return StdStats.mean(samples);
    }

    public double stddev() {    // sample standard deviation of percolation threshold
        return StdStats.stddev(samples);
    }

    public double confidenceLo() {                  // low  endpoint of 95% confidence interval
        double confidence = 1.96 * stddev() / Math.sqrt(samples.length);
        return mean() - confidence;
    }

    public double confidenceHi() {                  // high endpoint of 95% confidence interval
        double confidence = 1.96 * stddev() / Math.sqrt(samples.length);
        return mean() + confidence;
    }

    private static void printExperiment(int n, int trials) {
        PercolationStats ps = new PercolationStats(n, trials);
        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                  = " + ps.stddev());
        System.out.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }

    public static void main(String[] args) {        // test client (described below)
        int n = 200;
        int trials = 10000;

        // try {
        //     // Parse the string argument into an integer value.
        //     n = Integer.parseInt(args[0]);
        // }
        // catch (NumberFormatException nfe) {
        //     // The first argument isn't a valid integer.  Print
        //     // an error message, then exit with an error code.
        //     System.out.println("The first argument must be an integer.");
        // }
        //
        // try {
        //     // Parse the string argument into an integer value.
        //     trials = Integer.parseInt(args[1]);
        // }
        // catch (NumberFormatException nfe) {
        //     // The first argument isn't a valid integer.  Print
        //     // an error message, then exit with an error code.
        //     System.out.println("The second argument must be an integer.");
        // }

        Instant start = Instant.now();
        printExperiment(n, trials);
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end));
    }
}