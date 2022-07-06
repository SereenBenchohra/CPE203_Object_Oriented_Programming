
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import edu.calpoly.spritely.AnimationFrame;
import edu.calpoly.spritely.Tile;

/**
 * An entity in our virtual world.  An entity occupies a square
 * on the grid.  It might move around, and interact with other
 * entities in the world.
 */
abstract class EntityAnimated extends EntityAction
{
    public int animationPeriod;

    public EntityAnimated(Point position, List<Tile> tiles, int actionPeriod, int animationPeriod )
    {            
 	super(position, tiles, actionPeriod);
	this.animationPeriod = animationPeriod;
    }
    @Override
    public int getAnimationPeriod() // do not make it abstract
    {
         return animationPeriod;
    }


}
