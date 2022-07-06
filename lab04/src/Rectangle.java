import java.awt.Color;
import java.awt.Graphics;

class Rectangle implements Shape
{
    private Point topLeft;
    private double width;
    private double height;
    private Color color;
    
    public Rectangle(double width, double height, Point topLeft, Color color)
    {
	    this.topLeft = topLeft;
	    this.width = width;
        this.height = height;
        this.color = color;
    }
 
    public void setWidth(double newWidth)
    {
        this.width = newWidth;
    }       
    
    public double getWidth()
    {
        return width;
    }

    public double getHeight()
    {
        return height;
    }

    public void setHeight(double newHeight)
    {
        this.height = newHeight;
    }

    public Point getTopLeft()
    {
        return topLeft;
    }

    @Override 
    public double getPerimeter() 
	{
		double perimeter = 2 * height + 2 * width;
		return perimeter;
	}

    @Override 
    public double getArea()
    {
        return height * width;
    }

    @Override
    public void translate(double dx, double dy) 
    {
        topLeft.translatePoint(dx, dy);
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
