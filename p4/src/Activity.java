
import java.util.Random;
import java.util.List;
import edu.calpoly.spritely.Tile;

final public class Activity implements Action
{


    private Entity entity;
	private WorldModel world;

    public static final Random rand = new Random();

    public Activity(Entity entity, WorldModel world){
        this.entity = entity;
        this.world = world;
    }


	@Override
    public void executeAction(EventSchedule eventSchedule){

        executeActivityAction(eventSchedule);
   
    }


    private void
    executeActivityAction(EventSchedule eventSchedule)
    {

 		if(entity instanceof MinerFull){
			MinerFull minerf = (MinerFull)entity;
                        minerf.executeActivity(world,eventSchedule);
		}

 		else if(entity instanceof MinerNotFull){
			MinerNotFull minernf = (MinerNotFull)entity;
                        minernf.executeActivity(world,eventSchedule);
		}

 		else if(entity instanceof Ore){
			Ore ore = (Ore)entity;
                        ore.executeActivity(world,eventSchedule);
		}

 		else if(entity instanceof OreBlob){
			OreBlob oreblob = (OreBlob)entity;
                        oreblob.executeActivity(world,eventSchedule);
		}

 		else if(entity instanceof Quake){
			Quake quake = (Quake)entity;
                        quake.executeActivity(world,eventSchedule);
		}

 		else if(entity instanceof Vein){
			Vein vein = (Vein)entity;
                        vein.executeActivity(world,eventSchedule);
		}

		else {
            throw new UnsupportedOperationException(
                String.format("executeActivityAction not supported for %s",
                entity));
        }
    }

}

