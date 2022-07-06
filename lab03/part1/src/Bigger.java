class Bigger
{
    public static double whichIsBigger(Circle circle, Rectangle rect, Polygon poly)
    {
        double max = 0;        
        if(Util.perimeter(circle) > Util.perimeter(rect) && Util.perimeter(circle) > Util.perimeter(poly))
        {
          max = Util.perimeter(circle) ;
                  
        }
        
        else if(Util.perimeter(rect) > Util.perimeter(circle) && Util.perimeter(rect) > Util.perimeter(poly))
        {
          max = Util.perimeter(rect) ;
                  
        }
        
        else
        {
            max = Util.perimeter(poly);        
        }
    
        return max;
   }
}
