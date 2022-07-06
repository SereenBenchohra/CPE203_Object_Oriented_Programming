
import java.util.List;
import edu.calpoly.spritely.Tile;
import java.util.Random;

final class Ore extends EntityAction{

    private Random rand = new Random();

    public Ore(Point position, List<Tile> tiles, int actionPeriod)
    {
        super(position, tiles, actionPeriod);
    }


    @Override
    protected void executeActivity(WorldModel world,
                       EventSchedule eventSchedule)
    {
        Point pos = getPosition();    // store current position before removing

        world.removeEntity(this);
        eventSchedule.unscheduleAllEvents(this);

        OreBlob blob = new OreBlob(pos, VirtualWorld.blobTiles, getActionPeriod() / 4,
                                    50 + rand.nextInt(100));

        world.addEntity(blob);
        blob.scheduleActions(eventSchedule, world);
    }
    @Override
    public void scheduleActions(EventSchedule eventSchedule, WorldModel world)
    {
        eventSchedule.scheduleEvent(this, new Activity(this, world), getActionPeriod());
    }


}

 

