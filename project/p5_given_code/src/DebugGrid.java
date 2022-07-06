
import java.awt.Color;

import edu.calpoly.spritely.Tile;
import edu.calpoly.spritely.SolidColorTile;
import edu.calpoly.spritely.Size;
import edu.calpoly.spritely.SpriteWindow;
import edu.calpoly.spritely.AnimationFrame;

/**
 * A debugging tool to give a displayable, animated grid.  This might
 * be useful for visualizing what's happening with the A* pathing
 * algorithm.  You shouldn't need to modify this file.
 * <p>
 * Usage:
 * <pre>
 *    Size size = new Size(... the size of the grid you want);
 *    DebugGrid grid = new DebugGrid(size);
 *    grid.setXXX(whatever);
 *    grid.start();
 *    ...  Run your algorithm.  Within it, possibly inside other
 *         methods, whenever you want to visualize grid data:
 *        if (DebugGrid.ENABLED) {
 *            for (each square in the grid that might have changed) {
 *                grid.setGridValue(...);
 *            }
 *            grid.showFrame();
 *        }
 *        ...
 *    }
 *    grid.stop();
 * <pre>
 * <p>
 * Debugging is turned on and off by changing the value of 
 * DebugGrid.ENABLED.  If ENABLED is false, this class will
 * do nothing, but it's still good practice to avoid calling
 * it in time-sensitive parts of your code if ENABLED is false.
 */

public class DebugGrid {

    public final static boolean ENABLED = true;

    public final static Tile BACKGROUND_TILE;
    public final static Tile OBSTACLE_TILE;
    public final static Tile START_TILE;
    public final static Tile PATH_TILE;
    public final static Tile GOAL_TILE;
    public final static Tile OPEN_SET_TILE;
    public final static Tile CLOSED_SET_TILE;

    static {
        if (ENABLED) {
            System.out.println("NOTE:  DebugGrid is enabled.  Be sure to disable debug before submitting.");
            BACKGROUND_TILE = new SolidColorTile(Color.GRAY, '.');
            OBSTACLE_TILE = new SolidColorTile(Color.BLUE, 'X');
            START_TILE = new SolidColorTile(Color.ORANGE, 'S');
            PATH_TILE = new SolidColorTile(Color.YELLOW, 'p');
            GOAL_TILE = new SolidColorTile(Color.CYAN, 'G');
            OPEN_SET_TILE = new SolidColorTile(Color.GREEN, 'o');
            CLOSED_SET_TILE = new SolidColorTile(Color.RED, 'c');
        } else {
            BACKGROUND_TILE = null;
            OBSTACLE_TILE = null;
            START_TILE = null;
            PATH_TILE = null;
            GOAL_TILE = null;
            OPEN_SET_TILE = null;
            CLOSED_SET_TILE = null;
        }
    }

    private Tile[][] grid;
    private SpriteWindow window;

    /**
     * Create a new debug grid of the given size.  It's OK to have
     * multiple debug grids active at the same time.
     */
    public DebugGrid(Size size) {
        if (ENABLED) {
            grid = new Tile[size.height][size.width];
            window = new SpriteWindow("DebugGrid", size);
            for (int y = 0; y < grid.length; y++) {
                Tile[] row = grid[y];
                for (int x = 0; x < row.length; x++) {
                    grid[y][x] = BACKGROUND_TILE;
                }
            }
        }
    }

    /**
     * Set how quickly the DebugGrid tries to animate, in
     * frames/second.
     */
    public void setFps(double fps) {
        if (ENABLED) {
            window.setFps(fps);
        }
    }

    /** 
     * Set the size of the tiles in the grid.  The size is given
     * in pixels.
     */
    public void setTileSize(Size tileSize) {
        if (ENABLED) {
            window.setTileSize(tileSize);
        }
    }

    /**
     * Show the grid, and start the animation.
     */
    public void start() {
        if (ENABLED) {
            window.start();
        }
    }

    /**
     * Set the tile to be shown at a given position.  It will be
     * shown the next time showFrame() is called.
     */
    public void setGridValue(Point pos, Tile value) {
        if (ENABLED) {
            grid[pos.getY()][pos.getX()] = value;
        }
    }

    /**
     * Show the next frame in the debug animation.
     */
    public void showFrame() {
        if (ENABLED) {
            AnimationFrame frame = window.waitForNextFrame();
            if (frame == null) {
                return;
            }
            for (int y = 0; y < grid.length; y++) {
                Tile[] row = grid[y];
                for (int x = 0; x < row.length; x++) {
                    Tile t = grid[y][x];
                    if (t != null) {
                        frame.addTile(x, y, grid[y][x]);
                    }
                }
            }
            window.showNextFrame();
        }
    }

    /**
     * Pause the current animation for the given number of
     * milliseconds.
     */
    public void pause(int milliseconds) {
        if (ENABLED) {
            window.pauseAnimation(milliseconds);
        }
    }

    /**
     * Stop the current animation.  Note that the user can
     * also stop the animation, by closing the window.
     */
    public void stop() {
        if (ENABLED) {
            window.stop();
        }
    }
}
