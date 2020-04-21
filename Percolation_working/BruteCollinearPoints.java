import java.util.ArrayList;
import java.util.Arrays;

/**
 * read code from:
 *
 * @author: salimt
 */

public class BruteCollinearPoints {

    private LineSegment[] segs; // all the segments containing 4 points

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {

        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            for (int j = i + 1; j < points.length; j++) {
                if (points[j] == null || points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }

        ArrayList<LineSegment> foundSegs = new ArrayList<>();
        Point[] pointsCopy = points.clone();
        Arrays.sort(pointsCopy);

        for (int p = 0; p < pointsCopy.length - 3; p++) {
            Point pVal = pointsCopy[p];
            for (int q = p + 1; q < pointsCopy.length - 2; q++) {
                Point qVal = pointsCopy[q];
                for (int r = q + 1; r < pointsCopy.length - 1; r++) {
                    Point rVal = pointsCopy[r];
                    for (int s = r + 1; s < pointsCopy.length; s++) {
                        Point sVal = pointsCopy[s];
                        if (pVal.slopeTo(qVal) == pVal.slopeTo(rVal) && pVal.slopeTo(qVal) == pVal
                                .slopeTo(sVal)) {
                            foundSegs.add(new LineSegment(pointsCopy[p], pointsCopy[s]));
                        }
                    }
                }
            }
        }
        segs = foundSegs.toArray(new LineSegment[foundSegs.size()]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return segs.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(segs, segs.length);
    }
}
