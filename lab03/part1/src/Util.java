class Util
{

	public static double perimeter(Circle circle) 
	{
		double perimeter = 2 * circle.getRadius()*Math.PI;
		return perimeter;
	}

	public static double perimeter(Rectangle rectangle) 
	{
		double height = Math.abs(rectangle.getTopLeft().getY()-rectangle.getBottomRight().getY());		
		double width = Math.abs(rectangle.getTopLeft().getX()-rectangle.getBottomRight().getX());
		double perimeter = 2 * height + 2 * width;
		return perimeter;
	}
	
	public static double perimeter(Polygon polygon) 
	{
	
		double perimeter = 0;

        Point pLast = polygon.getPoints().get(polygon.getPoints().size()-1);
        Point pFirst = polygon.getPoints().get(0);
        perimeter += findDistance(pFirst, pLast);
 	
		for(int i = 1; i < polygon.getPoints().size(); i++)
		{
			
            Point p1 = polygon.getPoints().get(i-1);
            Point p2 = polygon.getPoints().get(i);

		   perimeter += findDistance(p1, p2);
           
			
		}
    


		return perimeter;
	}
    
    private static double findDistance( Point p1, Point p2)
    {
        double width = p1.getX()- p2.getX();
        double height = p1.getY()- p2.getY(); 
        double distance = Math.sqrt(width*width + height*height);
        return distance;        
   
    }
		
	

}
