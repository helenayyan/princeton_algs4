package Percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid; // the examinated grid
    private final int gridSize; // size of the grid
    private final WeightedQuickUnionUF quickUF; // method used
    private final int toproot = 0; // the root at the top of the grid
    private final int bottomroot; // the root at the bottom of the grid


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        gridSize = n;
        grid = new boolean[n][n];
        quickUF = new WeightedQuickUnionUF((n * n) + 2);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
            }
        }
        bottomroot = n * n + 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        grid[row - 1][col - 1] = true;
        if (row == 1) {
            quickUF.union(indexPosition(row, col), toproot);
        }
        else if (row == gridSize) {
            quickUF.union(indexPosition(row, col), bottomroot);
        }
        if (col > 1 && isOpen(row, col - 1)) {
            quickUF.union(indexPosition(row, col), indexPosition(row, col - 1));
        }
        if (col < gridSize && isOpen(row, col + 1)) {
            quickUF.union(indexPosition(row, col), indexPosition(row, col + 1));
        }
        if (row > 1 && isOpen(row - 1, col)) {
            quickUF.union(indexPosition(row, col), indexPosition(row - 1, col));
        }
        if (row < gridSize && isOpen(row + 1, col)) {
            quickUF.union(indexPosition(row, col), indexPosition(row + 1, col));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (0 < row && row <= gridSize && 0 < col && col <= gridSize) {
            return quickUF.connected(toproot, indexPosition(row, col));
        }
        else {
            throw new IndexOutOfBoundsException();
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int num = 0;
        for (int i = 1; i <= gridSize; i++) {
            for (int j = 1; j <= gridSize; j++) {
                if (isOpen(i, j)) {
                    num++;
                }
            }
        }
        return num;
    }

    // does the system percolate?
    public boolean percolates() {
        return quickUF.connected(toproot, bottomroot);
    }

    // convert 2D position to index
    private int indexPosition(int row, int col) {
        return gridSize * (row - 1) + col;
    }
}
