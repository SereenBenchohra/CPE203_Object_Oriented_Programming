import java.awt.Color;
import java.awt.Graphics;

class Triangle implements Shape
{
	
    private Point vertexA;
    private Point vertexB;
    private Point vertexC;
    private Color color;

    public Triangle( Point vertexA, Point vertexB, Point vertexC, Color color)
    {
        this.vertexA = vertexA;
        this.vertexB = vertexB; 
        this.vertexC = vertexC;
    }


    public Point getVertexA()
    {
        return vertexA;    
    }

    public Point getVertexB()
    {
        return vertexB;    
    }
   
    public Point getVertexC()
    {
        return vertexC;    
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

    @Override 
    public double getPerimeter()
    {
        double length1 = findDistance(vertexA, vertexB);
        double length2 = findDistance(vertexB, vertexC);
        double length3 = findDistance(vertexC, vertexA);
        double perimeter = length1 + length2 + length3;
        return perimeter;
    }

    private double findDistance( Point p1, Point p2)
    {
        double dx = p1.getX()- p2.getX();
        double dy = p1.getY()- p2.getY(); 
        double distance = Math.sqrt(dx*dx + dy*dy);
        return distance;        
   
    }

    @Override
    public double getArea()
    {
        double length1 = findDistance(vertexA, vertexB);
        double length2 = findDistance(vertexB, vertexC);
        double length3 = findDistance(vertexC, vertexA);
        double semiP = (length1 + length2 + length3) * 0.5;
        double area =
        Math.sqrt(semiP *(semiP-length1) * (semiP - length2)*(semiP - length3));
        return area;
    } 
    
    @Override
    public void translate(double dx, double dy)
   	{
   		vertexA.translatePoint(dx, dy);
        vertexB.translatePoint(dx, dy);
        vertexC.translatePoint(dx, dy);
   	}
	
	
}
