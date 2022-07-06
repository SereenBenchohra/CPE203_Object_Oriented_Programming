import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import edu.calpoly.spritely.Size;
import edu.calpoly.spritely.Tile;

/**
 * Data structures that hold the model of our virtual world.
 * It consists of a grid.  Each point on the grid is occupied
 * by a background tile, and, optionally, an Entity.
 */
final class WorldModel
{
    public final Size size;
    public final Tile background[][];
    public final Entity occupant[][];
    public final Set<Entity> entities;

    public WorldModel(Size gridSize)
    {
        this.size = gridSize;
        this.background = new Tile[gridSize.height][gridSize.width];
        this.occupant = new Entity[gridSize.height][gridSize.width];
        this.entities = new HashSet<Entity>();
    }
}
