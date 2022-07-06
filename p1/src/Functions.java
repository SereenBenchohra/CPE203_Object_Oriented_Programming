import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import edu.calpoly.spritely.AnimationFrame;
import edu.calpoly.spritely.Tile;

final class Functions
{
    public static final Random rand = new Random();

    public static int getAnimationPeriod(Entity entity)
    {
        switch (entity.kind)
        {
        case MINER_FULL:
        case MINER_NOT_FULL:
        case ORE_BLOB:
        case QUAKE:
            return entity.animationPeriod;
        default:
            throw new UnsupportedOperationException(
                String.format("getAnimationPeriod not supported for %s",
                entity.kind));
        }
    }

    public static void nextImage(Entity entity)
    {
        entity.tileIndex = (entity.tileIndex + 1) % entity.tiles.size();
    }

    public static Tile getCurrentTile(Entity entity)
    {
        return entity.tiles.get(entity.tileIndex);
    }

    public static void executeAction(Action action, EventSchedule eventSchedule)
    {
        switch (action.kind)
        {
        case ACTIVITY:
            executeActivityAction(action, eventSchedule);
            break;

        case ANIMATION:
            executeAnimationAction(action, eventSchedule);
            break;
        }
    }

    public static void 
    executeAnimationAction(Action action, EventSchedule eventSchedule)
    {
        nextImage(action.entity);

        if (action.repeatCount != 1)
        {
            eventSchedule.scheduleEvent(action.entity,
                createAnimationAction(action.entity,
                    Math.max(action.repeatCount - 1, 0)),
                getAnimationPeriod(action.entity));
        }
    }

    public static void 
    executeActivityAction(Action action, EventSchedule eventSchedule)
    {
        switch (action.entity.kind)
        {
        case MINER_FULL:
            executeMinerFullActivity(action.entity, action.world,
                                     eventSchedule);
            break;

        case MINER_NOT_FULL:
            executeMinerNotFullActivity(action.entity, action.world,
                                        eventSchedule);
            break;

        case ORE:
            executeOreActivity(action.entity, action.world, eventSchedule);
            break;

        case ORE_BLOB:
            executeOreBlobActivity(action.entity, action.world, eventSchedule);
            break;

        case QUAKE:
            executeQuakeActivity(action.entity, action.world, eventSchedule);
            break;

        case VEIN:
            executeVeinActivity(action.entity, action.world, eventSchedule);
            break;

        default:
            throw new UnsupportedOperationException(
                String.format("executeActivityAction not supported for %s",
                action.entity.kind));
        }
    }

    public static void 
    executeMinerFullActivity(Entity entity, WorldModel world, 
                             EventSchedule eventSchedule)
    {
        Entity fullTarget 
            = findNearest(world, entity.position, EntityKind.BLACKSMITH);

        if (fullTarget != null  &&
            moveToFull(entity, world, fullTarget, eventSchedule))
        {
            transformFull(entity, world, eventSchedule);
        }
        else
        {
            eventSchedule.scheduleEvent(entity,
                createActivityAction(entity, world),
                entity.actionPeriod);
        }
    }

    public static void 
    executeMinerNotFullActivity(Entity entity, WorldModel world, 
                                EventSchedule eventSchedule)
    {
        Entity notFullTarget = findNearest(world, entity.position,
            EntityKind.ORE);

        if (notFullTarget == null ||
            !moveToNotFull(entity, world, notFullTarget, eventSchedule) ||
            !transformNotFull(entity, world, eventSchedule))
        {
            eventSchedule.scheduleEvent(entity,
                createActivityAction(entity, world),
                entity.actionPeriod);
        }
    }

    public static void 
    executeOreActivity(Entity entity, WorldModel world,
                       EventSchedule eventSchedule)
    {
        Point pos = entity.position;    // store current position before removing

        removeEntity(world, entity);
        eventSchedule.unscheduleAllEvents(entity);

        Entity blob = createOreBlob(pos, entity.actionPeriod / 4,
                                    50 + rand.nextInt(100));

        addEntity(world, blob);
        scheduleActions(blob, eventSchedule, world);
    }

