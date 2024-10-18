package lab2;

import lab2.service.RoomService;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class RoomServiceTest {
    private RoomService roomService;
    @BeforeTest
    public void setUp() {
        roomService = new RoomService(Arrays.asList(
                new Room.RoomBuilder().setRoomNumber(101).setType("Deluxe").setNumberOfBeds(2).setAmenities("WiFi").build(),
                new Room.RoomBuilder().setRoomNumber(102).setType("Simple").setNumberOfBeds(1).setAmenities("WiFi").build(),
                new Room.RoomBuilder().setRoomNumber(103).setType("Deluxe").setNumberOfBeds(3).setAmenities("WiFi").build()
        ));
    }

    // Test for findRoomsByType method
    @Test
    public void testFindRoomsByType() {
        String type = "Deluxe";
        int expectedCount = 2;
        List<Room> foundRooms = roomService.findRoomsByType(type);
        assertEquals(foundRooms.size(), expectedCount);
    }

    // Test for getTotalNumberOfBeds method
    @Test
    public void testGetTotalNumberOfBeds() {
        int expected = 6;
        int sum = roomService.getTotalNumberOfBeds();
        assertEquals(sum, expected);
    }

    // DataProvide for sortRoomsByType method
    @DataProvider(name = "sortByType")
    public Object[][] sortByType() {
        return new Object[][] {
                {
                        // Input list of rooms
                        Arrays.asList(
                                new Room.RoomBuilder().setRoomNumber(101).setType("Deluxe").setNumberOfBeds(2).setAmenities("WiFi").build(),
                                new Room.RoomBuilder().setRoomNumber(102).setType("Simple").setNumberOfBeds(1).setAmenities("WiFi").build(),
                                new Room.RoomBuilder().setRoomNumber(103).setType("Deluxe").setNumberOfBeds(3).setAmenities("WiFi").build()
                        ),

                        Arrays.asList(
                                new Room.RoomBuilder().setRoomNumber(102).setType("Simple").setNumberOfBeds(1).setAmenities("WiFi").build(),
                                new Room.RoomBuilder().setRoomNumber(101).setType("Deluxe").setNumberOfBeds(2).setAmenities("WiFi").build(),
                                new Room.RoomBuilder().setRoomNumber(103).setType("Deluxe").setNumberOfBeds(3).setAmenities("WiFi").build()
                        )
                },
                {

                        Arrays.asList(
                                new Room.RoomBuilder().setRoomNumber(201).setType("Simple").setNumberOfBeds(1).setAmenities("WiFi").build(),
                                new Room.RoomBuilder().setRoomNumber(202).setType("Premium").setNumberOfBeds(2).setAmenities("WiFi").build(),
                                new Room.RoomBuilder().setRoomNumber(203).setType("Deluxe").setNumberOfBeds(3).setAmenities("WiFi").build()
                        ),

                        Arrays.asList(
                                new Room.RoomBuilder().setRoomNumber(201).setType("Simple").setNumberOfBeds(1).setAmenities("WiFi").build(),
                                new Room.RoomBuilder().setRoomNumber(203).setType("Deluxe").setNumberOfBeds(3).setAmenities("WiFi").build(),
                                new Room.RoomBuilder().setRoomNumber(202).setType("Premium").setNumberOfBeds(2).setAmenities("WiFi").build()

                        )
                }
        };
    }


    // Test for sortRoomsByType method
    @Test(dataProvider = "sortByType")
    public void testSortRoomsByType(List<Room> rooms, List<Room> expectedSortedRooms) {
        List<Room> sortedRooms = RoomService.sortRoomsByType(rooms);
        assertEquals(sortedRooms, expectedSortedRooms);
    }

    @Test
    public void testSortRoomsByNum() {
        List<Room> expectedSortedRooms = Arrays.asList(
                new Room.RoomBuilder().setRoomNumber(101).setType("Simple").setNumberOfBeds(1).setAmenities("WiFi").build(),
                new Room.RoomBuilder().setRoomNumber(102).setType("Deluxe").setNumberOfBeds(3).setAmenities("WiFi").build(),
                new Room.RoomBuilder().setRoomNumber(103).setType("Deluxe").setNumberOfBeds(2).setAmenities("WiFi").build()

        );
        List<Room> sortedRooms = roomService.sortRoomsByNumber();
        assertEquals(sortedRooms, expectedSortedRooms);
    }

    @Test
    public void testSortRoomsByNumberOfBeds(){
        List<Room> expectedSortedRooms = Arrays.asList(
                new Room.RoomBuilder().setRoomNumber(102).setType("Simple").setNumberOfBeds(1).setAmenities("WiFi").build(),
                new Room.RoomBuilder().setRoomNumber(101).setType("Deluxe").setNumberOfBeds(2).setAmenities("WiFi").build(),
                new Room.RoomBuilder().setRoomNumber(103).setType("Deluxe").setNumberOfBeds(3).setAmenities("WiFi").build()
        );
        List<Room> sortedRooms = roomService.sortRoomsByNumberOfBeds();
        assertEquals(sortedRooms, expectedSortedRooms);
    }

}
