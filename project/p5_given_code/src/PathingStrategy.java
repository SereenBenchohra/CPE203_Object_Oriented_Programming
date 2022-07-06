import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntBiFunction;

interface PathingStrategy
{
    /**
     * Returns a prefix of a path from the start point to a point within reach
     * of the end point.  This path is valid ("clear") when returned, but
     * may be invalidated in the future by the movement of other entities.
     * Element 0 of the returned list contains a point along the path one
     * step away from the start; the list may optionally contain more steps 
     * along the path.  If there is no path from start to goal,
     * a list of length zero is returned.
     * <p>
     * The returned value doesn't include the start point or the end point.
     *
     * @param start   The start point for the path.
     * @param end     The goal, the end point for the path.
     * @param canPassThrough      A function that returns true if the
     *                            given point isn't blocked.
     * @param potentialNeighbors  A function that gives the potential neighbors
     *                            for a given point.  This is just the set
     *                            of points an entity could move to assuming
     *                            nothing were blocked.
     * @param stepsFromTo         A function that gives the number of steps
     *                            from one point to another, assuming no
     *                            obstacles.
     * @return The prefix of a path from start to end.
     */
    List<Point> computePath(Point start, Point end,
                            Predicate<Point> canPassThrough,
                            Function<Point, List<Point>> potentialNeighbors,
                            ToIntBiFunction<Point, Point> stepsFromTo);

    /**
     * Give this strategy a debug grid to draw into, or reset it back to
     * null.  It might be helpful to use this to visualize what your
     * algorithm is doing, particularly if you write some more complex
     * unit tests.
     */
    public void setDebugGrid(DebugGrid debug);
}
