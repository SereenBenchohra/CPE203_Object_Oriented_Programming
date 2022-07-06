
import java.util.List;
import edu.calpoly.spritely.Tile;

final class MinerFull extends Miner{


    public MinerFull(Point position, List<Tile> tiles, int actionPeriod, int animationPeriod, int resourceLimit, int resourceCount)
    {
        super(position, tiles, actionPeriod, animationPeriod, resourceLimit, resourceCount);

    }

    @Override
    protected void
    executeActivity(WorldModel world,
                             EventSchedule eventSchedule)
    {
        Blacksmith b = (Blacksmith)world.findNearest(getPosition(), (Entity e) -> e instanceof Blacksmith);

        if (b != null  &&
            moveTo(world, b, eventSchedule))
        {
            transform(world, eventSchedule);
        }
        else
        {
            eventSchedule.scheduleEvent(this,
                new Activity(this, world),
                getActionPeriod());
        }
    }


    @Override
    protected boolean moveTo(WorldModel world, Entity target, EventSchedule eventSchedule)
    {
        if (Point.adjacent(getPosition(), target.getPosition()))
        {
            return true;
        }
        else
        {
            Point nextPos = nextPosition(world, target.getPosition());

            if (!getPosition().equals(nextPos))
            {
                world.moveEntity(this, nextPos); 
            }
            return false;
        }
    }

    @Override
    protected boolean transform(WorldModel world, EventSchedule eventSchedule)
    {
        MinerNotFull miner = new MinerNotFull(getPosition(), VirtualWorld.minerTiles, getActionPeriod(),
            getAnimationPeriod(), getResourceLimit(), getResourceCount());

        world.removeEntity(this);
        eventSchedule.unscheduleAllEvents(this);

        world.addEntity(miner);
        miner.scheduleActions(eventSchedule, world);

	return true;
    }
    @Override
    public void
    scheduleActions(EventSchedule eventSchedule, WorldModel world)
    {
        eventSchedule.scheduleEvent(this,new Activity(this, world),getActionPeriod());
        eventSchedule.scheduleEvent(this,new Animation(this, 0), getAnimationPeriod());
    }



}
