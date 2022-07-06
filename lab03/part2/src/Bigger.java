class Bigger
{
    public static double whichIsBigger(Circle circle, Rectangle rect, Polygon poly)
    {
        double max = 0;        
        if(circle.perimeter() > rect.perimeter() && circle.perimeter() > poly.perimeter())
        {
          max = circle.perimeter() ;
                  
        }
        
        else if(rect.perimeter() > circle.perimeter() && rect.perimeter() > poly.perimeter())
        {
          max = rect.perimeter() ;
                  
        }
        
        else
        {
            max = poly.perimeter();        
        }
    
        return max;
   }
}
