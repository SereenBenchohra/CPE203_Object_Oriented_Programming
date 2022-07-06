import java.util.Random;
final class Activity implements Action
{

    public Entity entity;
    public WorldModel world;

    public static final Random rand = new Random();
    public Activity(Entity entity, WorldModel world)
    {
        this.entity = entity;
        this.world  = world;
    }

    @Override
    public void executeAction(EventSchedule eventSchedule)
    {
        switch (entity.kind)
        {
            case MINER_FULL:
                executeMinerFullActivity(entity, world,
                        eventSchedule);
                break;

            case MINER_NOT_FULL:
                executeMinerNotFullActivity(entity, world,
                        eventSchedule);
                break;

            case ORE:
                executeOreActivity(entity, world, eventSchedule);
                break;

            case ORE_BLOB:
                executeOreBlobActivity(entity, world, eventSchedule);
                break;

            case QUAKE:
                executeQuakeActivity(entity, world, eventSchedule);
                break;

            case VEIN:
                executeVeinActivity(entity,world, eventSchedule);
                break;

            default:
                throw new UnsupportedOperationException(
                        String.format("executeActivityAction not supported for %s",
                                entity.kind));
        }

    }

    private void 
    executeMinerFullActivity(Entity entity, WorldModel world, 
                             EventSchedule eventSchedule)
    {
        Entity fullTarget 
            = world.findNearest(entity.position, EntityKind.BLACKSMITH);

        if (fullTarget != null  &&
            entity.moveToFull(world, fullTarget, eventSchedule))
        {
            entity.transformFull(world, eventSchedule);
        }
        else
        {
            eventSchedule.scheduleEvent(entity,
                entity.createActivityAction(world),
                entity.actionPeriod);
        }
    }

    private void 
    executeMinerNotFullActivity(Entity entity, WorldModel world, 
                                EventSchedule eventSchedule)
    {
        Entity notFullTarget = world.findNearest(entity.position,
            EntityKind.ORE);

        if (notFullTarget == null ||
            !entity.moveToNotFull(world, notFullTarget, eventSchedule) ||
            !entity.transformNotFull(world, eventSchedule))
        {
            eventSchedule.scheduleEvent(entity,
                entity.createActivityAction(world),
                entity.actionPeriod);
        }

    }

    private void 
    executeOreActivity(Entity entity, WorldModel world,
                       EventSchedule eventSchedule)
    {
        Point pos = entity.position;    // store current position before removing

        world.removeEntity(entity);
        eventSchedule.unscheduleAllEvents(entity);

        Entity blob = entity.createOreBlob(pos, entity.actionPeriod / 4,
                                    50 + rand.nextInt(100));

        world.addEntity(blob);
        blob.scheduleActions(eventSchedule, world);
    }

    private void 
    executeOreBlobActivity(Entity entity, WorldModel world,
                           EventSchedule eventSchedule)
    {
        Entity blobTarget = world.findNearest(
            entity.position, EntityKind.VEIN);
        long nextPeriod = entity.actionPeriod;

        if (blobTarget != null)
        {
            Point tgtPos = blobTarget.position;

            if (entity.moveToOreBlob(world, blobTarget, eventSchedule))
            {
                Entity quake = entity.createQuake(tgtPos);

                world.addEntity(quake);
                nextPeriod += entity.actionPeriod;
                quake.scheduleActions(eventSchedule, world);
            }
        }

        eventSchedule.scheduleEvent(entity,
            entity.createActivityAction(world),
            nextPeriod);
    }

    private void 
    executeQuakeActivity(Entity entity, WorldModel world, 
                         EventSchedule eventSchedule)
    {
        eventSchedule.unscheduleAllEvents(entity);
        world.removeEntity(entity);
    }

    private void 
    executeVeinActivity(Entity entity, WorldModel world,
                        EventSchedule eventSchedule)
    {
        Point openPt = world.findOpenAround(entity.position);

        if (openPt != null) {
            Entity ore = entity.createOre(openPt, 20000 + rand.nextInt(10000));
            world.addEntity(ore);
            ore.scheduleActions(eventSchedule, world);
        }

        eventSchedule.scheduleEvent(entity,
            entity.createActivityAction(world),
            entity.actionPeriod);
    }


}
