import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.*;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntBiFunction;

 public class AStarPathingStrategy implements PathingStrategy{

    public List<Point> computePath(Point start, Point end, Predicate<Point> canPassThrough,
        Function<Point, List<Point>> potentialNeighbors,ToIntBiFunction<Point, Point> stepsFromTo){

        LinkedList<Point> dest = new LinkedList<>();
        Set<Point> closed = new HashSet<>();
        HashMap<Point, Point> hash = new HashMap<>();

        PriorityQueue<Point> openses = new PriorityQueue<Point>((Point p1, Point p2) -> {

            double f_1 = get_e(start, end, p1);

            double f_2 = get_e(start, end, p2);

            return (int)(f_1 - f_2);

            });

        Point curr = start;
        openses.add(curr);

        while(!Point.adjacent(curr, end)){
            curr = openses.poll();

            if(curr == null){
                return dest;
            }

            if(Point.adjacent(curr, end)){
                hash.put(end,curr);
                dest = reconstructPath(hash, end);



                return dest;
                }
            closed.add(curr);
            LinkedList<Point> a_new_list = new LinkedList<>();





            for(Point p : potentialNeighbors.apply(curr)){
            if (canPassThrough.test(p) && !closed.contains(p)){
                a_new_list.add(p);
                }
            }

            for (Point c : a_new_list){
                if(!openses.contains(c)){
                    openses.add(c);
                    hash.put(c, curr);
                    }
                }
        }
        return dest;

        }

     protected LinkedList<Point> reconstructPath(HashMap<Point, Point> past, Point currrent)

{
        LinkedList<Point> dest = new LinkedList<Point>();
        dest.add(currrent);
        while(past.get(currrent) != null){
            currrent = past.get(currrent);
            dest.add(currrent);
        }
        dest.remove(dest.size() - 1);





        Collections.reverse(dest);
        return dest;
        }


     protected double cost_Est(Point p1,  Point p2,  int steps)
{
        double hypotenuse = Math.pow(p1.distanceSquared(p1, p2), 0.5);

        if (hypotenuse <= 1.05 * (double) steps){
            return (double) steps;
        }
        else{
            return hypotenuse;
        }
        }

        protected double get_e(Point start, Point end, Point now)
        {
            double dist = (Math.pow(now.getX() - end.getX(), 2)) + (Math.pow(now.getY() - end.getY(), 2));


            double distance = Math.pow(dist, 0.5);
            double cost = cost_Est(now, end, (int) distance);
            if(start == end){
                return cost;
            }
            else{
                double steps = (double) Math.abs(now.getX() - start.getX()) + Math.abs(now.getY() - start.getY());
                return cost + steps;

        }

        }

    @Override
    public void setDebugGrid(DebugGrid grid) {
        
    }

}
