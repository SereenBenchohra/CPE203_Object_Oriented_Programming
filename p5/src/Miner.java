
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



    abstract protected boolean transform(WorldModel world, EventSchedule eventSchedule);
}
