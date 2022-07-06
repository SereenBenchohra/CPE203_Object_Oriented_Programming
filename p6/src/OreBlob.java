
import java.util.List;
import edu.calpoly.spritely.Tile;

final class OreBlob extends EntityMoving{

    public OreBlob(Point position, List<Tile> tiles, int actionPeriod, int animationPeriod)
    {
        super(position,tiles,actionPeriod, animationPeriod);
    }


    @Override
    protected boolean moveTo(WorldModel world, Entity target, EventSchedule eventSchedule)
    {
        if (Point.adjacent(getPosition(), target.getPosition()))
        {
            world.removeEntity(target);
            eventSchedule.unscheduleAllEvents(target);
            return true;
        }
        else
        {
            Point nextPos = nextPosition(world, target.getPosition());

            if (!getPosition().equals(nextPos))
            {
                Entity occupant = world.getOccupant(nextPos);
                if (occupant != null)
                {
                    eventSchedule.unscheduleAllEvents(occupant);
                }

                world.moveEntity(this, nextPos); 
            }
            return false;
        }
    }



    @Override 
    protected void executeActivity(WorldModel world,
                           EventSchedule eventSchedule)
    {
        Vein v = (Vein)world.findNearest(
            this.getPosition(), (Entity e) -> e instanceof Vein);
        long nextPeriod = getActionPeriod();

        if (v != null)
        {
            Point tgtPos = v.getPosition();

            if (moveTo(world, v, eventSchedule))
            {
                Quake quake = new Quake(tgtPos, VirtualWorld.quakeTiles, getActionPeriod(), getAnimationPeriod());

                world.addEntity(quake);
                nextPeriod += getActionPeriod();
                quake.scheduleActions(eventSchedule, world);
            }
        }

        eventSchedule.scheduleEvent(this,
            new Activity(this, world),
            nextPeriod);
    }



    @Override
    public void
    scheduleActions(EventSchedule eventSchedule, WorldModel world)
    {
        eventSchedule.scheduleEvent(this,new Activity(this, world),getActionPeriod());
        eventSchedule.scheduleEvent(this,new Animation(this, 0), getAnimationPeriod());
    }


}
