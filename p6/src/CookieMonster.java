
import java.util.List;
import java.util.ArrayList;
import edu.calpoly.spritely.Tile;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.ToIntBiFunction;

final class CookieMonster extends EntityMoving
{

    private Random rand = new Random();

    public CookieMonster(Point position, List<Tile> tiles, int actionPeriod, int animationPeriod)
    {
        super(position, tiles, actionPeriod, animationPeriod);

    }


    @Override
    protected void executeActivity(WorldModel world,
                                EventSchedule eventSchedule)
    {
        OreBlob oreblob = (OreBlob)world.findNearest(
            this.getPosition(), (Entity e) -> e instanceof OreBlob);
        long nextPeriod = getActionPeriod();

        if (oreblob != null)
        {
            Point tgtPos = oreblob.getPosition();

            if (moveTo(world, oreblob, eventSchedule))
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
                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }



    @Override 
    protected Point nextPosition(WorldModel world, Point destPos)
    {
        final Predicate<Point> canPassThrough = point -> (world.withinBounds(point) && !world.isOccupied(point));

        final ToIntBiFunction<Point, Point> steps = (currPos, nextPos) -> currPos.distanceSquared(currPos, nextPos);

        SingleStepPathingStrategy path = new SingleStepPathingStrategy();

        List<Point> nxtPos = path.computePath(this.getPosition(),destPos,canPassThrough,(Point pt) -> diagNeighbors(pt),steps);
        
        if (nxtPos.isEmpty())
            return this.getPosition();
        else
            return nxtPos.get(0);       

    }

    public List<Point> diagNeighbors(Point point)
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


    @Override
    protected void
    scheduleActions(EventSchedule eventSchedule, WorldModel world)
    {
        eventSchedule.scheduleEvent(this,new Activity(this, world),getActionPeriod());
        eventSchedule.scheduleEvent(this,new Animation(this, 0), getAnimationPeriod());
    }


}


