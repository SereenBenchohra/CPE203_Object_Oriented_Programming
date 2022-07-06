import edu.calpoly.spritely.SolidColorTile;
import java.awt.Color;
public final class DayNightAnimation implements Animatable
{

    private int animationPeriod = 100;
    private Color color = new Color(0, 255, 0, 0);
    public SolidColorTile tile = new SolidColorTile(color, 'a');


	
    @Override
    public Animation createAnimationAction(int repeatCount)
    {
        return new Animation(this,repeatCount);
    }

    @Override
    public void nextImage() 
    {
		double time = VirtualWorld.getTime();
		double newTime = ((((VirtualWorld.timeScale*time/1000) % 30))/30) * (2 * Math.PI); // maybe multiply by timescale
		double scaleTime = ((newTime/30) * 2 * Math.PI);
		int alpha = (int)(155 * (0.5 * (1-Math.cos(newTime))));
		this.color = new Color(0, 255, 0, alpha);
		this.tile = new SolidColorTile(color, 'a');

    }

    @Override
    public int getAnimationPeriod()
    {
        return animationPeriod;
    }
	
	
    public void scheduleActions(EventSchedule schedule)
    {
        schedule.scheduleEvent(this, createAnimationAction(0), this.getAnimationPeriod());
    }

    public SolidColorTile getTile() {
        return tile;
    }
}
