import java.util.Random;
/**
 * An action data structure records information about
 * a future action an entity is to perform.  It 
 * is attached to an Event data structure.
 */

public interface Animatable
{
    public Animation createAnimationAction(int repeatCount);
	public void nextImage();
	public int getAnimationPeriod();

	

}
