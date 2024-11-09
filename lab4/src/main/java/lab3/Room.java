package lab3;


import jakarta.validation.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a hotel room with its details such as type, number of beds,
 * room number, and amenities.
 */

//@Builder
@Getter
//@JsonDeserialize(builder = Room.RoomBuilder.class)

public class Room implements Comparable<Room> {
    @NotNull(message = "Type cannot be null")
    @Size(min = 2, max = 50, message = "Type must be between 2 and 50 characters")
    private String type;

    @Min(value = 1, message = "Number of beds must be at least 1")
    private int numberOfBeds;

    @Min(value = 1, message = "Room number must be at least 1")
    private int roomNumber;

    private String amenities;

    public Room(){
    }

    private Room(String type, int numberOfBeds, int roomNumber, String amenities) {
    this.type = type;
    this.numberOfBeds = numberOfBeds;
    this.roomNumber = roomNumber;
    this.amenities = amenities;
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

    private static RoomBuilder builder() {
        return new RoomBuilder();
    }

    public static class RoomBuilder {
        private String type;
        private int numberOfBeds;
        private int roomNumber;
        private String amenities;

        RoomBuilder() {
        }

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
            var room = new Room(this.type, this.numberOfBeds, this.roomNumber, this.amenities);

            try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
                Validator validator = factory.getValidator();
                Set<ConstraintViolation<Room>> violations = validator.validate(room);
                List<String> validationError = violations.stream().map
                        (v -> "validation error in " + v.getPropertyPath() + ", value `" + v.getInvalidValue() + "` should satisfy condition: " + v.getMessage()).toList();
                if (!violations.isEmpty()) {
                    throw new ValidationException(String.join("\n", validationError));
                }

                System.out.println(Arrays.toString(validationError.toArray()));

                return room;
            }

        }
    }

    @Override
    public int compareTo(Room o) {
        return Integer.compare(this.roomNumber, o.roomNumber);
    }

}
