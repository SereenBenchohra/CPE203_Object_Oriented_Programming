
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.function.ToIntBiFunction;

import edu.calpoly.spritely.Size;

public class TestPathing {

    private final static String[] VERY_SIMPLE = {
        "S",
        ".",
        ".",
        "G"
    };
    private final static String[] VERY_SIMPLE_BLOCKED = {
        "S",
        ".",
        "X",
        "G"
    };

    private final static String[] SIMPLE = {
        "S.....",
        "......",
        "......",
        "......",
        "......",
        ".....G"
    };

    private final PathingStrategy strategy;
    private final DebugGrid debug;
    private final Size gridSize;
    private final Point start;
    private final Point goal;
    private final Set<Point> obstacles;
    private final Predicate<Point> canPassThrough;
    private final Function<Point, List<Point>> potentialNeighbors;
    private final ToIntBiFunction<Point, Point> stepsFromTo;

    /**
     * Create a test "world" to test a pathing algorithm.
     */
    public TestPathing(Size gridSize, Point start, Point goal, 
                      Set<Point> obstacles, PathingStrategy strategy)
    {
        this.gridSize = gridSize;
        this.start = start;
        this.goal = goal;
        this.obstacles = obstacles;
        this.strategy = strategy;
        this.debug = setupDebug();
        this.canPassThrough = setupCanPassThrough();
        this.potentialNeighbors = setupPotentialNeighbors();
        this.stepsFromTo = setupStepsFromTo();
    }

    /**
     * Create a test "world" to test a pathing algorithm.  This version
     * of the constructor takes a string array giving the layout, which can
     * be a convenient way to create smallish worlds.
     */
    public TestPathing(String[] layout, PathingStrategy strategy)
    {
        Point start = null;
        Point goal = null;
        this.gridSize = new Size(layout[0].length(), layout.length);
        this.obstacles = new HashSet<Point>();
        for (int y = 0; y < layout.length; y++) {
            System.out.println(layout[y]);
            assert gridSize.width == layout[y].length();
            for (int x = 0; x < layout[y].length(); x++) {
                char c = layout[y].charAt(x);
                if (c == 'S') {
                    assert start == null;
                    start = new Point(x, y);
                } else if (c == 'G') {
                    assert goal == null;
                    goal = new Point(x, y);
                } else if (c != '.') {
                    obstacles.add(new Point(x, y));
                }
            }
        }
        assert start != null;
        assert goal != null;
        this.start = start;
        this.goal = goal;
        this.strategy = strategy;
        this.debug = setupDebug();
        this.canPassThrough = setupCanPassThrough();
        this.potentialNeighbors = setupPotentialNeighbors();
        this.stepsFromTo = setupStepsFromTo();
    }

    //
    // Called from the constructors
    //
    private DebugGrid setupDebug() {
        if (DebugGrid.ENABLED) {
            DebugGrid debug = new DebugGrid(gridSize);
            int tileWidth = 1440 / gridSize.width;
            int tileHeight = 810 / gridSize.height;
            int tileSize = Math.max(1, Math.min(tileWidth, tileHeight));
            debug.setTileSize(new Size(tileSize, tileSize));
            debug.setFps(30.0);
            return debug;
        } else {
            return null;
        }
    }

    //
    // Create the canPassThrough predicate for our world.  This is
    // done as a method so we don't have to repeat the code in both
    // constructors.
    //
    private Predicate<Point> setupCanPassThrough() {
        return (Point p) -> {
            if (p.getX() < 0 || p.getX() >= gridSize.width) {
                return false;
            }
            if (p.getY() < 0 || p.getY() >= gridSize.height) {
                return false;
            }
            if (obstacles.contains(p)) {
                return false;
            }
            return true;
        };
    }

    //
    // Create the potentialNeighbors function for our world.  This is
    // done as a method so we don't have to repeat the code in both
    // constructors.
    //
    private Function<Point, List<Point>> setupPotentialNeighbors() {
        return (Point p) -> {
            ArrayList<Point> result = new ArrayList<>(4);
            result.add(new Point(p.getX()-1, p.getY()));
            result.add(new Point(p.getX()+1, p.getY()));
            result.add(new Point(p.getX(), p.getY()-1));
            result.add(new Point(p.getX(), p.getY()+1));
            return result;
        };
    }

    //
    // Create the stepsFromTo function for our world.  This is
    // done as a method so we don't have to repeat the code in both
    // constructors.
    //
    private ToIntBiFunction<Point, Point> setupStepsFromTo() {
        return (Point p1, Point p2) 
                -> Math.abs(p1.getX() - p2.getX()) 
                   + Math.abs(p1.getY() - p2.getY());
    }

