package lab5.comparator;

import lab5.Room;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


public class RoomComparator {

    private static final Map<String, Integer> roomTypeOrder;

    static {
        roomTypeOrder = new HashMap<>();
        roomTypeOrder.put("Simple", 1);
        roomTypeOrder.put("Medium", 2);
        roomTypeOrder.put("Deluxe", 3);
        roomTypeOrder.put("Premium", 4);
    }

    public static Comparator<Room> byType() {
        return Comparator.comparingInt(room -> roomTypeOrder.getOrDefault(room.getType(), 0));
    }

    public static Comparator<Room> byNumberOfBeds() {
        return Comparator.comparingInt(Room::getNumberOfBeds);
    }

}