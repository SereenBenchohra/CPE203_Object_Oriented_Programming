import java.util.Random;
final class Animation implements Action
{
	public ActionKind kind;
    public int repeatCount;     
	public Animatable anima;
    public static final Random rand = new Random();
    public Animation(Animatable anima, int repeatCount)
    {
 		this.anima = anima;
        this.repeatCount = repeatCount;
    }

    @Override
    public void executeAction(EventSchedule eventSchedule)
    {
        anima.nextImage();

        if (repeatCount != 1)
        {
            eventSchedule.scheduleEvent(anima,
                anima.createAnimationAction(
                    Math.max(repeatCount - 1, 0)),
                anima.getAnimationPeriod());
        }            
    
    }
}