    /**
     * Test the pathing algorithm on our test world.
     * Returns the number of steps to reach the goal, 0 if the
     * goal is not reached, or -steps if we fail after
     * taking some steps.
     */
    public int test() {
        if (DebugGrid.ENABLED && debug != null) {
            strategy.setDebugGrid(debug);
            debug.start();
        }
        long time = System.nanoTime();
        int steps = computeSteps();
        time = System.nanoTime() - time;
        System.out.println("*** " + steps + " steps in " 
                           + String.format("%.3f", time / 1000000.0) + " ms.");
        System.out.println();
        if (DebugGrid.ENABLED && debug != null) {
            debug.showFrame();
            debug.pause(2000);
            debug.stop();
            strategy.setDebugGrid(null);
        }
        return steps;
    }

    //
    // Populate the DebugGrid to reflect our current visited points, and the
    // rest of the world.
    //
    private void resetDebugGrid(Set<Point> visited) {
        assert DebugGrid.ENABLED && debug != null;
        for (int y = 0; y < gridSize.height; y++) {
            for (int x = 0; x < gridSize.width; x++) {
                Point pos = new Point(x, y);
                if (obstacles.contains(pos)) {
                    debug.setGridValue(pos, DebugGrid.OBSTACLE_TILE);
                } else {
                    debug.setGridValue(pos, DebugGrid.BACKGROUND_TILE);
                }
            }
        }
        for (Point p : visited) {
            if (!(p.equals(start))) {
                debug.setGridValue(p, DebugGrid.PATH_TILE);
            }
        }
        // Set the start after the visited, in case the start position
        // is in the visited set.
        debug.setGridValue(start, DebugGrid.START_TILE);
        debug.setGridValue(goal, DebugGrid.GOAL_TILE);
    }

    /**
     * Compute the steps from start to goal.
     * Returns the number of steps to reach the goal, 0 if the
     * goal is not reached, or -steps if we fail after
     * taking some steps.
     */
    public int computeSteps() {
        Set<Point> visited = new HashSet<Point>();
        int steps = 0;
        Point curr = start;
        steps = 0;
        visited.add(curr);

        while (!adjacent(curr, goal)) {
            if (DebugGrid.ENABLED && debug != null) {
                resetDebugGrid(visited);
                // Give ourselves a few frames on the starting configuration
                for (int i = 0; i < 15; i++) {
                    debug.showFrame();
                }
            }
            List<Point> path = strategy.
                    computePath(curr, goal, canPassThrough, 
                                potentialNeighbors, stepsFromTo);
            if (path == null || path.isEmpty()) {
                if (steps > 0) {
                    System.out.println("Unable to compute path after " 
                                        + steps + " steps.");
                }
                return -steps;
            }
            curr = path.get(0);
            if (visited.contains(curr)) {
                System.out.println("We went in a circle after " 
                                    + steps + " steps.");
                return -steps;
            }
            visited.add(curr);
            steps++;
        }
        if (DebugGrid.ENABLED && debug != null) {
            // Show the last step (the one that's adjacent to the goal)
            visited.add(curr);
            resetDebugGrid(visited);
            debug.showFrame();
        }
        return steps;
    }

    /**
     * Run all of our tests, and then exit.  This can be called from
     * your Main class.
     */
    public static void runTestsAndExit() {
        PathingStrategy strategy = new SingleStepPathingStrategy();
        (new TestPathing(VERY_SIMPLE, strategy)).test();
        (new TestPathing(VERY_SIMPLE_BLOCKED, strategy)).test();
        (new TestPathing(SIMPLE, strategy)).test();
        System.out.println();
        System.out.println("80x50 grid with no obstacles:");
        (new TestPathing(new Size(70, 50), new Point(1, 1),
                         new Point(68, 48), Collections.emptySet(),
                         strategy)
                ).test();
        System.out.println();
        System.exit(0);
    }

    //
    // We define adjacent here so we don't have to depend on
    // Point having this method.
    //
    private boolean adjacent(Point p1, Point p2) {
        return (p1.getX() == p2.getX() && Math.abs(p1.getY() - p2.getY()) == 1)
            || (p1.getY() == p2.getY() && Math.abs(p1.getX() - p2.getX()) == 1);
    }

    /**
     * A main method, so this class can act as a stand-alone test.
     */
    public static void main(String[] args) {
        // You should just call TestPathing.runTestsAndExit() from
        // your Main.main() method.  Make a command-line argument to
        // run the pathing unit tests, like "-path" -- that way, you can
        // use your project's normal run.sh, without modification.
        runTestsAndExit();
    }
}
