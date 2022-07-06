

/**
 * A very simple 2D point class, using doubles.
 */
public final class Point {

    //
    // It's usually considered OK to expose final instance variables of
    // a primitive type, when the class in question is a very simple data
    // holder like this.
    //
    public double x;
    public double y;

    public Point(double x, double y) 
	{
        this.x = x;
        this.y = y;
    }


    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }
	
	
	public void translatePoint(double dx, double dy)
	{
		this.x = x + dx;
		this.y = y + dy;
	}

    /**
     * determine if two points have the same x,y coordinates.
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Point) {
            Point op = (Point) other;
            return x == op.x && y == op.y;
        } else {
            return false;
        }
    }
}

