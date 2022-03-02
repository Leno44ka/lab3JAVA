import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * This class stores the basic state necessary for the A* algorithm to compute
 * a path across a map.  This state includes a collection of "open waypoints"
 * and another collection of "closed waypoints."  In addition, this class
 * provides the basic operations that the A* pathfinding algorithm needs to
 * perform its processing.
 **/
public class AStarState {
    /**
     * This is a reference to the map that the A* algorithm
     * is navigating.
     **/
    private Map2D map;

    /**
     * Initialize a map of all open waypoints and their locations.
     **/
    private Map<Location, Waypoint> open_waypoints
            = new HashMap<Location, Waypoint>();

    /**
     * Initialize a map of all closed waypoints and their locations.
     **/
    private Map<Location, Waypoint> closed_waypoints
            = new HashMap<Location, Waypoint>();

    /**
     * Initialize a new state object for the A* pathfinding algorithm to use.
     **/
    public AStarState(Map2D map) {
        if (map == null)
            throw new NullPointerException("map cannot be null");

        this.map = map;
    }

    /**
     * Returns the map that the A* pathfinder is navigating.
     **/
    public Map2D getMap() {
        return map;
    }

    /**
     * This method scans through all open waypoints, and returns the waypoint
     * with the minimum total cost.  If there are no open waypoints, this
     * method returns <code>null</code>.
     **/
    public Waypoint getMinOpenWaypoint() {
        // Проверка на то, есть ли в наборе открытые вершины?
        if (numOpenWaypoints() == 0)
            return null;

        // Создаем keySet, переменные для лучшей точки и лучшего веса для этой точки
        Set open_waypoint_keys = open_waypoints.keySet();
        Waypoint best = null;
        float best_cost = Float.MAX_VALUE;

        // Проходимся по всех точкам
        for (Object set : open_waypoint_keys) {
            // Сохраняем текущую Location
            Location location = (Location) set;
            // Сохраняем текущую точку
            Waypoint waypoint = open_waypoints.get(location);
            // Сохраняем текущий вес точки
            float waypoint_total_cost = waypoint.getTotalCost();

            // Если текущий вес ниже(лучше), чем тот что был запомнен, то
            // заменяем его и запомненный вес тоже заменяем
            if (waypoint_total_cost < best_cost) {
                best = open_waypoints.get(location);
                best_cost = waypoint_total_cost;
            }
        }
        // Возвращаем точук с минимальной стоимостью
        return best;
    }

    /**
     * This method adds a waypoint to (or potentially updates a waypoint
     * already in) the "open waypoints" collection.  If there is not already
     * an open waypoint at the new waypoint's location then the new waypoint
     * is simply added to the collection.  However, if there is already a
     * waypoint at the new waypoint's location, the new waypoint replaces
     * the old one <em>only if</em> the new waypoint's "previous cost" value
     * is less than the current waypoint's "previous cost" value.
     **/
    public boolean addOpenWaypoint(Waypoint newWP) {
        // Находим location новой точки
        Location location = newWP.getLocation();

        // Проверяем нет ли ее в списке открытых точек
        if (open_waypoints.containsKey(location)) {

            // Если она есть, то проверяем. Было ли предыдущее значение веса меньше,
            // чем новое(текущее)
            Waypoint current_waypoint = open_waypoints.get(location);
            if (newWP.getPreviousCost() < current_waypoint.getPreviousCost()) {

                // Если у новой точки предыдущий вес меньше(лучше),
                // чем предыдущий вес у текущей точки, то новая
                // точка заменяется на старую и возвращается true
                open_waypoints.put(location, newWP);
                return true;
            }

            // Если предыдущий вес новой точки не меньше, чем
            // предыдущий вес у текущей точки, то возвращаем false
            return false;
        }

        // Если точки нет в листе открытых точек, то
        // добавляем новую точку в коллекцию точек
        // и возвращаем true
        open_waypoints.put(location, newWP);
        return true;
    }


    /**
     * Returns the current number of open waypoints.
     **/
    public int numOpenWaypoints() {
        // Возвращаем размер массива открытых точек
        return open_waypoints.size();
    }

    /**
     * This method moves the waypoint at the specified location from the
     * open list to the closed list.
     **/
    public void closeWaypoint(Location loc) {
        // Перенос точки из коллекции открытых точек в закрытые
        Waypoint waypoint = open_waypoints.remove(loc);
        closed_waypoints.put(loc, waypoint);
    }

    /**
     * Returns true if the collection of closed waypoints contains a waypoint
     * for the specified location.
     **/
    public boolean isLocationClosed(Location loc) {
        // Проверка на нахождение в наборе указанного
        // местонахождения(наборе закрытых вершин)
        return closed_waypoints.containsKey(loc);
    }
}