

import java.util.List;
import edu.calpoly.spritely.Tile;

final class MinerNotFull extends Miner{

    public MinerNotFull(Point position, List<Tile> tiles, int actionPeriod, int animationPeriod, int resourceLimit, int resourceCount)
    {
        super(position, tiles, actionPeriod, animationPeriod, resourceLimit, resourceCount);

    }


    @Override
    protected void executeActivity(WorldModel world,
                                EventSchedule eventSchedule)
    {
        if (NextToRiver(world)){
            transformToCookie(world,eventSchedule);
        }

        else{
            Ore ore = (Ore)world.findNearest(getPosition(), (Entity e) -> e instanceof Ore);

            if (ore == null ||
                !moveTo(world, ore, eventSchedule) ||
                !transform(world, eventSchedule))
            {
                eventSchedule.scheduleEvent(this,
                    new Activity(this, world),
                    getActionPeriod());
            }
        }
    }

    @Override
    protected boolean moveTo(WorldModel world, Entity target, EventSchedule eventSchedule)
    {
        if (Point.adjacent(getPosition(), target.getPosition()))
        {
            addResourceCount();
            world.removeEntity(target);
            eventSchedule.unscheduleAllEvents(target);

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
        if (getResourceCount() >= getResourceLimit())
        {
            MinerFull miner = new MinerFull(getPosition(), VirtualWorld.minerFullTiles, getActionPeriod(),
            getAnimationPeriod(), getResourceLimit(), getResourceCount());
            world.removeEntity(this);
            eventSchedule.unscheduleAllEvents(this);

            world.addEntity(miner);
            miner.scheduleActions(eventSchedule, world);

            return true;
        }


		return false;
    }


    @Override
    protected void
    scheduleActions(EventSchedule eventSchedule, WorldModel world)
    {
        eventSchedule.scheduleEvent(this,new Activity(this, world),getActionPeriod());
        eventSchedule.scheduleEvent(this,new Animation(this, 0), getAnimationPeriod());
    }


}
