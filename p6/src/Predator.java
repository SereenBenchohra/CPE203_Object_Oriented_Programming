
import java.util.List;
import edu.calpoly.spritely.Tile;
import java.util.function.ToIntBiFunction;
import java.util.ArrayList;
import java.util.function.Predicate;

final class Predator extends EntityMoving // Uses the code from Oreblob class
{

    public Predator(Point position, List<Tile> tiles, int actionPeriod, int animationPeriod)
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
        Obstacle o = (Obstacle)world.findNearest(
            this.getPosition(), (Entity e) -> e instanceof Obstacle);

        long nextPeriod = getActionPeriod();

        if (o != null)
        {
            Point tgtPos = o.getPosition();

            if (moveTo(world, o, eventSchedule))
            {
                Freeze freeze = new Freeze(tgtPos, VirtualWorld.freezeTiles, getActionPeriod(), getAnimationPeriod());

                world.addEntity(freeze);

                nextPeriod += getActionPeriod();

                freeze.scheduleActions(eventSchedule, world);
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

    @Override 
    protected Point nextPosition(WorldModel world, Point destPos)
    {
        final Predicate<Point> canPassThrough = point -> (world.withinBounds(point) && !world.isOccupied(point));

        final ToIntBiFunction<Point, Point> stepFromTo = (currPos, nextPos) -> currPos.distanceSquared(currPos, nextPos);

        AStarPathingStrategy Astar = new AStarPathingStrategy();

        List<Point> pointz = Astar.computePath(this.getPosition(),destPos,canPassThrough,(Point pt) -> diagnolNeighbors(pt),stepFromTo);
        
        if (pointz.isEmpty())
            return this.getPosition();
        else
            return pointz.get(0);       

    }

    public List<Point> diagnolNeighbors(Point point)
    {
        List<Point> diagNeighbors = new ArrayList<Point>();

        Point downRight = new Point(point.getX() + 1, point.getY() + 1);
        Point downLeft = new Point(point.getX() - 1, point.getY() + 1);
        Point upRight = new Point(point.getX() + 1, point.getY() - 1);
        Point upLeft = new Point(point.getX() - 1, point.getY() - 1);

        diagNeighbors.add(downRight);
        diagNeighbors.add(downLeft);
        diagNeighbors.add(upRight);
        diagNeighbors.add(upLeft);
        return diagNeighbors;

    }






}
