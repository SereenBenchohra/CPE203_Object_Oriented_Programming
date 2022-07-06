import java.awt.Color;
import java.awt.Graphics;

/**
 * A circle in 2D space that knows how to draw itself and calculate its area.
 */


public class Circle implements Shape 
{

    private Point center;
    private double radius;
    private Color color;

    public Circle(double radius, Point center, Color color) 
    {
        this.center = center;
        this.radius = radius;
        this.color = color;
    }

    public double getRadius()
    {
        return radius;    
    }

    public void setRadius(double radiusIn)
    {
        this.radius = radiusIn;    
    }

    public Point getCenter()
    {
        return center;
    }

    @Override
    public double getArea() 
    {
        return (double) (Math.PI * radius * radius);
    }

    @Override
    public double getPerimeter()
    {
        double perimeter = 2 *radius*Math.PI;
		return perimeter;    
    }
    
    @Override
    public void translate(double dx, double dy)
   	{
   		center.translatePoint(dx, dy);
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

