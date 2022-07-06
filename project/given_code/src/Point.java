
/**
 * Immutable object representing a point on the VirtualWorld grid.
 */
final class Point
{
    private final int x;
    private final int y;

    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString()
    {
        return "Point(" + x + "," + y + ")";
    }

    public boolean equals(Object other)
    {
        if (other instanceof Point) {
            Point op = (Point) other;
            return x == op.x && y == op.y;
        } else {
            return false;
        }
    }

    public int hashCode()
    {
        int result = 17;
        result = result * 31 + x;
        result = result * 31 + y;
        return result;
    }
}
