import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private Point[] points;
    private ArrayList<LineSegment> s;

    public FastCollinearPoints(Point[] points) {
        check_input(points);
        // finds all line segments containing 4 points
        this.points = Arrays.copyOf(points, points.length);
        s = new ArrayList<LineSegment>();

        for (int i = 0; i < this.points.length; i++) {
            points = Arrays.copyOfRange(this.points, i + 1, this.points.length);
            Arrays.sort(points, this.points[i].slopeOrder());
            int count = 0;
            double slope = Double.NaN;
            int j = 0;
            ArrayList<Point> collinears = new ArrayList<>();
            for (; j < points.length; j++) {
                double that_slope = this.points[i].slopeTo(points[j]);
                if (that_slope == slope) {
                    if (count == 0) {
                        collinears = new ArrayList<>();
                        collinears.add(this.points[i]);
                        collinears.add(points[j - 1]);
                    }
                    collinears.add(points[j]);
                    count++;

                } else {
                    if (count > 1) {
                        Point[] a = collinears.toArray(new Point[0]);
                        Arrays.sort(a);
                        s.add(new LineSegment(a[0], a[a.length - 1]));
                    }
                    slope = that_slope;
                    count = 0;
                }
            }
            if (count > 1) {
//                System.out.println(collinears);
                Point[] a = collinears.toArray(new Point[0]);
                Arrays.sort(a);
                s.add(new LineSegment(a[0], a[a.length - 1]));
            }
        }
    }

    private void check_input(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        for (Point point : points) {
            if (point == null) throw new IllegalArgumentException();
        }
        Arrays.sort(points);
        for (int i = 1; i < points.length; i++) {
            if (points[i-1].equals(points[i])) throw new IllegalArgumentException();
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
        StdOut.println(Arrays.toString(points));

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}