    public static void 
    executeOreBlobActivity(Entity entity, WorldModel world,
                           EventSchedule eventSchedule)
    {
        Entity blobTarget = findNearest(world,
            entity.position, EntityKind.VEIN);
        long nextPeriod = entity.actionPeriod;

        if (blobTarget != null)
        {
            Point tgtPos = blobTarget.position;

            if (moveToOreBlob(entity, world, blobTarget, eventSchedule))
            {
                Entity quake = createQuake(tgtPos);

                addEntity(world, quake);
                nextPeriod += entity.actionPeriod;
                scheduleActions(quake, eventSchedule, world);
            }
        }

        eventSchedule.scheduleEvent(entity,
            createActivityAction(entity, world),
            nextPeriod);
    }

    public static void 
    executeQuakeActivity(Entity entity, WorldModel world, 
                         EventSchedule eventSchedule)
    {
        eventSchedule.unscheduleAllEvents(entity);
        removeEntity(world, entity);
    }

    public static void 
    executeVeinActivity(Entity entity, WorldModel world,
                        EventSchedule eventSchedule)
    {
        Point openPt = findOpenAround(world, entity.position);

        if (openPt != null) {
            Entity ore = createOre(openPt, 20000 + rand.nextInt(10000));
            addEntity(world, ore);
            scheduleActions(ore, eventSchedule, world);
        }

        eventSchedule.scheduleEvent(entity,
            createActivityAction(entity, world),
            entity.actionPeriod);
    }

    public static void 
    scheduleActions(Entity entity,  EventSchedule eventSchedule, 
                    WorldModel world)
    {
        switch (entity.kind)
        {
        case MINER_FULL:
            eventSchedule.scheduleEvent(entity,
                createActivityAction(entity, world),
                entity.actionPeriod);
            eventSchedule.scheduleEvent(entity, 
                                        createAnimationAction(entity, 0),
                                        getAnimationPeriod(entity));
            break;

        case MINER_NOT_FULL:
            eventSchedule.scheduleEvent(entity,
                createActivityAction(entity, world),
                entity.actionPeriod);
            eventSchedule.scheduleEvent(entity,
                createAnimationAction(entity, 0), getAnimationPeriod(entity));
            break;

        case ORE:
            eventSchedule.scheduleEvent(entity,
                createActivityAction(entity, world),
                entity.actionPeriod);
            break;

        case ORE_BLOB:
            eventSchedule.scheduleEvent(entity,
                createActivityAction(entity, world),
                entity.actionPeriod);
            eventSchedule.scheduleEvent(entity,
                createAnimationAction(entity, 0), getAnimationPeriod(entity));
            break;

        case QUAKE:
            eventSchedule.scheduleEvent(entity,
                createActivityAction(entity, world),
                entity.actionPeriod);
            eventSchedule.scheduleEvent(entity,
                createAnimationAction(entity, 10),
                getAnimationPeriod(entity));
            break;

        case VEIN:
            eventSchedule.scheduleEvent(entity,
                createActivityAction(entity, world),
                entity.actionPeriod);
            break;

        default:
        }
    }

    public static boolean 
    transformNotFull(Entity entity, WorldModel world,
                     EventSchedule eventSchedule)
    {
        if (entity.resourceCount >= entity.resourceLimit)
        {
            Entity miner = createMinerFull(entity.resourceLimit,
                entity.position, entity.actionPeriod, entity.animationPeriod);

            removeEntity(world, entity);
            eventSchedule.unscheduleAllEvents(entity);

            addEntity(world, miner);
            scheduleActions(miner, eventSchedule, world);

            return true;
        }

        return false;
    }

    public static void 
    transformFull(Entity entity, WorldModel world, EventSchedule eventSchedule)
    {
        Entity miner = createMinerNotFull(entity.resourceLimit,
            entity.position, entity.actionPeriod, entity.animationPeriod);

        removeEntity(world, entity);
        eventSchedule.unscheduleAllEvents(entity);

        addEntity(world, miner);
        scheduleActions(miner, eventSchedule, world);
    }

    public static boolean 
    moveToNotFull(Entity miner, WorldModel world,
                  Entity target,  EventSchedule eventSchedule)
    {
        if (adjacent(miner.position, target.position))
        {
            miner.resourceCount += 1;
            removeEntity(world, target);
            eventSchedule.unscheduleAllEvents(target);

            return true;
        }
        else
        {
            Point nextPos = nextPositionMiner(miner, world, target.position);

            if (!miner.position.equals(nextPos))
            {
                moveEntity(world, miner, nextPos);
            }
            return false;
        }
    }

