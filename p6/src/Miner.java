
import java.util.List;
import edu.calpoly.spritely.Tile;

abstract class Miner extends EntityMoving{
 
    private int resourceLimit;
    private int resourceCount;

    public Miner(Point position, List<Tile> tiles, int actionPeriod, int animationPeriod, int resourceLimit, int resourceCount)
    {
        super(position, tiles, actionPeriod, animationPeriod);
	this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
    }

    public int getResourceLimit()
    {
        return resourceLimit;
    }

    public int getResourceCount()
    {
        return resourceCount;
    }

    public void addResourceCount()
    {
        resourceCount += 1;
    }

    public boolean NextToRiver(WorldModel world)
	{

        River river = (River)world.findNearest(this.getPosition(), (Entity e) -> e instanceof River);
        List<Point> neigh = potentialNeighbors(this.getPosition());
        
		if (river != null)
		{
            for (Point p : neigh)
			{
                
				if (Point.adjacent(p, river.getPosition()))
                    return true;
            }
        }

        return false;
              

              
    }



    abstract protected boolean transform(WorldModel world, EventSchedule eventSchedule);

	protected void transformToCookie(WorldModel world, EventSchedule eventSchedule)
	{
        CookieMonster cookie = new CookieMonster(getPosition(), VirtualWorld.cookieTiles, getActionPeriod(),
            getAnimationPeriod());

        world.removeEntity(this);
        eventSchedule.unscheduleAllEvents(this);

        world.addEntity(cookie);
        cookie.scheduleActions(eventSchedule, world);
		
	}

}
