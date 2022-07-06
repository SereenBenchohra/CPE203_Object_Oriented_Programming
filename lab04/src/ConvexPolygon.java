import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.awt.Color;
import java.awt.Graphics;

class ConvexPolygon implements Shape
{
    private  Point[] points;
	private  Color color;
    
    public ConvexPolygon( Point[] points, Color color)
    {
        this.points = points;
		this.color = color;
    }
    
    public Point getVertex(int i)
    {   
       return points[i];   
    }
	
	public int getNumVertices()
	{
		return points.length;
	}

	@Override 
	public void translate(double dx, double dy)
	{
		for(int i = 0; i < points.length; i++)
		{
			points[i].translatePoint(dx, dy);		
		}
			
	} 
	@Override
	public double getPerimeter() 
	{
	
		double perimeter = 0;
        Point pLast = points[points.length-1];
        Point pFirst = points[0];
        perimeter += findDistance(pFirst, pLast);
 	
		for(int i = 1; i < points.length; i++)
		{
			
            Point p1 = points[i-1];
            Point p2 = points[i];

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

	@Override
	public double getArea()
	{
		double area = 0; 
		int len = points.length;
		for(int i = 0; i < len-1; i++)
		{
			area += points[i].x *points[i+1].y;
			area -= points[i].y *points[i+1].x;
		}	
			area += points[len-1].x *points[0].y; // see if this is necessary
			area -= points[len-1].y *points[0].x; // see if this is necessary

		return Math.abs(area/2);
	}

	@Override
	public Color getColor()
	{
		return color;	
	}

	@Override 
	public void setColor(Color newColor)
	{
		this.color = newColor;
	}
				
}
