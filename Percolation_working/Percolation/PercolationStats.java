package Percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int trialCount; // trial to class variable
    private final double[] fractions; // an array of fractions from each trial
    private final double coefficient = 1.96;
    // the coefficient used in the calculation of 95% confidence

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("either n ≤ 0 or trials ≤ 0");
        }
        trialCount = trials;
        fractions = new double[trialCount];
        for (int trialnum = 0; trialnum < trialCount; trialnum++) {
            Percolation pr = new Percolation(n);
            int openedCount = 0;
            while (!pr.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!pr.isOpen(row, col)) {
                    pr.open(row, col);
                    openedCount++;
                }
            }
            double frac = (double) openedCount / (n * n);
            fractions[trialnum] = frac;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fractions);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(fractions);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - coefficient * stddev() / Math.sqrt(trialCount);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + coefficient * stddev() / Math.sqrt(trialCount);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats prs = new PercolationStats(n, trials);

        String confidence = "[" + prs.confidenceLo() + ", " + prs.confidenceHi() + "]";
        System.out.println("mean                    = " + prs.mean());
        System.out.println("stddev                  = " + prs.stddev());
        System.out.println("95% confidence interval = " + confidence);
    }
}
