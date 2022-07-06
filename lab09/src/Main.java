

import java.util.function.Consumer;
import java.util.function.BiConsumer;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class Main {

    //
    // Do not modify this static field declaration.  An automated test
    // will use it.  This map holds a count of the number of times each
    // point is seen.
    //
    public static MyMap<Point, Integer> pointCounts;

    private static Comparator<MyMapEntry<Point, Integer>>
            sorter = (MyMapEntry<Point, Integer> a, MyMapEntry<Point, Integer> b) ->
    {
        int byY = Integer.compare(a.key.y, b.key.y);
        if (byY != 0) {
            return byY;
        }
        return Integer.compare(a.key.x, b.key.x);
    };

    /**
     * Adds one to the count for point p in pointCounts.  If p wasn't
     * in the map, adds it (with a count of 1).  This method will be
     * called by an automated test.
     */
    public static void addPoint(Point p) {
        Integer val = pointCounts.get(p);
        if (val == null) {
            pointCounts.put(p, 1);
        } else {
            pointCounts.put(p, 1 + val);
        }
    }

    /**
     * Print out the point counts, sortyed by y then x.
     * You do not need to modify this method.
     */
    public static void
    printPointCounts() {
        List<MyMapEntry<Point, Integer>> entries = pointCounts.getEntries();
        ArrayList<MyMapEntry<Point, Integer>> sorted = new ArrayList<>(entries);
        sorted.sort(sorter);
        for (MyMapEntry<Point, Integer> entry : sorted ) {
            System.out.println("    " + entry.key + " seen " +
                    entry.value + " times.");
        }
    }

    /**
     * Print the hash table buckets in order, starting from bucket 0.
     */
    public static void printBuckets() {
        // TODO: Implement this method, as follows:
        //
        List<List<MyMapEntry<Point, Integer>>> buckets = pointCounts.getBuckets();
        for (int i = 0; i < buckets.size(); i++) {
            ArrayList<MyMapEntry<Point, Integer>> sorted = new ArrayList<>(buckets.get(i));
            sorted.sort(sorter);
            System.out.println("    bucket " + i + " contains " + sorted);
        }
    }


    //
    // Main method.  You may modify this.
    //
    public static void main(String[] args) {
        // You might care to see what happens if you increase the
        // number of buckets to 20, or 40.
        pointCounts = new MyMap<Point, Integer>(10);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                addPoint(new Point(i, j));
                if (i == j) {
                    for (int k = 0; k < i; k++) {
                        addPoint(new Point(i, j));
                    }
                }
            }
        }

        System.out.println();
        System.out.println("Printing point counts, sorted by y, then x:");
        printPointCounts();

        System.out.println();
        System.out.println("Printing buckets:");
        printBuckets();

        System.out.println();
        System.out.println("Done.");
        System.out.println();
    }
}
