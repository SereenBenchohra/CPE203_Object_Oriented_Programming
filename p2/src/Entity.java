
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
final class Entity
{
    public EntityKind kind;
    public Point position;
    public List<Tile> tiles;
    public int tileIndex;       // Index into tiles for animation
    public int resourceLimit;
    public int resourceCount;
    public int actionPeriod;
    public int animationPeriod;

    public Entity(EntityKind kind, Point position,
                  List<Tile> tiles, int resourceLimit, int resourceCount,
                  int actionPeriod, int animationPeriod)
    {
        this.kind = kind;
        this.position = position;
        this.tiles = tiles;
        this.tileIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }

    public int getAnimationPeriod()
    {
        switch (kind)
        {
            case MINER_FULL:
            case MINER_NOT_FULL:
            case ORE_BLOB:
            case QUAKE:
                return animationPeriod;
            default:
                throw new UnsupportedOperationException(
                    String.format("getAnimationPeriod not supported for %s",
                    kind));
        }
    }

    public void nextImage() 
    {
        tileIndex = (this.tileIndex + 1) % tiles.size();
    }

    public Tile getCurrentTile() 
    {
        return this.tiles.get(this.tileIndex);
    }

    public void scheduleActions(EventSchedule eventSchedule, WorldModel world)
    {
        switch (kind)
        {
        case MINER_FULL:
            eventSchedule.scheduleEvent(this,
                createActivityAction(world),
                    actionPeriod);
            eventSchedule.scheduleEvent(this,
                createAnimationAction(0), getAnimationPeriod());
            break;

        case MINER_NOT_FULL:
            eventSchedule.scheduleEvent(this,
                createActivityAction(world),
                    actionPeriod);
            eventSchedule.scheduleEvent(this,
                createAnimationAction(0), getAnimationPeriod());
            break;

        case ORE:
            eventSchedule.scheduleEvent(this,
                createActivityAction(world),
                    actionPeriod);
            break;

        case ORE_BLOB:
            eventSchedule.scheduleEvent(this,
                createActivityAction(world),
                    actionPeriod);
            eventSchedule.scheduleEvent(this,
                createAnimationAction(0), getAnimationPeriod());
            break;

        case QUAKE:
            eventSchedule.scheduleEvent(this,
                createActivityAction(world),
                    actionPeriod);
            eventSchedule.scheduleEvent(this,
                createAnimationAction(10),
                getAnimationPeriod());
            break;

        case VEIN:
            eventSchedule.scheduleEvent(this,
                createActivityAction(world),
                    actionPeriod);
            break;

        default:
        }
    }

    public boolean 
    transformNotFull(WorldModel world,
                     EventSchedule eventSchedule)
    {
        if (resourceCount >= resourceLimit)
        {
            Entity miner = createMinerFull(resourceLimit, 
                position, actionPeriod, animationPeriod);

            world.removeEntity(this);
            eventSchedule.unscheduleAllEvents(this);

            world.addEntity(miner);
            miner.scheduleActions(eventSchedule, world);

            return true;
        }

        return false;
    }

    public void 
    transformFull(WorldModel world, EventSchedule eventSchedule)
    {
        Entity miner = createMinerNotFull(resourceLimit,
            position, actionPeriod, animationPeriod);

        world.removeEntity(this);
        eventSchedule.unscheduleAllEvents(this);

        world.addEntity(miner);			// miner changed from this
        miner.scheduleActions(eventSchedule, world);
    }

