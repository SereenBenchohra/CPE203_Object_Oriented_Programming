
import java.util.List;
import edu.calpoly.spritely.Tile;
import java.util.Random;
import java.util.function.Predicate;

final class Vein extends EntityAction{

    private Random rand = new Random();

    public Vein(Point position, List<Tile> tiles, int actionPeriod)
    {
        super(position, tiles, actionPeriod);
    }

    @Override
    public void scheduleActions(EventSchedule eventSchedule, WorldModel world)
    {
        eventSchedule.scheduleEvent(this, new Activity(this, world), getActionPeriod());
    }

    @Override
    protected void executeActivity(WorldModel world,
                        EventSchedule eventSchedule)
    {
        Point openPt = world.findOpenAround(getPosition());

        if (openPt != null) {
            Ore ore = new Ore(openPt, VirtualWorld.oreTiles, 20000 + rand.nextInt(10000));
            world.addEntity(ore);
            ore.scheduleActions(eventSchedule, world);
        }

        eventSchedule.scheduleEvent(this,
            new Activity(this, world),
            getActionPeriod());
    }
}


