package lab3;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import java.util.Objects;
/**
 * Represents a hotel room with its details such as type, number of beds,
 * room number, and amenities.
 */

//@Builder
@Getter
//@JsonDeserialize(builder = Room.RoomBuilder.class)

public class Room implements Comparable<Room> {
    private String type;
    private int numberOfBeds;
    private int roomNumber;
    private String amenities;

    public Room(){
    }

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

    //@JsonPOJOBuilder
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

        //@JsonCreator
        public Room build() {
            return new Room(this);
        }
    }

    @Override
    public int compareTo(Room o) {
        return Integer.compare(this.roomNumber, o.roomNumber);
    }

}
