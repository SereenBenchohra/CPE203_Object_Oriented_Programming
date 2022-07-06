import java.util.List;
import edu.calpoly.spritely.Tile;


abstract class Entity implements Animatable
{

    private Point position;
    private List<Tile> tiles;
    private int tileIndex; 
     
    public Entity(Point position, List<Tile> tiles)
    {
        this.position = position;
        this.tiles = tiles;
        this.tileIndex = 0;
        
    }


	@Override
	public int getAnimationPeriod(){
            throw new UnsupportedOperationException(
                String.format("getAnimationPeriod not supported for %s"
                ));
	}

    @Override
    public Animation createAnimationAction(int repeatCount)
    {
        return new Animation(this, repeatCount);
    }


	@Override
    public void nextImage()
    {
        tileIndex = (tileIndex + 1) % getTiles().size();
    }


    public Point getPosition()
    {
        return position;
    }

    public List<Tile> getTiles()
    {
        return tiles;
    }

    public Tile getCurrentTile()
    {
        return tiles.get(tileIndex);
    }

    public void setPosition(Point position)
    {
        this.position = position;
    }


}
