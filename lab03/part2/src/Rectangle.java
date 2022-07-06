class Rectangle 
{
    private final Point topLeft;
    private final Point bottomRight;
    
    public Rectangle(Point topLeft, Point bottomRight)
    {
	    this.topLeft = topLeft;
	    this.bottomRight = bottomRight;
    }

    public Point getTopLeft()
    {
	    return topLeft;
    }

    public Point getBottomRight()
    {
	    return bottomRight;
    }


    public  double perimeter() 
	{
	    double height = Math.abs(topLeft.getY()-bottomRight.getY());		
		double width = Math.abs(topLeft.getX()-bottomRight.getX());
		double perimeter = 2 * height + 2 * width;
		return perimeter;
	}
	

}
