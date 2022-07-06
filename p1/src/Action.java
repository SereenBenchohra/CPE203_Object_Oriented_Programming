
/**
 * An action data structure records information about
 * a future action an entity is to perform.  It 
 * is attached to an Event data structure.
 */

final class Action
{
    public ActionKind kind;
    public Entity entity;
    public WorldModel world;
    public int repeatCount;     // A repeat count of 0 means to repeat forever

    public Action(ActionKind kind, Entity entity, WorldModel world,
                  int repeatCount)
    {
        this.kind = kind;
        this.entity = entity;
        this.world = world;
        this.repeatCount = repeatCount;
    }
}
