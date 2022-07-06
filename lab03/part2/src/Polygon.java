import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

class Polygon
{
    private final List<Point> points;
    
    public Polygon( List<Point> points)
    {
        this.points = points;
    }
    
    public List<Point> getPoints()
    {   
       return points;   
    }
 

	public double perimeter() 
	{
	
		double perimeter = 0;
        Point pLast = points.get(points.size()-1);
        Point pFirst = points.get(0);
        perimeter += findDistance(pFirst, pLast);
 	
		for(int i = 1; i < points.size(); i++)
		{
			
            Point p1 = points.get(i-1);
            Point p2 = points.get(i);

		   perimeter += findDistance(p1, p2);   
			
		}

		return perimeter;
	}
    
    private double findDistance( Point p1, Point p2)
    {
        double width = p1.getX()- p2.getX();
        double height = p1.getY()- p2.getY(); 
        double distance = Math.sqrt(width*width + height*height);
        return distance;        
   
    }
		
	
}
