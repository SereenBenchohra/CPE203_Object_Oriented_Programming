import java.util.List;
import java.util.ArrayList;
import java.awt.Color;

public class WorkSpace 
{
    private ArrayList<Shape> shapes = new ArrayList<Shape>();

    public void add(Shape shape)
    {
        shapes.add(shape);
    }

    public int size()
    {
       return shapes.size();
    }
    
    public Shape get(int Index)
    {
        return shapes.get(Index);    
    }
    public List<Circle> getCircles()
    {
    
        List<Circle> circles = new ArrayList<Circle>();       
        
        for(int i = 0; i < shapes.size(); i++)
        {   
            if(shapes.get(i) instanceof Circle)
            {
                Circle c = (Circle)shapes.get(i);                
                circles.add(c);
            }            
        }
        return circles;    
    }

    public List<Rectangle> getRectangles()
    {
    
        ArrayList<Rectangle> rect = new ArrayList<Rectangle>();      
        
        for(int i = 0; i < shapes.size(); i++)
        {   
            if(shapes.get(i) instanceof Rectangle)
            {
                Rectangle r = (Rectangle)shapes.get(i);                 
                rect.add(r);
            }            
        }
        return rect;    
    }

    public List<Triangle> getTriangles()
    {
    
        ArrayList<Triangle> tri = new ArrayList<Triangle>();      
        
        for(int i = 0; i < shapes.size(); i++)
        {   
            if(shapes.get(i) instanceof Triangle)
            {
                Triangle t = (Triangle)shapes.get(i);                
                tri.add(t);
            }            
        }
        return tri;    
    }

    public ArrayList<ConvexPolygon> getConvexPolygons()
    {
    
        ArrayList<ConvexPolygon> poly = new ArrayList<ConvexPolygon>();       
        
        for(int i = 0; i < shapes.size(); i++)
        {   
            if(shapes.get(i) instanceof ConvexPolygon)
            {
                ConvexPolygon p = (ConvexPolygon)shapes.get(i);                
                poly.add(p);
            }            
        }
        return poly;    
    }


    public List<Shape> getShapesByColor(Color color)
    {
        ArrayList<Shape> shapecolor = new ArrayList<Shape>();
        for(int i = 0; i < shapes.size(); i++)
        {   
            if(shapes.get(i).getColor() == color)
            {
                shapecolor.add(shapes.get(i));
            }            
        }
        return shapecolor;
    }

    public double getAreaOfAllShapes()
    {
        double area = 0;        
        for(int i = 0; i < shapes.size(); i++)
        {
            area += shapes.get(i).getArea();    
        }
        return area;    
    }

    public double getPerimeterOfAllShapes()
    {
        double perimeter = 0;        
        for(int i = 0; i < shapes.size(); i++)
        {
            perimeter += shapes.get(i).getPerimeter();    
        }
        return perimeter;  
    }

}
