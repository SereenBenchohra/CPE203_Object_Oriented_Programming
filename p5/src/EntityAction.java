
import java.util.List;
import edu.calpoly.spritely.Tile;

abstract class EntityAction extends Entity
{

    private int actionPeriod;   

    public EntityAction(Point position, List<Tile> tiles, int actionPeriod)
    {
        super(position, tiles);
        this.actionPeriod = actionPeriod;
    }

    abstract protected void executeActivity(WorldModel world, EventSchedule eventSchedule);

    abstract void scheduleActions(EventSchedule eventSchedule, WorldModel world);

	public int getActionPeriod()
	{
        return actionPeriod;
   	}

}

