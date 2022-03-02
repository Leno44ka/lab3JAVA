/**
 * This class represents a specific location in a 2D map.  Coordinates are
 * integer values.
 **/
public class Location
{
    /** X coordinate of this location. **/
    public int xCoord;

    /** Y coordinate of this location. **/
    public int yCoord;


    /** Creates a new location with the specified integer coordinates. **/
    public Location(int x, int y)
    {
        xCoord = x;
        yCoord = y;
    }

    /** Creates a new location with coordinates (0, 0). **/
    public Location()
    {
        this(0, 0);
    }

    /** редактирование и доп реализация
    /** Сравниваем эту Location с другой **/
    public boolean equals(Object obj) {

        if (obj instanceof Location) {

            // Приводим объект к типу Location и сравниваем, если все ок, то true
            Location other = (Location) obj;
            if (xCoord == other.xCoord && yCoord == other.yCoord) {
                return true;
            }
        }

        // Если не подошло, то false
        return false;
    }

    /** Назначает hashCode каждой Location. **/
    public int hashCode() {
        int result = 13; // Начальная ценность
        result = 37 * result + xCoord;
        result = 37 * result + yCoord;
        return result;
    }
}