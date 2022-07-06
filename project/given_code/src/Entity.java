import java.util.List;
import edu.calpoly.spritely.Tile;

/**
 * An entity in our virtual world.  An entity occupies a square
 * on the grid.  It might move around, and interact with other
 * entities in the world.
 */
final class Entity
{
    public EntityKind kind;
    public Point position;
    public List<Tile> tiles;
    public int tileIndex;       // Index into tiles for animation
    public int resourceLimit;
    public int resourceCount;
    public int actionPeriod;
    public int animationPeriod;

    public Entity(EntityKind kind, Point position,
                  List<Tile> tiles, int resourceLimit, int resourceCount,
                  int actionPeriod, int animationPeriod)
    {
        this.kind = kind;
        this.position = position;
        this.tiles = tiles;
        this.tileIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }
}
