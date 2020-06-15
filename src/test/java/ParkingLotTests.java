import com.vijay.service.ParkingLotService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

;import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ParkingLotTests {

    ParkingLotService parkingLot;

    @Before
    public void before() throws Exception {
        parkingLot = new ParkingLotService();
    }

    @After
    public void after() throws Exception {
        parkingLot = null;
    }

    /**
     * Method: setUp()
     */
    @Test
    public void testSetUp() throws Exception {
        parkingLot = new ParkingLotService();
    }

    /**
     * Method: createParkingLot()
     */
    @Test
    public void testCreateParkingLot() throws Exception {
        String ans = parkingLot.createParkingLot("6");
        assertEquals("6", ans);
    }

    /**
     * Method: park()
     */
    @Test
    public void testPark() throws Exception {
        String ans = parkingLot.createParkingLot("6");
        assertEquals("6", ans);

        parkingLot.park("KA-01-HH-1234", "White");
        parkingLot.park("KA-01-HH-9999", "White");
        parkingLot.park("KA-01-BB-0001", "Black");
        parkingLot.park("KA-01-HH-7777", "Red");

        assertEquals(5, parkingLot.park("KA-01-HH-3141", "Black"));
    }

    /**
     * Method: leave()
     */
    @Test
    public void testLeave() throws Exception {
        ParkingLotService parkingLot = new ParkingLotService();
        String ans = parkingLot.createParkingLot("6");

        parkingLot.park("KA-01-HH-1234", "White");
        parkingLot.park("KA-01-HH-9999", "White");
        parkingLot.park("KA-01-BB-0001", "Black");
        parkingLot.park("KA-01-HH-7777", "Red");
        parkingLot.park("KA-01-HH-2701", "Blue");
        parkingLot.park("KA-01-HH-3141", "Black");

        assertEquals(4, parkingLot.leave("4"));
    }

    /**
     * Method: status()
     */
    @Test
    public void testStatus() throws Exception {
        String ans = parkingLot.createParkingLot("6");
        assertEquals("6", ans);

        parkingLot.park("KA-01-HH-1234", "White");
        parkingLot.park("KA-01-HH-9999", "White");
        parkingLot.park("KA-01-BB-0001", "Black");
        parkingLot.park("KA-01-HH-7777", "Red");
        parkingLot.park("KA-01-HH-2701", "Blue");
        parkingLot.park("KA-01-HH-3141", "Black");

        assertEquals(6, parkingLot.slotsAllocated.size());
        assertEquals(4, parkingLot.leave("4"));

        assertEquals(5, parkingLot.slotsAllocated.size());
        parkingLot.status();
    }

    /**
     * Method: getRegistrationNumbersFromColor()
     */
    @Test
    public void testGetRegistrationNumbersFromColor() throws Exception {
        String ans = parkingLot.createParkingLot("6");
        assertEquals("6", ans);

        parkingLot.park("KA-01-HH-1234", "White");
        parkingLot.park("KA-01-HH-9999", "White");
        parkingLot.park("KA-01-BB-0001", "Black");
        parkingLot.park("KA-01-HH-7777", "Red");
        parkingLot.park("KA-01-HH-2701", "Blue");
        parkingLot.park("KA-01-HH-3141", "Black");

        assertEquals(6, parkingLot.slotsAllocated.size());
        assertEquals(4, parkingLot.leave("4"));

        assertEquals(5, parkingLot.slotsAllocated.size());
        parkingLot.status();

        List<String> list = new ArrayList<>();
        list.add("KA-01-HH-2701");
        assertEquals(list, parkingLot.getRegistrationNumbersFromColor("Blue"));
    }

    /**
     * Method: getSlotNumbersFromColor()
     */
    @Test
    public void testGetSlotNumbersFromColor() throws Exception {
        String ans = parkingLot.createParkingLot("6");
        assertEquals("6", ans);

        parkingLot.park("KA-01-HH-1234", "White");
        parkingLot.park("KA-01-HH-9999", "White");
        parkingLot.park("KA-01-BB-0001", "Black");
        parkingLot.park("KA-01-HH-7777", "Red");
        parkingLot.park("KA-01-HH-2701", "Blue");
        parkingLot.park("KA-01-HH-3141", "Black");

        assertEquals(6, parkingLot.slotsAllocated.size());
        assertEquals(4, parkingLot.leave("4"));

        assertEquals(5, parkingLot.slotsAllocated.size());
        parkingLot.status();

        List<String> list = new ArrayList<>();
        list.add("KA-01-HH-2701");
        assertEquals(list, parkingLot.getRegistrationNumbersFromColor("Blue"));

        list.clear();
        list.add("1");
        list.add("2");
        assertEquals(list, parkingLot.getSlotNumbersFromColor("White"));
    }

    /**
     * Method: getSlotNumberFromRegNo()
     */
    @Test
    public void testGetSlotNumberFromRegNo() throws Exception {
        String ans = parkingLot.createParkingLot("6");
        assertEquals("6", ans);

        parkingLot.park("KA-01-HH-1234", "White");
        parkingLot.park("KA-01-HH-9999", "White");
        parkingLot.park("KA-01-BB-0001", "Black");
        parkingLot.park("KA-01-HH-7777", "Red");
        parkingLot.park("KA-01-HH-2701", "Blue");
        parkingLot.park("KA-01-HH-3141", "Black");

        assertEquals(6, parkingLot.slotsAllocated.size());
        assertEquals(4, parkingLot.leave("4"));

        assertEquals(5, parkingLot.slotsAllocated.size());
        parkingLot.status();

        List<String> list = new ArrayList<>();
        list.add("KA-01-HH-2701");
        assertEquals(list, parkingLot.getRegistrationNumbersFromColor("Blue"));

        list.clear();
        list.add("1");
        list.add("2");
        assertEquals(list, parkingLot.getSlotNumbersFromColor("White"));

        assertEquals("1", parkingLot.getSlotNumberFromRegNo("KA-01-HH-1234"));
    }


}
