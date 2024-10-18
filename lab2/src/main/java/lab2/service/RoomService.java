package lab2.service;

import lab2.Room;
import lab2.comparator.RoomComparator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Builder;

import java.util.*;
import java.util.stream.Collectors;

@Getter
//@Builder
@AllArgsConstructor
public class RoomService {
    private List<Room> rooms;

    // Method to sort rooms by room number
    public List<Room> sortRoomsByNumber() {
        return rooms.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public static List<Room> sortRoomsByType(List<Room> rooms) {
        return rooms.stream()
                .sorted(RoomComparator.byType())
                .collect(Collectors.toList());
    }

    // Method to get total number of beds in a list of rooms
    public int getTotalNumberOfBeds() {
        return rooms.stream()
                .mapToInt(Room::getNumberOfBeds)
                .sum();
    }

    // Method to find rooms by type
    public List<Room> findRoomsByType(String type) {
        return rooms.stream()
                .filter(room -> room.getType().equalsIgnoreCase(type))
                .sorted(RoomComparator.byType())
                .collect(Collectors.toList());
    }

    public List<Room> sortRoomsByNumberOfBeds(){
        return rooms.stream()
                .sorted(RoomComparator.byNumberOfBeds())
                .collect(Collectors.toList());
    }

}

