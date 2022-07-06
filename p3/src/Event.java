
/**
 * A timed event in the virtual world.  Events are queued, and then
 * executed when their time arrives.
 */
final class Event
{
    private final Action action;
    private final double time;
    private final Object target;

    public Event(Action action, double time, Object target)
    {
        this.action = action;
        this.time = time;
        this.target = target;
    }

    public double getTime() {
        return time;
    }

    public Object getTarget() {
        return target;
    }

    /**
     * Execute the given event.
     *
     * @param schedule  The EventSchedule that is executing this event.
     */
    public void execute(EventSchedule schedule) {

        // WARNING:  When you call this, it will cause other code 
        //           in the miner game to execute.
        //           What does that code do?  Specifically, is there
        //           anything to prevent it from calling methods of
        //           EventSchedule?  Think about the promises EventSchedule
        //           makes to the rest of the miner game, and make sure it
        //           keeps its promises while executing events!
        action.executeAction(schedule);
    }
}