    public boolean 
    moveToNotFull(WorldModel world,
                  Entity target,  EventSchedule eventSchedule)
    {
        if (Point.adjacent(position, target.position))
        {
            resourceCount += 1;
            world.removeEntity(target);
            eventSchedule.unscheduleAllEvents(target);

            return true;
        }
        else
        {
            Point nextPos = nextPositionMiner(world, target.position);

            if (!position.equals(nextPos))
            {
                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    public boolean 
    moveToFull(WorldModel world,
               Entity target,  EventSchedule eventSchedule)
    {
        if (Point.adjacent(position, target.position))
        {
            return true;
        }
        else
        {
            Point nextPos = nextPositionMiner(world, target.position);

            if (!position.equals(nextPos))
            {
                world.moveEntity(this, nextPos); //was originally this
            }
            return false;
        }
    }

    public boolean 
    moveToOreBlob(WorldModel world,
                  Entity target,  EventSchedule eventSchedule)
    {
        if (Point.adjacent(position,target.position))
        {
            world.removeEntity(target);
            eventSchedule.unscheduleAllEvents(target);
            return true;
        }
        else
        {
            Point nextPos = nextPositionOreBlob(world, target.position);

            if (!position.equals(nextPos))
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

    private Point 
    nextPositionMiner(WorldModel world, Point destPos)
    {
        int horiz = Integer.signum(destPos.getX() - position.getX());
        Point newPos = new Point(position.getX() + horiz,
                position.getY());

        if (horiz == 0 || world.isOccupied(newPos))
        {
            int vert = Integer.signum(destPos.getY() - position.getY());
            newPos = new Point(position.getX(),
                    position.getY() + vert);

            if (vert == 0 || world.isOccupied(newPos))
            {
                newPos = position;
            }
        }

        return newPos;
    }

    private Point 
    nextPositionOreBlob(WorldModel world, Point destPos)
    {
        int horiz = Integer.signum(destPos.getX() - position.getX());
        Point newPos = new Point(position.getX() + horiz,
            position.getY());

        Entity occupant = world.getOccupant(newPos);

        if (horiz == 0 ||
            (occupant != null && !(occupant.kind == EntityKind.ORE)))
        {
            int vert = Integer.signum(destPos.getY() - position.getY());
            newPos = new Point(position.getX(), 
                                position.getY() + vert);
            occupant = world.getOccupant(newPos);

            if (vert == 0 ||
                (occupant != null && !(occupant.kind == EntityKind.ORE)))
            {
                newPos = position;
            }
        }

        return newPos;
    }

    public Action createAnimationAction(int repeatCount)
    {
        return new Action(ActionKind.ANIMATION, this, null, repeatCount);
    }

    public Action createActivityAction(WorldModel world)
    {
        return new Action(ActionKind.ACTIVITY, this, world, 0);
    }

    public static Entity createBlacksmith(Point position)
    {
        return new Entity(EntityKind.BLACKSMITH, position, 
                          VirtualWorld.blacksmithTiles, 0, 0, 0, 0);
    }

    public Entity 
    createMinerFull(int resourceLimit, Point position, 
                    int actionPeriod, int animationPeriod)
    {
        return new Entity(EntityKind.MINER_FULL, position, 
                          VirtualWorld.minerFullTiles,
                          resourceLimit,resourceLimit, actionPeriod, 
                          animationPeriod);
    }

    public static Entity 
    createMinerNotFull(int resourceLimit, Point position, int actionPeriod, 
                       int animationPeriod)
    {
        return new Entity(EntityKind.MINER_NOT_FULL, position, 
                          VirtualWorld.minerTiles,
                          resourceLimit, 0, actionPeriod, animationPeriod);
    }

    public static Entity 
    createObstacle(Point position)
    {
        return new Entity(EntityKind.OBSTACLE, position, 
                          VirtualWorld.obstacleTiles, 0, 0, 0, 0);
    }

    public Entity 
    createOre(Point position, int actionPeriod)
    {
        return new Entity(EntityKind.ORE, position, 
                          VirtualWorld.oreTiles, 0, 0, actionPeriod, 0);
    }

    public Entity 
    createOreBlob(Point position, int actionPeriod, int animationPeriod)
    {
        return new Entity(EntityKind.ORE_BLOB, position, 
                          VirtualWorld.blobTiles,
                          0, 0, actionPeriod, animationPeriod);
    }

    public Entity createQuake(Point position)
    {
        return new Entity(EntityKind.QUAKE, position, 
                          VirtualWorld.quakeTiles, 0, 0, 1100, 100);
    }

    public static Entity createVein(Point position, int actionPeriod)
    {
        return new Entity(EntityKind.VEIN, position, 
                          VirtualWorld.veinTiles, 0, 0, actionPeriod, 0);
    }
	
	
    public Point getPosition()
    {
        return position;
    }
}
