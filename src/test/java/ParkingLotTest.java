import com.vijay.service.ParkingLotService;
import org.junit.jupiter.api.*;
import org.omg.CORBA.Environment;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.Assert.*;

@TestMethodOrder(OrderAnnotation.class)
public class ParkingLotTest {
    ParkingLotService parkingLot = new ParkingLotService();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void cleanUpStreams() {
        System.setOut(null);
    }

    @Order(1)
    @Test
    public void createParkingLot() throws Exception {
        parkingLot.createParkingLot("6");
        assertEquals("6", parkingLot.lotSize);
        assertEquals(0, parkingLot.slotsAllocated.size());
        assertTrue("Created a parking lot with 6 slots\n".equals(outContent.toString()));
    }

    @Order(2)
    @Test
    public void park() throws Exception {

        parkingLot.park("KA-01-HH-1234", "White");
        parkingLot.park("KA-01-HH-9999", "White");
        parkingLot.park("KA-01-BB-0001", "Black");
        parkingLot.park("KA-01-HH-7777", "Red");
        parkingLot.park("KA-01-HH-2701", "Blue");
        parkingLot.park("KA-01-HH-3141", "Black");

        assertEquals(6, parkingLot.slotsAllocated.size());
    }

    @Order(3)
    @Test
    public void leave() throws Exception {
        parkingLot.leave("4");

        assertEquals(5, parkingLot.slotsAllocated.size());
        assertTrue("Slot number 4 is free\n".equals(outContent.toString()));

        parkingLot.leave("4");
        assertTrue("Slot number 4 is free\nSlot is not allocated\n".equals(outContent.toString()));

        parkingLot.park("KA-01-PB-3333", "White");
    }

    @Order(4)
    @Test
    public void status() throws Exception {
        parkingLot.status();
        assertEquals(6, parkingLot.slotsAllocated.size());

        assertEquals("Slot No." + "\t" + "Registration No" + "\t\t" + "Color" +
                "\n" +
                "1" + "\t\t" + "KA-01-HH-1234" + "\t\t" + "White" +
                "\n" +
                "2" + "\t\t" + "KA-01-HH-9999" + "\t\t" + "White" +
                "\n" +
                "3" + "\t\t" + "KA-01-BB-0001" + "\t\t" + "Black" +
                "\n" +
                "4" + "\t\t" + "KA-01-PB-3333" + "\t\t"	+ "White" +
                "\n" +
                "5" + "\t\t" + "KA-01-HH-2701" + "\t\t" + "Blue" +
                "\n" +
                "6" + "\t\t" + "KA-01-HH-3141" + "\t\t" + "Black" + "\n",outContent.toString());
    }

    @Order(5)
    @Test
    public void getRegistrationNumbersFromColor() throws Exception {
        parkingLot.getRegistrationNumbersFromColor("Blue");
        assertTrue("KA-01-HH-2701\n".equals(outContent.toString()));
    }

    @Order(6)
    @Test
    public void getSlotNumbersFromColor() throws Exception {
        parkingLot.getSlotNumbersFromColor("White");
        assertTrue("1, 2, 4\n".equalsIgnoreCase(outContent.toString()));
    }

    @Order(7)
    @Test
    public void getSlotNumberFromRegNo() throws Exception {
        parkingLot.getSlotNumberFromRegNo("KA-01-HH-1234");
        assertTrue("1\n".equals(outContent.toString()));
    }

}