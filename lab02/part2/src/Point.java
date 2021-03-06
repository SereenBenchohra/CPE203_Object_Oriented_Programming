class Point
{
    private final double x;
    private final double y;

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

    public double getRadius()
    {
        return Math.sqrt(x * x + y * y) ;
    }

    public double getAngle()
    {
        return Math.atan(y/x);
    }

    public Point rotate90()
    {
	Point newPoint = new Point(-y,x); 
	return newPoint;

    }
}

    
