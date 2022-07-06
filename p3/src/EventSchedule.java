
import java.util.ArrayList;
import java.util.List;

/**
 * This data stucture is used to hold the scheduled events that are queued
 * for later execution.
 * <p>
 * This event queue is used to drive all movement and animation within
 * the miner simulation.
 */
final class EventSchedule {

    /**
     * A list of all pending events.
     */
    private List<Event> pendingEvents;

    /**
     * The current time in ms, according to how far we've advanced.
     */
    private double currentTime;

    /**
     * Create a new EventSchedule.
     */
    public EventSchedule()
    {
        this.pendingEvents = new ArrayList<Event>();
        this.currentTime = 0.0;
    }

    /**
     * Get the time we've advanced to so far.  When an event's action is
     * being executed, this will be the time of that event.
     */
    public double getCurrentTime() {
        return currentTime;
    }

    /**
     * Schedule an event at or after the current time.
     *
     * @param target  The target of the event.  This can be used to
     *                unschedule all events for a given target.  It may
     *                be null.
     * @param action  The action to execute when the event is fired.
     * @param after   How far in the future, in ms.  Must be >= 0.
     *                The event will fire at getCurrentTime() + after.
     */
    public void scheduleEvent(Object target, Action action, double after) 
	{
        assert after >= 0;

		Event newEvent = new Event(action, after + getCurrentTime(), target);
		pendingEvents.add(newEvent);
		System.out.println("Pending Events " + pendingEvents.get(0));
	

    }

    /**
     * Remove all of the events for the given target from the list of
     * pending events.  This should remove every event where 
     * target.equals(event.getTarget()).  Only events with non-null
     * targets can be unscheduled with this method.
     */
    public void unscheduleAllEvents(Object target)
    {
        assert target != null;
	
        System.out.println("Testing Size : " + pendingEvents.size());
		
		List<Event> newList = new ArrayList<Event>();
			
		for(int i = 0; i < pendingEvents.size(); i ++ ) 
		{
			
			Event event = pendingEvents.get(i);
			Object eventTarget = event.getTarget();
			
			if(eventTarget != null && eventTarget.equals(target))
			{			
				newList.add(event);				

			}
			else if(eventTarget == null && target == null)
			{
				newList.add(event);
				
			} 

		
		System.out.println("Testing Size: " + pendingEvents.size());
		}

		pendingEvents.removeAll(newList);
    }


    /**
     * Execute all the pending events, in order, until we get to 
     * the time advanceToTime (inclusive).  When this method completes,
     * getCurrentTime() will return advanceToTime.
     */
    public void processEvents(double advanceToTime) // if anything goes wrong later check on this code
    {
        assert advanceToTime >= currentTime;
        
        if( !pendingEvents.isEmpty()) 
        {
	        while (currentTime <= advanceToTime && size() > 0) // 
	        {
	                  
					Event event = minEvent();
		            if( event.getTime() <= advanceToTime)
		            {

                        //event = minEvent();			            
           				currentTime = event.getTime();
		                pendingEvents.remove(event);
						event.execute(this);
		                System.out.println("Event: " + event.getTime());
		           
		
	                }
                    else
                    {
		                break;
		     		}
	        }

       } 

		this.currentTime = advanceToTime;
	
    }
		

    private Event minEvent()
    {
        Event minEvent = pendingEvents.get(0);
        for(int i = 0; i < pendingEvents.size(); i++)
        {
            if(minEvent.getTime() > pendingEvents.get(i).getTime())
            {
                minEvent = pendingEvents.get(i);
            }        
        }
        return minEvent;    
    }
    /**
     * Give the number of events currently scheduled.
     */
    public int size() {
        return pendingEvents.size();
    }
    

}
