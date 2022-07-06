import java.awt.*;
import java.awt.Color;

public interface Shape 
{


    public Color getColor();

    public void setColor(Color newColor);
    public double getArea();
    public double getPerimeter();
    public void translate(double dx, double dy);


}
