
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


    @Override
    protected Point nextPosition(WorldModel world, Point destPos)
    {
        int horiz = Integer.signum(destPos.getX() - getPosition().getX());
        Point newPos = new Point(getPosition().getX() + horiz,
            getPosition().getY());

        if (horiz == 0 || world.isOccupied(newPos))
        {
            int vert = Integer.signum(destPos.getY() - getPosition().getY());
            newPos = new Point(getPosition().getX(),
                getPosition().getY() + vert);

            if (vert == 0 || world.isOccupied(newPos))
            {
                newPos = getPosition();
            }
        }

        return newPos;
    }

    abstract protected boolean transform(WorldModel world, EventSchedule eventSchedule);
}
