package lab1;

import java.util.Objects;
/**
 * Represents a hotel room with its details such as type, number of beds,
 * room number, and amenities.
 */
public class Room {
    private final String type;
    private final int numberOfBeds;
    private final int roomNumber;
    private final String amenities;

    private Room(RoomBuilder builder) {
        this.type = builder.type;
        this.numberOfBeds = builder.numberOfBeds;
        this.roomNumber = builder.roomNumber;
        this.amenities = builder.amenities;
    }

    @Override
    public String toString() {
        return "Room{" +
                "type='" + type + '\'' +
                ", numberOfBeds=" + numberOfBeds +
                ", roomNumber=" + roomNumber +
                ", amenities='" + amenities + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return roomNumber == room.roomNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber);
    }

    public static class RoomBuilder {
        private String type;
        private int numberOfBeds;
        private int roomNumber;
        private String amenities;

        public RoomBuilder setType(String type) {
            this.type = type;
            return this;
        }

        public RoomBuilder setNumberOfBeds(int numberOfBeds) {
            this.numberOfBeds = numberOfBeds;
            return this;
        }

        public RoomBuilder setRoomNumber(int roomNumber) {
            this.roomNumber = roomNumber;
            return this;
        }

        public RoomBuilder setAmenities(String amenities) {
            this.amenities = amenities;
            return this;
        }

        public Room build() {
            return new Room(this);
        }
    }
}