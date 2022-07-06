
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
abstract class EntityMoving extends EntityAnimated
{

    public EntityMoving(Point position, List<Tile> tiles, int actionPeriod, int animationPeriod)
    {            
		super(position, tiles, actionPeriod, animationPeriod);
    }

	    abstract protected Point 
    nextPosition(WorldModel world, Point destPos);

	abstract protected boolean 
    moveTo(WorldModel world,
                  Entity target,  EventSchedule eventSchedule);
}