    public static boolean 
    moveToFull(Entity miner, WorldModel world,
               Entity target,  EventSchedule eventSchedule)
    {
        if (adjacent(miner.position, target.position))
        {
            return true;
        }
        else
        {
            Point nextPos = nextPositionMiner(miner, world, target.position);

            if (!miner.position.equals(nextPos))
            {
                moveEntity(world, miner, nextPos);
            }
            return false;
        }
    }

    public static boolean 
    moveToOreBlob(Entity blob, WorldModel world,
                  Entity target,  EventSchedule eventSchedule)
    {
        if (adjacent(blob.position, target.position))
        {
            removeEntity(world, target);
            eventSchedule.unscheduleAllEvents(target);
            return true;
        }
        else
        {
            Point nextPos = nextPositionOreBlob(blob, world, target.position);

            if (!blob.position.equals(nextPos))
            {
                Entity occupant = getOccupant(world, nextPos);
                if (occupant != null)
                {
                    eventSchedule.unscheduleAllEvents(occupant);
                }

                moveEntity(world, blob, nextPos);
            }
            return false;
        }
    }

    public static Point 
    nextPositionMiner(Entity entity, WorldModel world, Point destPos)
    {
        int horiz = Integer.signum(destPos.getX() - entity.position.getX());
        Point newPos = new Point(entity.position.getX() + horiz,
            entity.position.getY());

        if (horiz == 0 || isOccupied(world, newPos))
        {
            int vert = Integer.signum(destPos.getY() - entity.position.getY());
            newPos = new Point(entity.position.getX(),
                entity.position.getY() + vert);

            if (vert == 0 || isOccupied(world, newPos))
            {
                newPos = entity.position;
            }
        }

        return newPos;
    }

    public static Point 
    nextPositionOreBlob(Entity entity, WorldModel world, Point destPos)
    {
        int horiz = Integer.signum(destPos.getX() - entity.position.getX());
        Point newPos = new Point(entity.position.getX() + horiz,
            entity.position.getY());

        Entity occupant = getOccupant(world, newPos);

        if (horiz == 0 ||
            (occupant != null && !(occupant.kind == EntityKind.ORE)))
        {
            int vert = Integer.signum(destPos.getY() - entity.position.getY());
            newPos = new Point(entity.position.getX(), 
                               entity.position.getY() + vert);
            occupant = getOccupant(world, newPos);

            if (vert == 0 ||
                (occupant != null && !(occupant.kind == EntityKind.ORE)))
            {
                newPos = entity.position;
            }
        }

        return newPos;
    }

    public static boolean adjacent(Point p1, Point p2)
    {
        return (p1.getX() == p2.getX() && Math.abs(p1.getY() - p2.getY()) == 1)
            || (p1.getY() == p2.getY() && Math.abs(p1.getX() - p2.getX()) == 1);
    }

    public static Point findOpenAround(WorldModel world, Point pos)
    {
        for (int dy = -1; dy <= 1; dy++)
        {
            for (int dx = -1; dx <= 1; dx++)
            {
                Point newPt = new Point(pos.getX() + dx, pos.getY() + dy);
                if (withinBounds(world, newPt) &&
                    !isOccupied(world, newPt))
                {
                    return newPt;
                }
            }
        }

        return null;
    }

    public static boolean withinBounds(WorldModel world, Point pos)
    {
        return pos.getY() >= 0 && pos.getY() < world.size.height &&
            pos.getX() >= 0 && pos.getX() < world.size.width;
    }

    public static boolean isOccupied(WorldModel world, Point pos)
    {
        return withinBounds(world, pos) && getOccupantCell(world, pos) != null;
    }

