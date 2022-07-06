import org.w3c.dom.Node;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntBiFunction;
import java.util.Collections;

//this.pendingEvents = new PriorityQueue<Event>((Event first, Event sec) -> (int)(first.getTime() - sec.getTime()));

 class AStarPathingStrategy implements PathingStrategy
{
    private static final int INFINITE = 1000000000; // Really large value for default gScore
    @Override
    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   Function<Point, List<Point>> potentialNeighbors,
                                   ToIntBiFunction<Point, Point> stepsFromTo)
    {


        List<Point> failure = new ArrayList<Point>();
        //The set of the Nodes already evaluated
        List<Point> closedSet = new ArrayList<>();


        // The set of currently discovered nodes that are not evaluated yet.
        // Initially only the start node is known.
        List<Point> openSet = new ArrayList<>();
        openSet.add(start);

        // For each node, which node it can most efficiently be reached from.
        // If a node can be reached from many nodes, cameFrom will eventually contain the
        // most efficient previous step.
        HashMap<Point, Point> cameFrom = new HashMap<>();

        // For each node, the cost of getting from the start node to that node.
        HashMap<Point, Integer> gScore = new HashMap<>();

        //gScore.getOrDefault(INFINITE);

        // The cost of going from start to start is zero.
        gScore.put(start, 0);

        // For each node, the total cost of getting from the start node to the goal
        // by passing by that node. That value is partly known, partly heuristic.
        HashMap<Point, Integer> fScore = new HashMap<>();

        // For the first node, that value is completely heuristic.
        fScore.put(start, (int)costEstimate(start, end, stepsFromTo.applyAsInt(start, end)));

        while (!(openSet.isEmpty()))
        {
            Point current = openSet.get(0); //Current Index, of type Point
            int lowest = fScore.get(current);
            List<Point> neighbors = potentialNeighbors.apply(current);

            for(Point p: openSet) // Finding and Setting to the actual lowest values
            {
                if(fScore.get(p) < lowest)
                {
                    current = p;
                    lowest = fScore.get(p);
                }
            }

            if(current.equals(end)) // needs to be one position away from the ore, could use potential neighbor
            {
                openSet.add(end);
                failure.add(end);
                return openSet;
            }

            openSet.remove(current);
            closedSet.add(current);

            for (Point neighbor : potentialNeighbors.apply(current))
            {
                if(closedSet.contains(neighbor))
                {
                    continue;  //Ignore the neighbor which is already evaluated
                }

                if(!openSet.contains(neighbor)) // Discover a new node
                {

                    closedSet.add(neighbor);

                    openSet.add(neighbor);
                    //failure.add(neighbor);
                }

                //Distance from start to a neighbor
                // The "dist_between" function may vary as per the solution requirements.

                int tentative_gScore = gScore.getOrDefault(current, stepsFromTo.applyAsInt(current, start)) +
                dist_between(current, neighbor);

                if (tentative_gScore >= gScore.getOrDefault(neighbor, INFINITE))
                {
                    continue; // This is not a better path
                }

                //This path is the best until now. Record it!!
                cameFrom.put(neighbor, current);
                gScore.put(neighbor, tentative_gScore);
                fScore.put(neighbor, (int)(gScore.getOrDefault(neighbor, INFINITE) + costEstimate(neighbor, end,
                        stepsFromTo.applyAsInt(neighbor, end))));

            }

        }


        return openSet;
    }
    private List<Point> reconstruct_path(HashMap<Point, Point> cameFrom, Point current)
    {
        List<Point> total_path = new ArrayList<>();
        total_path.add(current);;
        while(cameFrom.containsKey(current))
        {
            current = cameFrom.get(current);
            System.out.println(current);
            total_path.add(current);
        }
        Collections.reverse(total_path);
        return total_path;
    }
    protected double costEstimate(Point current, Point goal, int c)
    {

        int dx = Math.abs(current.getX() - goal.getX());
        int dy = Math.abs(current.getY() - goal.getY());
        int cost = dx + dy;

        if(cost >= (1.05 * c))
            return c;
        else
            return cost;
    }

    private int dist_between(Point current, Point neighbor)
    {
        return Math.abs(current.getX() - neighbor.getX()) + Math.abs(current.getY() - neighbor.getY());
    }


    @Override
    public void setDebugGrid(DebugGrid grid) {
        // Nothing much interesting to see here, so we don't use it.
    }
}
