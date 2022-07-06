
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

    public static boolean adjacent(Point p1, Point p2) // see whether I need to include Point 1 passes the compiler
    {
        return (p1.getX() == p2.getX() && Math.abs(p1.getY() - p2.getY()) == 1)
            || (p1.getY() == p2.getY() && Math.abs(p1.getX() - p2.getX()) == 1);
    }

    public int distanceSquared(Point p1,Point p2)// see whether I need to include Point 1
    {
        int deltaX = p1.getX() - p2.getX();
        int deltaY = p1.getY() - p2.getY();

        return deltaX * deltaX + deltaY * deltaY;
    }


}
