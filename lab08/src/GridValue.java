
import java.awt.Color;
import java.io.File;
import java.io.IOException;

import edu.calpoly.spritely.Tile;
import edu.calpoly.spritely.ImageTile;
import edu.calpoly.spritely.SolidColorTile;

/**
 * An enum to represent the possible states of a grid. 
 * <p>
 * You might note that this enum is a little more involved than ones
 * you've seen to date.  You can look up enum in Horstmann to see
 * what's going on here, but in short, we define a spritely Tile
 * that's attached to each enum value,  That tile is used when we
 * display our grid.
 */
public enum GridValue { 
    BACKGROUND("grass.png", '.'),   // Grass; our path can occupy this grid
    OBSTACLE("obstacle.png", 'O'),  // Obstacle:  Path cannot occupy this grid
    START("wyvern.png", 'W'),       // Wyverin:  Our starting point
    GOAL(Color.blue, 'g'),          // Water:  our goal
    PATH("path.png", 'p'),          // This part of the grid is on our path
    SEARCHED("searched.png", '+');  // This square was searched, but is no 
                                    // longer on the path

    public final Tile tile;

    GridValue(String imageName, char text) {
        Tile t = null;
        try {
            t = new ImageTile(new File("images/" + imageName),
                              PathingMain.TILE_SIZE, text);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        this.tile = t;
    }

    GridValue(Color color, char text) {
        tile = new SolidColorTile(Color.blue, 'G');
    }
}
