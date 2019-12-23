import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private Point[] points;
    private ArrayList<LineSegment> s;

    public BruteCollinearPoints(Point[] points) {
        check_input(points);
        // finds all line segments containing 4 points
        this.points = points;
        s = new ArrayList<LineSegment>();

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        double slope = points[i].slopeTo(points[j]);
                        if (slope == points[i].slopeTo(points[k]) && slope == points[i].slopeTo(points[l])) {
                            Point[] a = {points[i], points[j], points[k], points[l]};
                            Arrays.sort(a);
                            s.add(new LineSegment(a[0], a[3]));
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        // the number of line segments
        return s.size();
    }

    public LineSegment[] segments() {
        // the line segments
        return s.toArray(new LineSegment[0]);
    }

    private void check_input(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        for (Point point : points) {
            if (point == null) throw new IllegalArgumentException();
        }
        Arrays.sort(points);
        for (int i = 1; i < points.length; i++) {
            if (points[i - 1].equals(points[i])) throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}