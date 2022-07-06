
// You don't need to modify this file

public final class Point
{
    public final int x;
    public final int y;

    /**
     * A simple data holder class for a 2D point with integer coordinates.
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        return x + 97 * y;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Point) {
            Point p = (Point) other;
            return x == p.x && y == p.y;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Point(" + x + ", " + y + ")";
    }
}
