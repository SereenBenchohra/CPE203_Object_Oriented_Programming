
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.ToIntBiFunction;

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

    protected Point
    nextPosition(WorldModel world, Point destPos)
    {
        SingleStepPathingStrategy singleStep = new SingleStepPathingStrategy();
        AStarPathingStrategy AstarPath = new AStarPathingStrategy();


        final Predicate<Point> canPass = point -> (!world.isOccupied(point) && world.withinBounds(point));


        final ToIntBiFunction <Point, Point> stepsFromTo = (p1, p2) -> p1.distanceSquared(p1,p2);


        List<Point> nextPos = AstarPath
                .computePath(super.getPosition(), destPos, canPass,
                (Point point) -> potentialNeighbors(point), stepsFromTo);

        if (nextPos.isEmpty())
            return super.getPosition();

        return nextPos.get(0);
    }
    public List<Point> potentialNeighbors(Point point)
    {
        Point Left = new Point(point.getX() - 1, point.getY() );
        Point Right = new Point(point.getX() + 1, point.getY());
        Point Up = new Point(point.getX() , point.getY() + 1);
        Point Down = new Point(point.getX() + 0, point.getY() - 1);

        List<Point> potentialNeighbor = new ArrayList<>();

        potentialNeighbor.add(Up);
        potentialNeighbor.add(Down);
        potentialNeighbor.add(Left);
        potentialNeighbor.add(Right);

        return potentialNeighbor;
    }


	abstract protected boolean 
    moveTo(WorldModel world,
                  Entity target,  EventSchedule eventSchedule);
                  

}
