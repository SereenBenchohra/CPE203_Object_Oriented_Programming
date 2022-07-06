import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import edu.calpoly.spritely.AnimationFrame;
import edu.calpoly.spritely.Tile;

final class Quake extends EntityAnimated{

    public Quake(Point position, List<Tile> tiles, int actionPeriod, int animationPeriod)
    {
        super(position,tiles,actionPeriod, animationPeriod);
    }


    @Override
    protected void executeActivity(WorldModel world,
                         EventSchedule eventSchedule)
    {
        eventSchedule.unscheduleAllEvents(this);
        world.removeEntity(this);
    }

    @Override 
    public void
    scheduleActions(EventSchedule eventSchedule, WorldModel world)
    {
        eventSchedule.scheduleEvent(this,new Activity(this, world),getActionPeriod());
        eventSchedule.scheduleEvent(this,new Animation(this, 10), getAnimationPeriod());
    }

}
