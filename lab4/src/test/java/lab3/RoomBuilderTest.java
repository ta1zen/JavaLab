package lab3;

import jakarta.validation.ValidationException;
import lab3.Room;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class RoomBuilderTest {
    private Room.RoomBuilder roomBuilder;

    @BeforeTest
    public void setUp() {
        roomBuilder = new Room.RoomBuilder();
    }

    @Test
    public void testBuildRoom() {
        Room room = roomBuilder
                .setType("Deluxe")
                .setNumberOfBeds(2)
                .setRoomNumber(101)
                .setAmenities("WiFi, TV")
                .build();

        assertEquals(room.getType(), "Deluxe");
        assertEquals(room.getNumberOfBeds(), 2);
        assertEquals(room.getRoomNumber(), 101);
        assertEquals(room.getAmenities(), "WiFi, TV");
    }


    @Test(expectedExceptions = ValidationException.class)
    public void testBuildRoomWithZeroBeds() {
        try {
            new Room.RoomBuilder()
                    .setType(null)
                    .setNumberOfBeds(0)
                    .setRoomNumber(103)
                    .setAmenities("AC")
                    .build();
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
            assertTrue(e.getMessage().contains("must be at least 1"));
            throw e;
        }
    }

}
