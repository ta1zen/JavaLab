package lab3;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class RoomTest {

    @DataProvider(name = "roomDataTostring")
    public Object[][] roomDataTostring() {
        return new Object[][] {
                {
                        new Room.RoomBuilder()
                                .setType("Deluxe")
                                .setNumberOfBeds(2)
                                .setRoomNumber(101)
                                .setAmenities("WiFi, Air Conditioning")
                                .build(),
                        "Room{type='Deluxe', numberOfBeds=2, roomNumber=101, amenities='WiFi, Air Conditioning'}"
                },
        };
    }

    @Test(dataProvider = "roomDataTostring")
    public void testToString(Room room, String expected) {
        assertEquals(room.toString(), expected);
    }

    @DataProvider(name = "roomData")
    public Object[][] roomData() {
        return new Object[][]{
                {
                        new Room.RoomBuilder()
                                .setType("Simple")
                                .setNumberOfBeds(2)
                                .setRoomNumber(101)
                                .setAmenities("WiFi, Air Conditioning")
                                .build(),
                        new Room.RoomBuilder()
                                .setType("Deluxe")
                                .setNumberOfBeds(2)
                                .setRoomNumber(101)
                                .setAmenities("WiFi, Air Conditioning")
                                .build(),
                        true
                },
                {
                        new Room.RoomBuilder()
                                .setType("Simple")
                                .setNumberOfBeds(2)
                                .setRoomNumber(101)
                                .setAmenities("WiFi, Air Conditioning")
                                .build(),
                        new Room.RoomBuilder()
                                .setType("Simple")
                                .setNumberOfBeds(2)
                                .setRoomNumber(102)
                                .setAmenities("WiFi, Air Conditioning")
                                .build(),
                        false
                }
        };
    }

    @Test(dataProvider = "roomData")
    public void testEquals(Room room1, Room room2, boolean expected) {
        assertEquals(room1.equals(room2), expected);
    }



    @Test(dataProvider = "roomData")
    public void roomDataHash(Room room1, Room room2, boolean expected) {
        if (expected) {
            assertEquals(room1.hashCode(), room2.hashCode());
        } else {
            assertNotEquals(room1.hashCode(), room2.hashCode());
        }
    }
}
