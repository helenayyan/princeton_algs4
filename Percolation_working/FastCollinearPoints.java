import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * read code from:
 *
 * @author: salimt
 */

public class FastCollinearPoints {
    private LineSegment[] segs; // all the segments containing 4 points

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        // throw exceptions
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

        ArrayList<Point> ends = new ArrayList<>();
        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);
        for (int i = 0; i < sortedPoints.length - 3; i++) {

            ArrayList<Double> slopes = new ArrayList<>();
            for (int j = i + 1; j < sortedPoints.length; j++) {
                slopes.add(sortedPoints[i].slopeTo(sortedPoints[j]));
            }

            ArrayList<Double> reps = new ArrayList<>();
            for (double val : slopes) {
                if (Collections.frequency(slopes, val) >= 3 && !reps.contains(val)) {
                    reps.add(val);
                }
            }

            for (double rep : reps) {
                Point[] segment = new Point[2];
                segment[0] = sortedPoints[i];
                for (int s = 0; s < slopes.size(); s++) {
                    if (slopes.get(s).equals(rep)) {
                        segment[1] = sortedPoints[i + s + 1];
                    }
                }
                if (segment[1] != null && !ends.contains(segment[1]))
                    foundSegs.add(new LineSegment(segment[0], segment[1]));
                ends.add(segment[1]);
            }
        }
        this.segs = foundSegs.toArray(new LineSegment[0]);

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