    public static Entity nearestEntity(List<Entity> entities, Point pos)
    {
        if (entities.isEmpty()) {
            return null;
        } else {
            Entity nearest = entities.get(0);
            int nearestDistance = distanceSquared(nearest.position, pos);

            for (Entity other : entities)
            {
                int otherDistance = distanceSquared(other.position, pos);

                if (otherDistance < nearestDistance)
                {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }

            return nearest;
        }
    }

    public static int distanceSquared(Point p1, Point p2)
    {
        int deltaX = p1.getX() - p2.getX();
        int deltaY = p1.getY() - p2.getY();

        return deltaX * deltaX + deltaY * deltaY;
    }

    public static Entity findNearest(WorldModel world, Point pos,
        EntityKind kind)
    {
        List<Entity> ofType = new LinkedList<>();
        for (Entity entity : world.entities)
        {
            if (entity.kind == kind)
            {
                ofType.add(entity);
            }
        }

        return nearestEntity(ofType, pos);
    }

    /*
        Assumes that there is no entity currently occupying the
        intended destination cell.
    */
    public static void addEntity(WorldModel world, Entity entity)
    {
        if (withinBounds(world, entity.position))
        {
            setOccupantCell(world, entity.position, entity);
            world.entities.add(entity);
        }
    }

    public static void moveEntity(WorldModel world, Entity entity, Point pos)
    {
        Point oldPos = entity.position;
        if (withinBounds(world, pos) && !pos.equals(oldPos))
        {
            setOccupantCell(world, oldPos, null);
            removeEntityAt(world, pos);
            setOccupantCell(world, pos, entity);
            entity.position = pos;
        }
    }

    public static void removeEntity(WorldModel world, Entity entity)
    {
        removeEntityAt(world, entity.position);
    }

    public static void removeEntityAt(WorldModel world, Point pos)
    {
        if (withinBounds(world, pos)
            && getOccupantCell(world, pos) != null)
        {
            Entity entity = getOccupantCell(world, pos);

            /* this moves the entity just outside of the grid for
                debugging purposes */
            entity.position = new Point(-1, -1);
            world.entities.remove(entity);
            setOccupantCell(world, pos, null);
        }
    }

    public static Entity getOccupant(WorldModel world, Point pos)
    {
        if (isOccupied(world, pos)) {
            return getOccupantCell(world, pos);
        } else { 
            return null;
        }
    }

    public static Entity getOccupantCell(WorldModel world, Point pos)
    {
        return world.occupant[pos.getY()][pos.getX()];
    }

    public static void 
    setOccupantCell(WorldModel world, Point pos, Entity entity)
    {
        world.occupant[pos.getY()][pos.getX()] = entity;
    }

    public static Action createAnimationAction(Entity entity, int repeatCount)
    {
        return new Action(ActionKind.ANIMATION, entity, null, repeatCount);
    }

    public static Action createActivityAction(Entity entity, WorldModel world)
    {
        return new Action(ActionKind.ACTIVITY, entity, world, 0);
    }

    public static Entity createBlacksmith(Point position)
    {
        return new Entity(EntityKind.BLACKSMITH, position, 
                          VirtualWorld.blacksmithTiles, 0, 0, 0, 0);
    }

    public static Entity 
    createMinerFull(int resourceLimit, Point position, 
                    int actionPeriod, int animationPeriod)
    {
        return new Entity(EntityKind.MINER_FULL, position, 
                          VirtualWorld.minerTiles,
                          resourceLimit, resourceLimit, actionPeriod, 
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

    public static Entity 
    createOre(Point position, int actionPeriod)
    {
        return new Entity(EntityKind.ORE, position, 
                          VirtualWorld.oreTiles, 0, 0, actionPeriod, 0);
    }

    public static Entity 
    createOreBlob(Point position, int actionPeriod, int animationPeriod)
    {
        return new Entity(EntityKind.ORE_BLOB, position, 
                          VirtualWorld.blobTiles,
                          0, 0, actionPeriod, animationPeriod);
    }

    public static Entity createQuake(Point position)
    {
        return new Entity(EntityKind.QUAKE, position, 
                          VirtualWorld.quakeTiles, 0, 0, 1100, 100);
    }

    public static Entity createVein(Point position, int actionPeriod)
    {
        return new Entity(EntityKind.VEIN, position, 
                          VirtualWorld.veinTiles, 0, 0, actionPeriod, 0);
    }

    /** 
     * Paint the WorldModel to frame, by adding tiles to frame's
     * grid.
     */
    public static void paint(WorldModel model, AnimationFrame frame) {
        for (int y = 0; y < model.size.height; y++) {
            for (int x = 0; x < model.size.width; x++) {
                frame.addTile(x, y, model.background[y][x]);
                Entity occupant = model.occupant[y][x];
                if (occupant != null) {
                    Tile tile = getCurrentTile(occupant);
                    frame.addTile(x, y, tile);
                }
            }
        }
    }
}
